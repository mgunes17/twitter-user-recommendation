package db.model;

import db.compositekey.WordFrequencyPK;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by mgunes on 18.12.2016.
 */
@Entity
@Table(name = "word_frequency27")
public class WordFrequency implements Serializable{

    @EmbeddedId
    private WordFrequencyPK wordFrequencyPK;

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

    public WordFrequencyPK getWordFrequencyPK() {
        return wordFrequencyPK;
    }

    public void setWordFrequencyPK(WordFrequencyPK wordFrequencyPK) {
        this.wordFrequencyPK = wordFrequencyPK;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
