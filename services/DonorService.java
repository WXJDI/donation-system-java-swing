package services;

import dao.DonorDAO;
import models.Donor;
import models.User;

import java.sql.SQLIntegrityConstraintViolationException;

public class DonorService {
    private DonorDAO donorDAO = new DonorDAO();
    private services.UserService userService = new services.UserService();

    public Donor registerDonorUser(String username, String password, String email, String name, String address) throws SQLIntegrityConstraintViolationException {
        try {
            User user = userService.registerUser(username, password, email);
            if (user != null) {
                Donor donor = new Donor(0, name, address, user);
                boolean donorCreated = donorDAO.addDonor(donor);
                if (donorCreated)
                    return donor;
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Donor getDonorByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        Donor donor = donorDAO.getDonorByUsername(username);

        return donor;
    }
}