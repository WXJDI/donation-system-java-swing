package dao;

import models.DonationCollection;
import utils.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class DonationCollectionDAO {
    public boolean collectDonation(int associationId, int donationId, int quantityToCollect) throws SQLException {
        String insertQuery = """
                INSERT INTO DonationCollection
                (donation_id, association_id, quantity, dateDonationCollected)
                VALUES (?, ?, ?, ?);
                """;
        String updateQuantityQuery = """
                UPDATE Donation
                SET quantity = quantity - ?
                WHERE id = ?;
                """;
        String updateAvailabilityQuery = """
                UPDATE Donation
                SET isAvailable = FALSE
                WHERE id = ? AND quantity <= 0;
                """;
        Connection conn = DBConnection.getConnection();
        conn.setAutoCommit(false);

        try {
            PreparedStatement insertStatement = conn.prepareStatement(insertQuery);
            insertStatement.setInt(1, donationId);
            insertStatement.setInt(2, associationId);
            insertStatement.setInt(3, quantityToCollect);
            insertStatement.setDate(4, Date.valueOf(LocalDate.now()));
            int insertRowsAffected = insertStatement.executeUpdate();

            PreparedStatement updateQuantityStatement = conn.prepareStatement(updateQuantityQuery);
            updateQuantityStatement.setInt(1, quantityToCollect);
            updateQuantityStatement.setInt(2, donationId);
            int updateRowsAffected = updateQuantityStatement.executeUpdate();

            PreparedStatement updateAvailabilityStatement = conn.prepareStatement(updateAvailabilityQuery);
            updateAvailabilityStatement.setInt(1, donationId);
            updateAvailabilityStatement.executeUpdate();

            conn.commit();
            return insertRowsAffected > 0 && updateRowsAffected > 0;
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public ArrayList<DonationCollection> getCollectedDonationsForDonor(int donorId) throws SQLException {
        ArrayList<DonationCollection> collectedDonations = new ArrayList<>();
        String sqlQuery = """
                SELECT dc.id, d.type, d.description, dc.quantity, a.name AS association_name, dc.dateDonationCollected
                FROM DonationCollection dc, Donation d, Association a
                WHERE dc.donation_id = d.id AND dc.association_id = a.id
                AND d.donor_id = ?
                """;
        Connection conn = DBConnection.getConnection();

        try {
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setInt(1, donorId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                DonationCollection donationCollection = new DonationCollection(
                        resultSet.getInt("id"),
                        resultSet.getString("type"),
                        resultSet.getString("description"),
                        resultSet.getInt("quantity"),
                        resultSet.getString("association_name"),
                        resultSet.getDate("dateDonationCollected").toLocalDate()
                );
                collectedDonations.add(donationCollection);
            }
        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return collectedDonations;
    }

    public ArrayList<DonationCollection> getDonationsCollectedByAssociation(int associationId) throws SQLException {
        ArrayList<DonationCollection> collectedDonations = new ArrayList<>();
        String sqlQuery = """
                SELECT dc.id, do.type, do.description, dc.quantity, dn.name AS donor_name, dc.dateDonationCollected
                FROM DonationCollection dc, Donor dn, Donation do
                WHERE dc.donation_id = do.id
                AND dn.id = do.donor_id
                AND dc.association_id = ?
                """;
        Connection conn = DBConnection.getConnection();

        try {
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setInt(1, associationId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                DonationCollection donationCollection = new DonationCollection(
                        resultSet.getInt("id"),
                        resultSet.getString("type"),
                        resultSet.getString("description"),
                        resultSet.getInt("quantity"),
                        resultSet.getString("donor_name"),
                        resultSet.getDate("dateDonationCollected").toLocalDate()
                );
                collectedDonations.add(donationCollection);
            }
        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return collectedDonations;
    }
}