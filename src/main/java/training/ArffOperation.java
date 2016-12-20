package training;

import model.ParsedTweet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

            for(ParsedTweet parsedTweet: parsedTweetList) {
                StringBuilder stringBuilder = new StringBuilder();
                String[] words = parsedTweet.getOrderedWords().split("-");
                bw.write("'");

                for(String word: words) {
                    stringBuilder.append(word + " ");
                }

                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                bw.write(stringBuilder.toString());
                bw.write("'," + parsedTweet.getCategory().getId());
                bw.write("\n");
            }

            bw.flush();
            bw.close();

            return true;
        } catch (IOException e) {
            System.out.println("createArff:" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
