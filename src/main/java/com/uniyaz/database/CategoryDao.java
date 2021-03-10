package com.uniyaz.database;

import com.uniyaz.domain.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    List<Category> categoryList = new ArrayList<Category>();

    public void addCategory(Category category) throws SQLException, ClassNotFoundException {
        connection = DatabaseConnection.getConnection();
        String sql = "INSERT INTO UNIVERSAL.CATEGORY (NAME) VALUES(?)";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, category.getName());
        preparedStatement.execute();
    }
    public void deleteCategory(Category category) throws SQLException, ClassNotFoundException {
        connection = DatabaseConnection.getConnection();
        String sql = "DELETE FROM UNIVERSAL.CATEGORY WHERE ID= ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, String.valueOf(category.getId()));
        preparedStatement.execute();
    }

    public List<Category> getCategories() throws SQLException, ClassNotFoundException {

        connection = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM UNIVERSAL.CATEGORY";
        preparedStatement = connection.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Category category = new Category();

            int id = resultSet.getInt(1);
            category.setId(id);

            String name = resultSet.getString("NAME");
            category.setName(name);

            categoryList.add(category);
        }
        return categoryList;
    }
}
