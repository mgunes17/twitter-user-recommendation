package db.compositekey;

import db.model.Category;
import db.model.Sentiment;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class AccountWordAnalyzePK implements Serializable{
    @Column(name = "user_name")
    private String userName;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    @Column(name = "word")
    private String word;


    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
