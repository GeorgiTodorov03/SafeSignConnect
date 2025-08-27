package Controllers.Dao.Impl;

import Controllers.Dao.UserDAO;
import Models.User;
import Utils.DatabaseConnection;
import org.mindrot.jbcrypt.BCrypt;

import javax.xml.crypto.Data;
import java.sql.*;
import java.time.LocalDateTime;

public class UserDAOImpl implements UserDAO {


    @Override
    public void addUser(User user) {
        String sql = "INSERT INTO users (username, email, password_hash) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPasswordHash()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try(Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();

            if(rs.next()) {
               return mapRowToUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try(Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();

            if(rs.next()) {
               return mapRowToUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";

        try(Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();

            if(rs.next()) {
               return mapRowToUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateUser(User user) {
        String sql = "UPDATE users SET username = ?, email = ?, password_hash = ? WHERE id = ?";
        try(Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPasswordHash()));
            statement.setLong(4, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try(Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateLastLogin(int id) {
        String sql = "UPDATE users SET last_login = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void incrementFailedAttempts(int id) {
        String sql = "UPDATE users SET failed_attempts = failed_attempts + 1, WHERE id = ?";

        try(Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resetFailedAttempts(int id) {
        String sql = "UPDATE users SET failed_attempts = 0 WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void lockUser(int id) {
        String sql = "UPDATE users SET is_locked = TRUE WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Helper methods
    private String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    public boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    private User mapRowToUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getLong("id"),
                rs.getString("username"),
                rs.getString("email"),
                rs.getString("password_hash"),
                rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null,
                rs.getTimestamp("last_login") != null ? rs.getTimestamp("last_login").toLocalDateTime() : null,
                rs.getInt("failed_attempts"),
                rs.getBoolean("is_locked")
        );
    }
}
