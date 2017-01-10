package db.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    public Sentiment(int id) {
        super();
        this.id = id;
    }

    @OneToMany(mappedBy="wordSentimentFrequencyPK.sentiment", targetEntity=WordSentimentFrequency.class,
            fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<WordSentimentFrequency> wordFrequencies = new ArrayList<WordSentimentFrequency>();

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

    public List<WordSentimentFrequency> getWordFrequencies() {
        return wordFrequencies;
    }

    public void setWordFrequencies(List<WordSentimentFrequency> wordFrequencies) {
        this.wordFrequencies = wordFrequencies;
    }
}
