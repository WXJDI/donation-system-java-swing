package services;

import dao.AssociationDAO;
import dao.DonationDAO;
import models.Association;
import models.Donation;
import models.User;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

public class AssociationService {
    private services.UserService userService = new services.UserService();

    public Association registerAssociationUser(String username, String password, String email, String name, String location) throws SQLIntegrityConstraintViolationException {
        try {
            User user = userService.registerUser(username, password, email);
            if (user != null) {
                Association association = new Association(0, name, location, user);
                boolean associationCreated = AssociationDAO.addAssociation(association);
                if (associationCreated)
                    return association;
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Donation> getAvailableDonations() {
        DonationDAO donationDAO = new dao.DonationDAO();
        return donationDAO.getAvailableDonations();
    }

    public boolean collectDonation(int donationId, int quantityToCollect) {
        DonationDAO donationDAO = new DonationDAO();
        return donationDAO.collectDonation(donationId, quantityToCollect);
    }
}