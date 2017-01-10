package statistics;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "top_word")
public class TopWord implements Serializable {
    @Id
    @Column(name = "word")
    private String word;

    @Column(name = "count")
    private long count;

    public TopWord(String word, int count) {
        super();
        this.word = word;
        this.count = count;
    }

    public TopWord(){

    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
