package parsing;

import hibernate.ParsedTweetDAO;
import hibernate.PlainTweetDAO;
import hibernate.WordDAO;
import model.AllWords;
import model.ParsedTweet;
import model.PlainTweet;
import zemberek.morphology.analysis.WordAnalysis;
import zemberek.morphology.analysis.tr.TurkishMorphology;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mgunes on 17.12.2016.
 */

public class ParseAlgorithm {
    private List<PlainTweet> plainTweets;
    private List<ParsedTweet> parsedTweets;

    public ParseAlgorithm() {
        parsedTweets = new ArrayList<ParsedTweet>();
    }

    public int parseNewTweets() {
        TurkishMorphology morphology = null;
        PlainTweetDAO plainTweetDAO = new PlainTweetDAO();
        plainTweets = plainTweetDAO.getRawTweets();
        ParsedTweet parsedTweet ;
        WordDAO wordDAO = new WordDAO();
        int count = 0;
        WordAlgorithm wordAlgorithm = new WordAlgorithm();

        try {
            morphology = TurkishMorphology.createWithDefaults();

            for(PlainTweet plainTweet: plainTweets) {
                StringBuilder tempWords = new StringBuilder();
                parsedTweet = new ParsedTweet();
                String[] words = plainTweet.getTweet().split(" ");

                //kelime başındaki-sonundaki . , ! ? = " " ( ) temizlenecek
                words = cleanPunctuation(words);

                //parsedTweet.setHashtag(findHashtag(words));

                parsedTweet.setId(plainTweet.getId());

                int countedWords = 0; //zemberek tarafından parse edilebilen kelime sayısı
                int totalWords = 0; // özel isim, hashtag, mention hariç kelime sayısı

                for(String word: words) {
                    word = word.toLowerCase();

                    if(!word.contains("#") && !word.contains("@") && !word.contains("'")
                           && word.length() > 3 && !word.equals("rt")){
                        totalWords++;
                        try{
                            //köküne ayır
                            List<WordAnalysis> results = morphology.analyze(word);

                            //kelime sözlükte var
                            if(results.get(0).getLemma().length() > 3) {
                                countedWords++;

                                if(!wordDAO.isStopWord(word)) {
                                    tempWords.append(results.get(0).getLemma() + "-");
                                }
                            }
                        } catch(Exception ex){
                            System.out.println(ex.getMessage());
                        }

                    }
                    parsedTweet.setOrderedWords(tempWords.toString());
                }

                if(countedWords > 0) {
                    parsedTweet.setImpactRate((float)countedWords / totalWords);
                    count++;
                    parsedTweets.add(parsedTweet);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ParsedTweetDAO parsedTweetDAO = new ParsedTweetDAO();
        parsedTweetDAO.saveParsedList(parsedTweets);

        return count;
    }

    private String findHashtag(String[] words) {
        String hashtag = null;
        int i = 0;
        int j = 0;

        while(i < words.length && j < 2) {
            if(words[i].contains("#")) {
                hashtag = words[i];
                j++;
            }

            i++;
        }

        if(j == 2)
            return null;

        return hashtag;
    }

    private String[] cleanPunctuation(String[] words) {
        for(int i = 0; i < words.length; i++) {
            if(words.length > 1) {
                try {
                    words[i] = clean(words[i]);
                } catch (StringIndexOutOfBoundsException ex) {
                    System.out.println(ex.getMessage());
                }
            }


        }

        return words;
    }

    protected String clean(String s) throws StringIndexOutOfBoundsException {
        int length = s.length() - 1;

        if(s.charAt(0) == '?' || s.charAt(0) == '.' || s.charAt(0) == ','
                || s.charAt(0) == '"' || s.charAt(0) == '"' || s.charAt(0) == '='
                ||  s.charAt(0) == '!' || s.charAt(0) == '(' || s.charAt(0) == ')'
                ||  s.charAt(0) == '-' ||  s.charAt(0) == '-') {
            return s.substring(1, s.length());
        } else if(s.charAt(length) == '?' || s.charAt(length) == '.' || s.charAt(length) == ','
                || s.charAt(length) == '"' || s.charAt(length) == '"' || s.charAt(length) == '='
                ||  s.charAt(length) == '!' ||  s.charAt(length) == '(' ||  s.charAt(length) == ')'
                ||  s.charAt(length) == '-' ||  s.charAt(length) == '-') {
           return s.substring(0, length);
        } else {
            return s;
        }
    }
}
