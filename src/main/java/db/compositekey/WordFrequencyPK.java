package db.compositekey;

import db.model.Category;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by ercan on 21.12.2016.
 */
@Embeddable
public class WordFrequencyPK implements Serializable{

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    @Column(name = "word")
    private String word;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
