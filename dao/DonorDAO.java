package dao;

import models.Donor;
import models.User;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DonorDAO {
    private UserDAO userDAO;

    public boolean addDonor(Donor donor) {
        String sqlQuery = "INSERT INTO Donor (name, address, user_id) VALUES (?, ?, ?)";
        Connection conn = DBConnection.getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, donor.getName());
            statement.setString(2, donor.getAddress());
            statement.setInt(3, donor.getUser().getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

   public Donor getDonorByDonorId(int DonorId) {
        userDAO = new UserDAO();
        String sqlQuery = "SELECT * FROM Donor WHERE id = ?";
        Connection conn = DBConnection.getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setInt(1, DonorId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = userDAO.getUserByUserId(resultSet.getInt("user_id"));
                Donor donor = new Donor(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("address"), user);
                return donor;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Donor getDonorByUsername(String username) {
        userDAO = new UserDAO();
        User user = userDAO.getUserByUsername(username);
        Donor donor = null;
        String sqlQuery = "SELECT * FROM Donor WHERE user_id = ?";
        Connection conn = DBConnection.getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setInt(1, user.getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                donor = new Donor(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("address"), user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return donor;
    }
}