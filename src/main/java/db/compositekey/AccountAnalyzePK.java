package db.compositekey;

import db.model.Category;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by mgunes on 06.01.2017.
 */
@Embeddable
public class AccountAnalyzePK {
    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    @Column(name = "user_name")
    private String userName;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
