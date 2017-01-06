package db.compositekey;

import db.model.Category;
import db.model.Sentiment;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by ercan on 21.12.2016.
 */
@Embeddable
public class WordSentimentFrequencyPK implements Serializable{

    @ManyToOne
    @JoinColumn(name = "sentiment")
    private Sentiment sentiment;

    @Column(name = "word")
    private String word;

    public Sentiment getSentiment() {
        return sentiment;
    }

    public void setSentiment(Sentiment sentiment) {
        this.sentiment = sentiment;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
