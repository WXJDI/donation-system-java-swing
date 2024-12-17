package dao;

import models.DonationCollection;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DonationCollectionDAO {
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
                SELECT dc.id, do.description, dc.quantity, dn.name AS donor_name, dc.dateDonationCollected
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
}