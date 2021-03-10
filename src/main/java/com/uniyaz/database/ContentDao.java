package com.uniyaz.database;

import com.uniyaz.domain.Category;
import com.uniyaz.domain.Content;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContentDao {

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    List<Content> contentList = new ArrayList<Content>();

    public void addContent(Category category, Content content) throws SQLException, ClassNotFoundException {
        connection = DatabaseConnection.getConnection();
        String sql = "INSERT INTO UNIVERSAL.CONTENT (NAME,CategoryID,Data) VALUES(?,?,?)";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, content.getName());
        preparedStatement.setString(2, String.valueOf(category.getId()));
        preparedStatement.setString(3, content.getData());
        preparedStatement.execute();
    }
    public void deleteContent(Content content) throws SQLException, ClassNotFoundException {
        connection = DatabaseConnection.getConnection();
        String sql = "DELETE FROM UNIVERSAL.CONTENT WHERE ID= ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, String.valueOf(content.getId()));
        preparedStatement.execute();
    }

    public List<Content> getContents(String categoryID) throws SQLException, ClassNotFoundException {

        connection = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM UNIVERSAL.CONTENT WHERE CategoryID=?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, categoryID);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Content content = new Content();

            int id = resultSet.getInt(1);
            content.setId(id);

            String name = resultSet.getString("NAME");
            content.setName(name);

            String data = resultSet.getString("Data");
            content.setData(data);

            contentList.add(content);
        }
        return contentList;
    }
}
