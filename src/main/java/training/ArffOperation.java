package training;

import db.hibernate.WordCategoryFrequencyDAO;
import db.model.ParsedTweet;
import db.model.WordCategoryFrequency;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by mgunes on 20.12.2016.
 */
public class ArffOperation {
    private String fileName;
    private String path;
    private String relationName;

    public ArffOperation(String fileName, String path, String relationName) {
        this.fileName = fileName;
        this.path = path;
        this.relationName = relationName;
    }
    public boolean createArff(List<ParsedTweet> parsedTweetList) {
        File file = new File(path + "/" + fileName + ".arff");

        try {
            FileWriter fw = null;
            fw = new FileWriter(file, false);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("@RELATION " + relationName);
            bw.write("\n\n");
            bw.write("@ATTRIBUTE Text string");
            bw.write("\n");
            bw.write("@ATTRIBUTE category {1,2,3,4,5,6}");
            bw.write("\n\n");
            bw.write("@DATA");
            bw.write("\n");

            List<String> wordsAsStrings = getSortedWordListAsString();
            int count = 0;
            for(ParsedTweet parsedTweet: parsedTweetList) {
                StringBuilder stringBuilder = new StringBuilder();
                String[] words = parsedTweet.getOrderedWords().split("-");

                if(parsedTweet.getCategory().getId() != 4 && parsedTweet.getCategory().getId() != 6) {
                    stringBuilder.append("'");
                    for (String word : words) {
                        /*if (!word.equals("olmak")
                                && !word.equals("etmek")
                                && !word.equals("yeni")
                                && !word.equals("demek")
                                && !word.equals("yapmak")
                                && !word.equals("gelmek")
                                && !word.equals("gitmek")
                                && !word.equals("bugün"))*/
                        if(isWordUnique(word, wordsAsStrings)){
                            count++;
                            stringBuilder.append(word + " ");
                        }
                    }
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                    if(stringBuilder.length() != 0) {
                        bw.write(stringBuilder.toString());
                        bw.write("'," + parsedTweet.getCategory().getId());
                        bw.write("\n");
                    }
                }
            }

            bw.flush();
            bw.close();
            System.out.println(count);
            return true;
        } catch (IOException e) {
            System.out.println("createArff:" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private boolean isWordUnique(String word, List<String> wordList){
        boolean result = false;

        if(wordList.indexOf(word) == wordList.lastIndexOf(word)){
            result = true;
        }
        return result;
    }

    private List<String> getSortedWordListAsString(){
        WordCategoryFrequencyDAO wordCategoryFrequencyDAO = new WordCategoryFrequencyDAO();
        List<WordCategoryFrequency> wordCategoryFrequencyList = wordCategoryFrequencyDAO.getWordFrequencyList();
        List<String> wordsAsStrings = new ArrayList<String>();

        for(WordCategoryFrequency wf : wordCategoryFrequencyList){
            wordsAsStrings.add(wf.getWordFrequencyPK().getWord());
        }

        Collections.sort(wordsAsStrings, new Comparator<String>() {
            public int compare(final String str1, final String str2) {
                return str1.compareTo(str2);
            }
        });
        return wordsAsStrings;
    }
}
