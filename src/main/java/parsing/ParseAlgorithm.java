package parsing;

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

    public ParseAlgorithm() {
        plainTweets = new ArrayList<PlainTweet>();
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

                if(word.charAt(0) != '#' && wordDAO.isWordExist(word)) {
                    tempWords.append(word + "-");
                    count++;
                }

                parsedTweet.setOrderedWords(tempWords.toString());
            }

        }

        return count;
    }

    private String findHashtag(String[] words) {
        String hashtag = null;
        int i = 0;
        int j = 0;

        while(i < words.length && j < 2) {
            if(words[i].charAt(0) == '#') {
                hashtag = words[i];
                j++;
            }

            i++;
        }

        return hashtag;
    }
}
