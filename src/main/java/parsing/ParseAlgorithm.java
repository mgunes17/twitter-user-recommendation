package parsing;

import hibernate.ParsedTweetDAO;
import hibernate.PlainTweetDAO;
import hibernate.WordDAO;
import model.AllWords;
import model.ParsedTweet;
import model.PlainTweet;

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
        PlainTweetDAO plainTweetDAO = new PlainTweetDAO();
        plainTweets = plainTweetDAO.getRawTweets();
        ParsedTweet parsedTweet ;
        WordDAO wordDAO = new WordDAO();
        int count = 0;
        WordAlgorithm wordAlgorithm = new WordAlgorithm();

        for(PlainTweet plainTweet: plainTweets) {
            StringBuilder tempWords = new StringBuilder();
            parsedTweet = new ParsedTweet();
            String[] words = plainTweet.getTweet().split(" ");
            parsedTweet.setHashtag(findHashtag(words));
            parsedTweet.setId(plainTweet.getId());
            int countedWords = 0;

            for(String word: words) {
                word = word.toLowerCase();

                if(!word.contains("#") && !wordDAO.isStopWord(word) && !word.contains("'")){
                    if(wordDAO.isWordExist(word)){
                        tempWords.append(word + "-");
                        countedWords++;
                    } else {
                        /*try {
                            if(!word.equals("rt") && word.charAt(0) <= 'z' && word.charAt(0) >= 'a'){
                                List<AllWords> wordsWithFirstLetter = wordDAO.getWordsWithFirstLetter(word.charAt(0), word.length());
                                AllWords correctWord = wordAlgorithm.findClosestWord(wordsWithFirstLetter, word);
                                System.out.println(correctWord.getWord() + " ve " + word);
                                tempWords.append(correctWord + "-");
                                countedWords++;
                                wordsWithFirstLetter.clear();
                            }
                        }catch(Exception ex){
                            System.out.println(ex.getMessage());
                        }*/
                    }
                }

                parsedTweet.setOrderedWords(tempWords.toString());
            }
            parsedTweet.setImpactRate((float)countedWords / words.length);
            count++;
            parsedTweets.add(parsedTweet);
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
}
