package collection;

import configuration.TwitterConfiguration;
import model.PlainTweet;
import twitter4j.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ercan on 14.12.2016.
 */
public class FetchTweet {
    private int count;
    private String hashtag;

    public FetchTweet(String hashtag, int count) {
        this.hashtag = hashtag;
        this.count = count;
    }

    public List<PlainTweet> getTweetsByHashtag(){
        Twitter twitter = TwitterConfiguration.getInstance();
        List<PlainTweet> plainTweetList = new ArrayList<PlainTweet>();
        PlainTweet plainTweet;

        try {
            Query query = new Query(hashtag);
            query.count(count);
            QueryResult result = twitter.search(query);
            List<Status> status = result.getTweets();

            for(Status s: status) {
                plainTweet = new PlainTweet();
                plainTweet.setId(s.getId());
                plainTweet.setScreenName(s.getUser().getScreenName());
                plainTweet.setFavoriteCount(s.getFavoriteCount());
                plainTweet.setCreatedCate(s.getCreatedAt());
                plainTweet.setTweet(s.getText());
                plainTweet.setUserName(s.getUser().getName());
                plainTweet.setRetweetCount(s.getRetweetCount());

                plainTweetList.add(plainTweet);
            }
            return plainTweetList;
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
