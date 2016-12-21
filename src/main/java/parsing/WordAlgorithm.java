package parsing;

import db.model.AllWords;

import java.util.List;

/**
 * Created by ercan on 17.12.2016.
 */

public class WordAlgorithm {

    public AllWords findClosestWord(List<AllWords> wordList, String word){
        AllWords closestWord = null;
        int minDistance = 999;
        int tempDistance;
        int count = 0;
        int length = word.length();
        System.out.println(wordList.size() + " " + word);
        for(AllWords w: wordList){
            tempDistance = editDistance(word, w.getWord(), length, w.getWord().length());
            if(tempDistance <15)
                return w;
            if (tempDistance < minDistance) {
                minDistance = tempDistance;
                closestWord = w;
            }
            System.out.println(count++);
        }
        return closestWord;
    }

    private int editDistance(String word1 , String word2 , int length1 ,int length2) {
        if (length1 == 0) return length2 * 5;
        if (length2 == 0) return length1 * 3;

        if (word1.charAt(length1 - 1) == word2.charAt(length2 - 1))
            return editDistance(word1, word2, length1 - 1, length2 - 1);

        return min ( editDistance(word1,  word2, length1, length2 - 1) + 5,
                editDistance(word1,  word2, length1 - 1, length2) + 3,
                editDistance(word1,  word2, length1 - 1, length2 - 1) + 1);
    }

    private int min(int x,int y,int z) {
        if (x<y && x<z) return x;
        if (y<x && y<z) return y;
        else return z;
    }
}
