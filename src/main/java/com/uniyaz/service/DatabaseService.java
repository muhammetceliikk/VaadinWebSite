package com.uniyaz.service;

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
    public void addContent(Content content) throws SQLException, ClassNotFoundException, FileNotFoundException {
        contentDao= new ContentDao();
        contentDao.addContent(content);
    }
    public void deleteContent(Content content) throws SQLException, ClassNotFoundException {
        contentDao= new ContentDao();
        contentDao.deleteContent(content);
    }
    public void updateContent(Content content) throws SQLException, ClassNotFoundException {
        contentDao= new ContentDao();
        contentDao.updateContent(content);
    }
    public List<Content> getContents() throws SQLException, ClassNotFoundException {
        contentDao= new ContentDao();
        return contentDao.getContents();
    }
    public List<Content> getContents2(String id) throws SQLException, ClassNotFoundException {
        contentDao= new ContentDao();
        return contentDao.getContents2(id);
    }
}
