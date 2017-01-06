package db.model;

import db.compositekey.AccountAnalyzePK;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by mgunes on 06.01.2017.
 */
@Entity
@Table(name = "account_analyze")
public class AccountAnalyze implements Serializable {
    @EmbeddedId
    private AccountAnalyzePK pk;

    @Column(name = "weight", nullable = false)
    private int weight;

    public AccountAnalyze() {
        super();
    }

    public AccountAnalyzePK getPk() {
        return pk;
    }

    public void setPk(AccountAnalyzePK pk) {
        this.pk = pk;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
