package db.model;

import db.compositekey.WordCategoryFrequencyPK;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by mgunes on 18.12.2016.
 */
@Entity
@Table(name = "word_category_frequency")
public class WordCategoryFrequency implements Serializable{

    @EmbeddedId
    private WordCategoryFrequencyPK wordCategoryFrequencyPK;

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

    public WordCategoryFrequencyPK getWordCategoryFrequencyPK() {
        return wordCategoryFrequencyPK;
    }

    public void setWordCategoryFrequencyPK(WordCategoryFrequencyPK wordCategoryFrequencyPK) {
        this.wordCategoryFrequencyPK = wordCategoryFrequencyPK;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
