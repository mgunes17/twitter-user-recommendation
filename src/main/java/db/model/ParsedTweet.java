package db.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ercan on 17.12.2016.
 */

@Entity
@Table(name = "ordered_word_list")
//@PrimaryKeyJoinColumn(name="id")
public class ParsedTweet  implements Serializable{

    @Id
    @Column(name="id")
    private long id;

    @Column(name = "hashtag")
    private String hashtag;

    @Column(name = "impact_rate")
    private float impactRate = 0.0f;

    @Column(name = "ordered_words")
    private String orderedWords;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "sentiment")
    private Sentiment sentiment;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Sentiment getSentiment() {
        return sentiment;
    }

    public void setSentiment(Sentiment sentiment) {
        this.sentiment = sentiment;
    }
}
