package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
     Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String rec = "CREATE TABLE IF NOT EXISTS Users\n" +
                "                (id int NOT NULL AUTO_INCREMENT,\n" +
                "                name VARCHAR(20), \n" +
                "                lastName VARCHAR(20),\n" +
                "                age tinyint unsigned," +
                "PRIMARY KEY (id));";
        try (PreparedStatement preparedStatement = connection.prepareStatement(rec)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void dropUsersTable() {
        String rec = "DROP TABLE IF EXISTS Users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(rec)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String rec = "insert into Users ( name, lastname, age) VALUES (?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(rec)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void removeUserById(long id) {
        String rec = "DELETE FROM Users WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(rec)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }

    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String rec = "SELECT * FROM Users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(rec)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                //user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        String rec = " TRUNCATE TABLE Users ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(rec)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
