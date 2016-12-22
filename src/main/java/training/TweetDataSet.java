package training;

import weka.core.Attribute;
import weka.core.FastVector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mgunes on 20.12.2016.
 */
public class TweetDataSet {
    private Attribute text;
    private Attribute category;
    private List categoryList;
    private List<Attribute> features;

    public TweetDataSet() {
        text = new Attribute("textAtt", (FastVector) null);

        categoryList = new ArrayList<String>(2);
        //categoryList.add("spor");
        //categoryList.add("siyaset");
        categoryList.add("ekonomi");
        //categoryList.add("eğlence");
        categoryList.add("sağlık");
        //categoryList.add("bilim-teknoloji");

        category = new Attribute("categoryAtt", categoryList);

        features = new ArrayList<Attribute>();
        features.add(text);
        features.add(category);
    }

    public Attribute getText() {
        return text;
    }

    public void setText(Attribute text) {
        this.text = text;
    }

    public Attribute getCategory() {
        return category;
    }

    public void setCategory(Attribute category) {
        this.category = category;
    }

    public List getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List categoryList) {
        this.categoryList = categoryList;
    }

    public List<Attribute> getFeatures() {
        return features;
    }

    public void setFeatures(List<Attribute> features) {
        this.features = features;
    }
}
