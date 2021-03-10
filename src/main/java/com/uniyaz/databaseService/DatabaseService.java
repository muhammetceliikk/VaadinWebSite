package com.uniyaz.databaseService;

import com.uniyaz.database.CategoryDao;
import com.uniyaz.database.ContentDao;
import com.uniyaz.domain.Category;
import com.uniyaz.domain.Content;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextField;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

public class DatabaseService {
    public CategoryDao categoryDao;
    public ContentDao contentDao;

    public void addCategory(Category category) throws SQLException, ClassNotFoundException {
        categoryDao= new CategoryDao();
        categoryDao.addCategory(category);
    }
    public void deleteCategory(Category category) throws SQLException, ClassNotFoundException {
        categoryDao= new CategoryDao();
        categoryDao.deleteCategory(category);
    }
    public void updateCategory(Category category, TextField textField) throws SQLException, ClassNotFoundException {
        categoryDao= new CategoryDao();
        categoryDao.updateCategory(category,textField);
    }
    public List<Category> getCategories() throws SQLException, ClassNotFoundException {
        categoryDao= new CategoryDao();
        return categoryDao.getCategories();
    }
    public void addContent(Category category,Content content) throws SQLException, ClassNotFoundException, FileNotFoundException {
        contentDao= new ContentDao();
        contentDao.addContent(category,content);
    }
    public void deleteContent(Content content) throws SQLException, ClassNotFoundException {
        contentDao= new ContentDao();
        contentDao.deleteContent(content);
    }
    public void updateContent(Content content, TextField contentName,RichTextArea richTextArea) throws SQLException, ClassNotFoundException {
        contentDao= new ContentDao();
        contentDao.updateContent(content,contentName,richTextArea);
    }
    public List<Content> getContents(String id) throws SQLException, ClassNotFoundException {
        contentDao= new ContentDao();
        return contentDao.getContents(id);
    }
}
