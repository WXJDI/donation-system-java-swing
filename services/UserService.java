package services;

import dao.UserDAO;
import models.User;
import models.Donor ;
import models.Association ;
import dao.DonorDAO ;
import dao.AssociationDAO ;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class UserService {
    private UserDAO userDAO = new UserDAO();
    private DonorDAO donorDAO = new DonorDAO(); // Declare the DonorDAO
    private AssociationDAO associationDAO = new AssociationDAO() ; // Declare the AssociationDAO

    public User registerUser(String username, String password, String email) throws SQLIntegrityConstraintViolationException {
        try {
            User user = new User(0, username, password, email);
            return userDAO.addUser(user);
        } catch (SQLIntegrityConstraintViolationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String loginUser(String username, String password) {
        Donor donor = donorDAO.getDonorByUsername(username);
        if (donor != null && donor.getUser().getPassword().equals(password)) {
            return "donor"; // If a valid donor, return "donor"
        }

        // Then check if the user is an association
        Association association = associationDAO.getAssociationByUsername(username);
        if (association != null && association.getUser().getPassword().equals(password)) {
            return "association"; // If a valid association, return "association"
        }

        return "invalid"; // If the user is neither a donor nor an association, return "invalid"
    }
    }
