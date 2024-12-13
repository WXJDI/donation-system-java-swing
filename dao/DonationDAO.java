package dao;

import models.Donation;
import models.Donor;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DonationDAO {
    private DonorDAO donorDAO;

    public boolean addDonation(Donation donation) {
        String sqlQuery = "INSERT INTO Donation (type, description, quantity, isAvailable, donor_id) values (?, ?, ?, ?, ?)";
        Connection conn = DBConnection.getConnection();

        try {
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, donation.getType());
            statement.setString(2, donation.getDescription());
            statement.setInt(3, donation.getQuantity());
            statement.setBoolean(4, donation.isAvailable());
            statement.setInt(5, donation.getDonor().getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteDonationByDonationId(int donationId) {
        String sqlQuery = "DELETE FROM Donation WHERE id = ?";
        Connection conn = DBConnection.getConnection();

        try {
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setInt(1, donationId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateDonation(Donation donation) {
        String sqlQuery = "UPDATE Donation SET type = ?, description = ?, quantity = ?, isAvailable = ?, donor_id = ? WHERE id = ?";
        Connection conn = DBConnection.getConnection();

        try {
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, donation.getType());
            statement.setString(2, donation.getDescription());
            statement.setInt(3, donation.getQuantity());
            statement.setBoolean(4, donation.isAvailable());
            statement.setInt(5, donation.getDonor().getId());
            statement.setInt(6, donation.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Donation getDonationByDonationId(Donor donor, int donationId) {
        Donation donation = null;
        String sqlQuery = "SELECT * FROM Donation WHERE id = ?";
        Connection conn = DBConnection.getConnection();

        try {
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setInt(1, donationId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                donation = new Donation(resultSet.getInt("id"), resultSet.getString("type"), resultSet.getString("description"), resultSet.getInt("quantity"), donor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return donation;
    }

    public ArrayList<Donation> getAllDonationsByDonorId(int donorId) {
        donorDAO = new DonorDAO();
        ArrayList<Donation> donations = new ArrayList<>();
        String sqlQuery = "SELECT * FROM Donation WHERE donor_id = ?";
        Connection conn = DBConnection.getConnection();

        try {
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setInt(1, donorId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Donor donor = donorDAO.getDonorByDonorId(donorId);
                Donation donation = new Donation(resultSet.getInt("id"), resultSet.getString("type"), resultSet.getString("description"), resultSet.getInt("quantity"), donor);
                donations.add(donation);
            }
            return donations;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return donations;
    }
}