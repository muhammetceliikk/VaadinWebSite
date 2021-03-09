package com.uniyaz.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    //alt v,p
    static final String driverName="com.mysql.jdbc.Driver";
    static final String conUrl="jdbc:mysql://localhost/UNIVERSAL";
    static final String root="Muhammet";
    static final String password="12345678";
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(driverName);
        Connection connection= DriverManager.getConnection(conUrl,root,password);
        return connection;
    }
}
