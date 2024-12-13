package services;

import dao.DonationDAO;
import models.Donation;
import models.Donor;

import java.util.ArrayList;

public class DonationService {

    public Donation createDonation(String type, String description, int quantity, Donor donor) {
        DonationDAO donationDAO = new DonationDAO();
        try {
            Donation donation = new Donation(0, type, description, quantity, donor);
            boolean donationCreated = donationDAO.addDonation(donation);
            if (donationCreated)
                return donation;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteDonationById(int donationId) {
        DonationDAO donationDAO = new DonationDAO();
        boolean donationDeleted = false;
        try {
            donationDeleted = donationDAO.deleteDonationByDonationId(donationId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return donationDeleted;
    }

    public Donation editDonation(int donationId, String type, String description, int quantity, Donor donor) {
        DonationDAO donationDAO = new DonationDAO();
        Donation donation = null;
        try {
            donation = new Donation(donationId, type, description, quantity, donor);
            donationDAO.updateDonation(donation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return donation;
    }

    public Donation getDonationById(Donor donor, int donationId) {
        DonationDAO donationDAO = new DonationDAO();
        Donation donation = null;
        try {
            donation = donationDAO.getDonationByDonationId(donor, donationId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return donation;
    }

    public ArrayList<Donation> getDonationsForDonor(int donorId) {
        DonationDAO donationDAO = new DonationDAO();
        ArrayList<Donation> donations = null;
        try {
            donations = donationDAO.getAllDonationsByDonorId(donorId);
            return donations;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return donations;
    }
}