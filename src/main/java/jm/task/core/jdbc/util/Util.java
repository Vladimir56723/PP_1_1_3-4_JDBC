package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util  {
    // реализуйте настройку соеденения с БД
    private static final String URL ="jdbc:mysql://localhost:3306/mytest";
    private static final String USERNAME="root";
    private static final String PASSWORD= "root";
    public static Connection getConnection() {
        Connection connection=null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Unable to load class.");
            e.printStackTrace();
        }
        return connection;
    }
}
