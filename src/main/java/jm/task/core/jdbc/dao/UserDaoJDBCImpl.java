package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    private void doStatement(String query) {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createUsersTable() {
        final String query = "CREATE TABLE users (id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(45), " +
                "lastName VARCHAR(45), " +
                "age TINYINT);";
        doStatement(query);
    }

    public void dropUsersTable() {
        final String query = "DROP TABLE users;";
        doStatement(query);
    }

    public void saveUser(String name, String lastName, byte age) {
        final String query = String.format("INSERT INTO users(name, lastName, age) VALUES('%s','%s','%d');", name, lastName, age);
        doStatement(query);
        System.out.println("User с именем - " + name + " добавлен в базу данных");
    }

    public void removeUserById(long id) {
        final String query = String.format("DELETE FROM users WHERE id= %d;",id);
        doStatement(query);
    }

    public List<User> getAllUsers() {
        List<User> resultList= new ArrayList<>();
        final String query = "SELECT * FROM users";

        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(query)) {
            ResultSet resSet = preparedStatement.executeQuery();
            while (resSet.next()) {
                resultList.add(new User(resSet.getLong("id"),
                        resSet.getString("name"),
                        resSet.getString("lastName"),
                        resSet.getByte("age") ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultList;
    }

    public void cleanUsersTable() {
        final String query = "TRUNCATE TABLE users";
        doStatement(query);

    }
}
