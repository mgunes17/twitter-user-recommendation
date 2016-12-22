package training;

import db.hibernate.WordFrequencyDAO;

/**
 * Created by mgunes on 22.12.2016.
 */
public class TfIdf {
    private String tweet;
    private WordFrequencyDAO wordFrequencyDAO;

    public TfIdf(String tweet) {
        this.tweet = tweet;
        wordFrequencyDAO = new WordFrequencyDAO();
    }

    public String findCategory() {
        String[] words = tweet.split(" ");
        Double[] values = new Double[8];

        for(int i = 1; i < 8; i++){
            values[i] = 0.0;
        }

        for(String word: words) {
            for(int i = 1; i < 8; i++) {
                values[i] += computeTfIdf(word, i);
            }
        }

        int id = findMax(values);

        return categoryName(id);
    }

    private double computeTfIdf(String word, int id) {
        double idf = (double) wordFrequencyDAO.occurenceOnCategory(word, id) / wordFrequencyDAO.occurenceAllCategory(word);
        double tf = (double) wordFrequencyDAO.occurenceOnCategory(word, id) / wordFrequencyDAO.maxOccurenceOnCategory(id);

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
            case 7 : return  "diğer" ;
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
}
