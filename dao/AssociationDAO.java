package dao;
import models.Association;
import models.Donor;
import utils.DBConnection;

import java.sql.*;
public class AssociationDAO {
    public static boolean addAssociation(Association Association) {
        String sqlQuery = "INSERT INTO association (name, location, user_id) VALUES (?, ?, ?)";
        Connection conn = DBConnection.getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, Association.getName());
            statement.setString(2, Association.getLocation());
            statement.setInt(3, Association.getUser().getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}