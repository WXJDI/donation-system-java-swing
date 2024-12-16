package dao;

import models.Association;
import models.User;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AssociationDAO {
    public static boolean addAssociation(Association Association) {
        String sqlQuery = "INSERT INTO Association (name, location, user_id) VALUES (?, ?, ?)";
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

    public Association getAssociationByUsername(String username) {
        String sqlQuery = "SELECT a.id, a.name, a.location, u.id AS user_id, u.username, u.password, u.email " +
                "FROM Association a " +
                "INNER JOIN User u ON a.user_id = u.id " +
                "WHERE u.username = ?";


        Connection conn = DBConnection.getConnection();

        try {
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Create the User object first
                User user = new User(
                        resultSet.getInt("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email")
                );

                // Create and return the Association object with the User
                return new Association(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("location"),
                        user // Pass the User object
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no matching association is found
    }




}