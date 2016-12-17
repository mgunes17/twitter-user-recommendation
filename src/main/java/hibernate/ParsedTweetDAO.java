package hibernate;

import model.ParsedTweet;

import java.util.List;

/**
 * Created by mgunes on 17.12.2016.
 */
public class ParsedTweetDAO extends AbstractDAO {

    public boolean saveParsedList(List<ParsedTweet> parsedTweets) {
        return saveList(parsedTweets);
    }
}
