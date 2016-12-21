package collection;

import configuration.TwitterConfiguration;
import db.model.PlainTweet;
import twitter4j.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ercan on 14.12.2016.
 */
public class FetchTweet {
    private int count;
    private String keyword;

    public FetchTweet(String keyword, int count) {
        this.keyword = keyword;
        this.count = count;
    }

    public List<PlainTweet> getTweetsByHashtag(){
        Twitter twitter = TwitterConfiguration.getInstance();
        try {
            Query query = new Query(keyword);
            query.count(count);
            QueryResult result = twitter.search(query);
            List<Status> status = result.getTweets();
            List<PlainTweet> plainTweetList = saveTweets(status);
            return plainTweetList;
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public List<PlainTweet> getTweetsByUsername(){
        Twitter twitter = TwitterConfiguration.getInstance();
        try {
            Paging paging = new Paging(1, count);
            List<Status> status = twitter.getUserTimeline(keyword, paging);
            List<PlainTweet> plainTweetList = saveTweets(status);
            return plainTweetList;
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public List<PlainTweet> saveTweets(List<Status> status){
        List<PlainTweet> plainTweetList = new ArrayList<PlainTweet>();
        PlainTweet plainTweet;

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
    }
}
