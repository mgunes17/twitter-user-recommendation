package db.model;

import db.compositekey.AccountWordAnalyzePK;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "account_word_analyze")
public class AccountWordyAnalyze implements Serializable{
    @EmbeddedId
    private AccountWordAnalyzePK pk;

    @ManyToOne
    @JoinColumn(name = "sentiment")
    private Sentiment sentiment;

    public AccountWordAnalyzePK getPk() {
        return pk;
    }

    public void setPk(AccountWordAnalyzePK pk) {
        this.pk = pk;
    }

    public Sentiment getSentiment() {
        return sentiment;
    }

    public void setSentiment(Sentiment sentiment) {
        this.sentiment = sentiment;
    }
}
