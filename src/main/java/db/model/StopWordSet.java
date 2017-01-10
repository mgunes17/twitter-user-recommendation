package db.model;

import db.hibernate.WordDAO;

import java.util.Set;

public class StopWordSet {
    public static Set<String> STOP_WORD_SET;

    public StopWordSet() {
        STOP_WORD_SET = new WordDAO().getStopWords();
    }
}
