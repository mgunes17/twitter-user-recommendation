package db.model;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by ercan on 12.01.2017.
 */
public class Recommendation {
    private String userName;
    private int count;

    public Recommendation() {
        super();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
