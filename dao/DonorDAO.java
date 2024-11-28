package dao;

import models.Donor;
import utils.DBConnection;

import java.sql.*;

public class DonorDAO {
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
}