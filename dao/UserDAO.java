package dao;

import models.User;
import utils.DBConnection;

import java.sql.*;

public class UserDAO {
    public boolean addUser(User user) {
        String sql = "INSERT INTO User (username, password, email) VALUES (?, ?, ?)";
        Connection conn = DBConnection.getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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
            return null;
        }
    }
}