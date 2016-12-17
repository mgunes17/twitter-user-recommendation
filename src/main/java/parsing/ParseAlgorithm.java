package parsing;

import hibernate.ParsedTweetDAO;
import hibernate.PlainTweetDAO;
import hibernate.WordDAO;
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
        plainTweets = new ArrayList<PlainTweet>();
        parsedTweets = new ArrayList<ParsedTweet>();
    }

    public int parseNewTweets() {
        PlainTweetDAO plainTweetDAO = new PlainTweetDAO();
        plainTweets = plainTweetDAO.getRawTweets();
        ParsedTweet parsedTweet;
        WordDAO wordDAO = new WordDAO();
        StringBuilder tempWords = new StringBuilder();
        int count = 0;

        for(PlainTweet plainTweet: plainTweets) {
            parsedTweet = new ParsedTweet();
            String[] words = plainTweet.getTweet().split(" ");
            parsedTweet.setHashtag(findHashtag(words));

            for(String word: words) {
                word.toLowerCase();

                if(!word.contains("#") && wordDAO.isWordExist(word) && !wordDAO.isStopWord(word)) {
                    tempWords.append(word + "-");
                    count++;
                }

                parsedTweet.setOrderedWords(tempWords.toString());
            }

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
