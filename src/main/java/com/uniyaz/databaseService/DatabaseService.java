package com.uniyaz.databaseService;

import com.uniyaz.database.CategoryDao;
import com.uniyaz.database.ContentDao;
import com.uniyaz.domain.Category;
import com.uniyaz.domain.Content;

import java.sql.SQLException;
import java.util.List;

public class DatabaseService {
    public CategoryDao categoryDao;
    public ContentDao contentDao;

    public void AddCategory(String name) throws SQLException, ClassNotFoundException {
        categoryDao= new CategoryDao();
        categoryDao.addCategory(name);
    }
    public void DeleteCategory(String id) throws SQLException, ClassNotFoundException {
        categoryDao= new CategoryDao();
        categoryDao.deleteCategory(id);
    }
    public List<Category> GetCategories() throws SQLException, ClassNotFoundException {
        categoryDao= new CategoryDao();
        return categoryDao.getCategories();
    }

    public void AddContent(String categoryId,String name,String data) throws SQLException, ClassNotFoundException {
        contentDao= new ContentDao();
        contentDao.addContent(categoryId,name,data);
    }
    public void DeleteContent(String id) throws SQLException, ClassNotFoundException {
        contentDao= new ContentDao();
        contentDao.deleteContent(id);
    }
    public List<Content> GetContents(String id) throws SQLException, ClassNotFoundException {
        contentDao= new ContentDao();
        return contentDao.getContents(id);
    }

}
