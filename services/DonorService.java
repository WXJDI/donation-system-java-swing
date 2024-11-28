package services;

import dao.DonorDAO;
import models.Donor;
import models.User;

public class DonorService {
    private DonorDAO donorDAO = new DonorDAO();
    private UserService userService = new UserService();

    public Donor registerDonorUser(String username, String password, String email, String name, String address) {
        User user = userService.registerUser(username, password, email);
        if (user != null) {
            Donor donor = new Donor(0, name, address, user);
            boolean donorCreated = donorDAO.addDonor(donor);
            if (donorCreated)
                return donor;
        }
        return null;
    }
}