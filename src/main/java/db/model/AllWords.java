package db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by ercan on 17.12.2016.
 */

@Entity
@Table(name = "all_words")
public class AllWords implements Serializable{
    @Id
    @Column(name = "word")
    private String word;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
