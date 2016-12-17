package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by ercan on 14.12.2016.
 */

@Entity
@Table(name = "plain_tweet")
@Inheritance(strategy = InheritanceType.JOINED)
public class PlainTweet implements Serializable {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "tweet")
    private String tweet;

    @Column(name = "screen_name")
    private String screenName;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "favorite_count")
    private int favoriteCount;

    @Column(name = "retweet_count")
    private int retweetCount;

    @Column(name = "created_date")
    private Date createdDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(int retweetCount) {
        this.retweetCount = retweetCount;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedCate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
