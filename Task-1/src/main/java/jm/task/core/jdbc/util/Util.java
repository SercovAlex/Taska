package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String PASSWORD_key = "db.password";
    private static final String USERNAME_key = "db.username";
    private static final String URL_key = "db.url";

    private Util() {
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    PropertiesUtil.get(URL_key),
                    PropertiesUtil.get(USERNAME_key),
                    PropertiesUtil.get(PASSWORD_key)

            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static {
        loadDriver();
    }
    private static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    // реализуйте настройку соеденения с БД
}
