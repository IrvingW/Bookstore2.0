package dao;

import model.Category1;
import model.Category2;

import java.util.List;

public interface CategoryDao extends BaseDao {
    List<Category1> getAllCategory();
    Category1 getCategory1ByID(int category1ID);
    Category2 getCategory2ByID(int category2ID);
}
