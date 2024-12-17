package services;

import dao.DonationCollectionDAO;
import models.DonationCollection;

import java.sql.SQLException;
import java.util.ArrayList;

public class DonationCollectionService {
    private DonationCollectionDAO donationCollectionDAO = new DonationCollectionDAO();

    public ArrayList<DonationCollection> getCollectedDonationsForDonor(int donorId) {
        try {
            return donationCollectionDAO.getCollectedDonationsForDonor(donorId);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public ArrayList<DonationCollection> getDonationsCollectedByAssociation(int associationId) {
        try {
            return donationCollectionDAO.getDonationsCollectedByAssociation(associationId);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}