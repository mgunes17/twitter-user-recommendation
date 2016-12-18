package hibernate;

import model.Category;

import java.util.List;

/**
 * Created by mgunes on 18.12.2016.
 */
public class CategoryDAO extends AbstractDAO {

    public List<Category> getCategoryList(){
        return getAllRows("Category");
    }
}
