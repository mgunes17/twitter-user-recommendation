package training;

import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NominalToString;
import weka.filters.unsupervised.attribute.StringToNominal;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by mgunes on 21.12.2016.
 */
public class LearningModel {
    private TweetDataSet tweetDataSet;
    private Instances trainingSet;
    private Instances test;

    public LearningModel(String tweet) {
        tweetDataSet = new TweetDataSet();
        trainingSet = new Instances("tweets", (ArrayList<Attribute>) tweetDataSet.getFeatures(), 419);
        trainingSet.setClassIndex(1);

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader("/home/mgunes/deneme.arff"));
            ArffLoader.ArffReader arff = new ArffLoader.ArffReader(reader);
            trainingSet = arff.getData();
            trainingSet.setClassIndex(1);
        } catch (FileNotFoundException e) {
            System.out.println("LearningModel:" + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("LearningModel:" + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("LearningModel:" + e.getMessage());
            e.printStackTrace();
        }

        StringToWordVector filter1 = new  StringToWordVector();
        try {
            filter1.setInputFormat(trainingSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            trainingSet = Filter.useFilter(trainingSet, filter1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Instance sample = new DenseInstance(2);
        sample.setValue(tweetDataSet.getFeatures().get(0), tweet);
        test = new Instances("test", (ArrayList<Attribute>) tweetDataSet.getFeatures(), 1);
        test.add(sample);

        try {
            filter1.setInputFormat(test);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            test = Filter.useFilter(test, filter1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String trainingNaiveBayes() {
        try {
            NaiveBayes naiveBayes = new NaiveBayes();
            naiveBayes.buildClassifier(trainingSet);
            test.get(0).setDataset(trainingSet);
            double[] result = naiveBayes.distributionForInstance(test.get(0));

            FilteredClassifier classifier = new FilteredClassifier();
            classifier.buildClassifier(trainingSet);
            double[] result2 = classifier.distributionForInstance(test.get(0));
            int a = (int) classifier.classifyInstance(test.get(0));

            String category = findCategory(result);
            return category;
        } catch (Exception e) {
            System.out.println("trainingNaiveBayes " + e.getMessage());
            e.printStackTrace();
            return "hata";
        }
    }

    private String findCategory(double[] result) {
        double max = result[0];
        int maxIndex = 0;

        for(int i = 1; i < result.length - 1; i++) {
            if(result[i] > max) {
                maxIndex = i;
                max = result[i];
            }
        }

        System.out.println("Max :" + result[maxIndex] + "--" + maxIndex);
        return tweetDataSet.getCategoryList().get(maxIndex).toString();
    }
}
