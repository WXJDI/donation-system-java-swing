package services;
import dao.DonationDAO ;
import dao.AssociationDAO;
import models.Association;
import models.User;
import models.Donation;

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
    public boolean collectDonation(int associationId, int donationId, int quantityToCollect) {
        DonationDAO donationDAO = new DonationDAO();
        return donationDAO.collectDonation(associationId, donationId, quantityToCollect);
    }




}