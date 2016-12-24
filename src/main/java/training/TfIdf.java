package training;

import db.hibernate.WordCategoryFrequencyDAO;

/**
 * Created by mgunes on 22.12.2016.
 */
public class TfIdf {
    private String tweet;
    private WordCategoryFrequencyDAO wordCategoryFrequencyDAO;

    public TfIdf() {
        super();
        wordCategoryFrequencyDAO = new WordCategoryFrequencyDAO();
    }

    public TfIdf(String tweet) {
        this.tweet = tweet;
        wordCategoryFrequencyDAO = new WordCategoryFrequencyDAO();
    }

    public String findCategory() {
        int id = findCategoryID();
        return categoryName(id);
    }

    public int findCategoryID() {
        String[] words = tweet.split(" ");
        Double[] values = new Double[7];

        for(int i = 1; i < 7; i++){
            values[i] = 0.0;
        }

        for(String word: words) {
            for(int i = 1; i < 7; i++) {
                values[i] += computeTfIdf(word, i);
            }
        }

        int id = findMax(values);

        return id;
    }

    private double computeTfIdf(String word, int id) {
        double idf = (double) wordCategoryFrequencyDAO.occurenceOnCategory(word, id) / wordCategoryFrequencyDAO.occurenceAllCategory(word);
        double tf = (double) wordCategoryFrequencyDAO.occurenceOnCategory(word, id) / wordCategoryFrequencyDAO.maxOccurenceOnCategory(id);

        return tf * idf;
    }

    private String categoryName(int id) {
        switch (id) {
            case 1 : return  "spor" ;
            case 2 : return  "siyaset" ;
            case 3 : return  "ekonomi" ;
            case 4 : return  "eğlence" ;
            case 5 : return  "sağlık" ;
            case 6 : return  "bilim-teknoloji" ;
            //case 7 : return  "diğer" ;
        }

        return "null";
    }

    private int findMax(Double[] values) {
        int index = 1;
        double max = values[1];

        for(int j = 2; j < values.length; j++) {
            if(values[j] > max) {
                max = values[j];
                index = j;
            }
        }

        return index;
    }

    public String findSentiment() {
        int id = findSentimentID();
        return sentimentName(id);
    }

    public int findSentimentID() {
        String[] words = tweet.split(" ");
        Double[] values = new Double[4];

        for(int i = 1; i < 4; i++){
            values[i] = 0.0;
        }

        for(String word: words) {
            for(int i = 1; i < 4; i++) {
                values[i] += computeTfIdfForSentiment(word, i);
            }
        }

        int id = findMax(values);

        return id;
    }

    private double computeTfIdfForSentiment(String word, int id) {
        double idf = (double) wordCategoryFrequencyDAO.occurenceOnSentiment(word, id) / wordCategoryFrequencyDAO.occurenceAllCategory(word);
        double tf = (double) wordCategoryFrequencyDAO.occurenceOnSentiment(word, id) / wordCategoryFrequencyDAO.maxOccurenceOnSentiment(id);

        return tf * idf;
    }

    private String sentimentName(int id) {
        switch (id) {
            case 1 : return  "olumlu" ;
            case 2 : return  "olumsuz" ;
            case 3 : return  "nötr" ;
        }

        return "null";
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }
}
