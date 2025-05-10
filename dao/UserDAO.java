package dao;

import models.User;
import utils.DBConnection;

import java.sql.*;

public class UserDAO {
    public User addUser(User user) throws SQLIntegrityConstraintViolationException {
        String sql = "INSERT INTO User (username, password, email) VALUES (?, ?, ?)";
        Connection conn = DBConnection.getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    user.setId(id);
                    return user;
                }
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM User WHERE username = ?";
        Connection conn = DBConnection.getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet res = stmt.executeQuery();
            if (res.next()) {
                return new User(res.getInt("id"), res.getString("username"), res.getString("password"), res.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserByUserId(int userId) {
        String sqlQuery = "SELECT * FROM User WHERE id = ?";
        Connection conn = DBConnection.getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User(userId, resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("email"));
                return user;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}