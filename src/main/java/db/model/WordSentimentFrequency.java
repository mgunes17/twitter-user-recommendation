package db.model;

import db.compositekey.WordSentimentFrequencyPK;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "word_sentiment_frequency")
public class WordSentimentFrequency implements Serializable{

    @EmbeddedId
    private WordSentimentFrequencyPK wordSentimentFrequencyPK;

    @Column(name = "id")
    private int id;

    @Column(name = "count")
    private int count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public WordSentimentFrequencyPK getWordSentimentFrequencyPK() {
        return wordSentimentFrequencyPK;
    }

    public void setWordSentimentFrequencyPK(WordSentimentFrequencyPK wordSentimentFrequencyPK) {
        this.wordSentimentFrequencyPK = wordSentimentFrequencyPK;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
