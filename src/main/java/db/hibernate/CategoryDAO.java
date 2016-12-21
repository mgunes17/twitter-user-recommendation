package db.hibernate;

import db.model.Category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mgunes on 18.12.2016.
 */
public class CategoryDAO extends AbstractDAO {

    public List<Category> getCategoryList(){
        return getAllRows("Category");
    }

    public Map<Integer, Category> getCategoryAsMap(){
        List<Category> categoryList = getCategoryList();
        Map<Integer, Category> categoryMap = new HashMap<Integer, Category>();

        for(Category category : categoryList){
            categoryMap.put(category.getId(), category);
        }
        return categoryMap;
    }
}
