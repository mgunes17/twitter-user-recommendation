package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by ercan on 17.12.2016.
 */

@Entity
@Table(name = "ordered_word_list")
@PrimaryKeyJoinColumn(name="id")
public class ParsedTweet extends PlainTweet implements Serializable{

    @Column(name = "hashtag")
    private String hashtag;

    @Column(name = "impact_rate")
    private float impactRate;

    @Column(name = "ordered_words")
    private String orderedWords;

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public float getImpactRate() {
        return impactRate;
    }

    public void setImpactRate(float impactRate) {
        this.impactRate = impactRate;
    }

    public String getOrderedWords() {
        return orderedWords;
    }

    public void setOrderedWords(String orderedWords) {
        this.orderedWords = orderedWords;
    }
}
