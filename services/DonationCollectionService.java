package services;

import dao.DonationCollectionDAO;
import models.DonationCollection;

import java.sql.SQLException;
import java.util.ArrayList;

public class DonationCollectionService {
    private DonationCollectionDAO donationCollectionDAO = new DonationCollectionDAO();

    public boolean collectDonation(int associationId, int donationId, int quantityToCollect) {
        try {
            return donationCollectionDAO.collectDonation(associationId, donationId, quantityToCollect);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

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