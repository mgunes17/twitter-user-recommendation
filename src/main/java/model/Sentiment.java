package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by mgunes on 18.12.2016.
 */

@Entity
@Table(name = "sentiment")
public class Sentiment implements Serializable {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    public Sentiment() {
        super();
    }

    //getter-setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
