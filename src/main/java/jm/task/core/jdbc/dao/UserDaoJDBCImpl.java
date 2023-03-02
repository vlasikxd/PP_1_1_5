package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    private void doStatement(String query) {
        try (Connection connection = new Util().getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void createUsersTable() {
        final String query = "CREATE TABLE IF NOT EXISTS users (id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(45), " +
                "lastName VARCHAR(45), " +
                "age TINYINT);";
        doStatement(query);
    }

    public void dropUsersTable() {
        final String query = "DROP TABLE users;";
        doStatement(query);
    }

    public void cleanUsersTable() {
        final String query = "TRUNCATE TABLE users";
        doStatement(query);
    }

    public void saveUser(String name, String lastName, byte age) {
        final String query = "INSERT INTO users(name, lastName, age) VALUES(?,?,?)";
        try (Connection connection = new Util().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    public void removeUserById(long id) {
        final String query = "DELETE FROM users WHERE id = ?";

        try (Connection connection = new Util().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        final String query = "SELECT * FROM users";
        List<User> resultList = new ArrayList<>();

        try (Connection connection = new Util().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery(query)) {
            while (resultSet.next()) {
                resultList.add(new User(resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return resultList;
    }
}
