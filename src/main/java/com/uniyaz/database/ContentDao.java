package com.uniyaz.database;

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

    public void addContent(String categoryId,String name,String data) throws SQLException, ClassNotFoundException {
        connection = DatabaseConnection.getConnection();
        String sql = "INSERT INTO UNIVERSAL.CONTENT (NAME,CategoryID,Data) VALUES(?,?,?)";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, categoryId);
        preparedStatement.setString(3, data);
        preparedStatement.execute();
    }
    public void deleteContent(String id) throws SQLException, ClassNotFoundException {
        connection = DatabaseConnection.getConnection();
        String sql = "DELETE FROM UNIVERSAL.CONTENT WHERE ID= ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, id);
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

            System.out.println(id);

            String name = resultSet.getString("NAME");
            content.setName(name);

            System.out.println(name+"-----");
            String data = resultSet.getString("Data");
            content.setData(data);

            contentList.add(content);
        }
        return contentList;
    }
}
