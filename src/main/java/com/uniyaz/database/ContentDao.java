package com.uniyaz.database;

import com.uniyaz.domain.Content;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextField;

import javax.sql.rowset.serial.SerialBlob;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContentDao {

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    List<Content> contentList = new ArrayList<Content>();

    public void addContent(Content content) throws SQLException, ClassNotFoundException, FileNotFoundException {
        connection = DatabaseConnection.getConnection();
        String sql = "INSERT INTO UNIVERSAL.CONTENT (NAME,CategoryID,Data,Image) VALUES(?,?,?,?)";
        preparedStatement = connection.prepareStatement(sql);
        Blob blob = new SerialBlob(content.getImage());
        preparedStatement.setString(1, content.getName());
        preparedStatement.setString(2, String.valueOf(content.getCategory().getId()));
        preparedStatement.setString(3, content.getData());
        preparedStatement.setBlob(4,blob);
        preparedStatement.execute();
    }
    public void deleteContent(Content content) throws SQLException, ClassNotFoundException {
        connection = DatabaseConnection.getConnection();
        String sql = "DELETE FROM UNIVERSAL.CONTENT WHERE ID= ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, String.valueOf(content.getId()));
        preparedStatement.execute();
    }
    public void updateContent(Content content) throws SQLException, ClassNotFoundException {
        connection = DatabaseConnection.getConnection();
        String sql = "UPDATE UNIVERSAL.CONTENT SET Name=?,Data=? WHERE ID= ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, content.getName());
        preparedStatement.setString(2, content.getData());
        preparedStatement.setString(3, String.valueOf(content.getId()));
        preparedStatement.execute();
    }

    public List<Content> getContents() throws SQLException, ClassNotFoundException {

        connection = DatabaseConnection.getConnection();
        String sql="SELECT co.*,ca.NAME as catName FROM UNIVERSAL.CONTENT co INNER JOIN UNIVERSAL.Category  ca ON co.CategoryID = ca.ID" ;
        preparedStatement = connection.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Content content = new Content();

            int id = resultSet.getInt(1);
            content.setId(id);

            String name = resultSet.getString("NAME");
            content.setName(name);

            String data = resultSet.getString("Data");
            content.setData(data);

            Blob imageBlob = resultSet.getBlob("Image");
            byte[] imagebytes = imageBlob.getBytes(1, (int) imageBlob.length());
            content.setImage(imagebytes);

            int categoryID= resultSet.getInt("CategoryID");
            content.getCategory().setId(categoryID);

            String categoryName= resultSet.getString("catName");
            content.getCategory().setName(categoryName);

            contentList.add(content);
        }
        return contentList;
    }

    public List<Content> getContents2(String categoryID) throws SQLException, ClassNotFoundException {

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

            Blob imageBlob = resultSet.getBlob("Image");
            byte[] imagebytes = imageBlob.getBytes(1, (int) imageBlob.length());
            content.setImage(imagebytes);

            contentList.add(content);
        }
        return contentList;
    }
}
