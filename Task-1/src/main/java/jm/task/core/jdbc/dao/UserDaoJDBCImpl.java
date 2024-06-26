package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }


    private static final String ADD_TABLE_SQL = """
            CREATE TABLE IF NOT EXISTS users (
            id SERIAL PRIMARY KEY,
            name VARCHAR(20),
            lastname VARCHAR(20),
            age INT)
            """;

    public void createUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
             {
                System.out.println(connection.getTransactionIsolation());
                statement.execute(ADD_TABLE_SQL);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating table", e);
        }
    }

    private static final String DROP_TABLE_SQL = """
            DROP TABLE IF EXISTS users
            """;

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
           {
                System.out.println(connection.getTransactionIsolation());
                statement.execute(DROP_TABLE_SQL);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error dropping table", e);
        }
    }

    private static final String SAVE_USER_SQL = """
            INSERT INTO users (name, lastname, age) VALUES (?, ?, ?)
            """;

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_USER_SQL)) {
           {
                statement.setString(1, name);
                statement.setString(2, lastName);
                statement.setByte(3, age);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving user", e);
        }
    }

    private static final String REMOVE_USER_SQL = """
            DELETE FROM users WHERE id = ?
            """;

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_USER_SQL)) {
           {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error removing user", e);
        }
    }

    private static final String GET_USER_SQL = """
            SELECT id, name, lastname, age FROM users
            """;

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_SQL)) {
           {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    User currentUser = new User(
                            resultSet.getString("name"),
                            resultSet.getString("lastname"),
                            resultSet.getByte("age")
                    );
                    currentUser.setId(resultSet.getLong("id"));
                    users.add(currentUser);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving users", e);
        }
        return users;
    }

    private static final String CLEAN_USERS_SQL = """
            TRUNCATE TABLE users RESTART IDENTITY
            """;

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CLEAN_USERS_SQL)) {
            {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error cleaning users table", e);
        }
    }
}

    
