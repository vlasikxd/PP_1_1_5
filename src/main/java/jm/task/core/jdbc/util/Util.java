package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private final Connection connection;

    public Util() throws SQLException {
        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public Connection getConnection() {
        return connection;
    }

    private static final SessionFactory sessionFactory;

    static {
        Configuration configuration = new Configuration();
        Properties properties = new Properties();
        properties.put(Environment.URL, URL);
        properties.put(Environment.USER, USERNAME);
        properties.put(Environment.PASS, PASSWORD);
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

        properties.put(Environment.SHOW_SQL, "true");

        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

        properties.put(Environment.HBM2DDL_AUTO, "");

        sessionFactory = configuration.addAnnotatedClass(User.class).setProperties(properties).buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {

        return sessionFactory;
    }


}


