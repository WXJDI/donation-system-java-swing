package services;

import dao.UserDAO;
import models.User;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    public User registerUser(String username, String password, String email) {
        User user = new User(0, username, password, email);
        boolean userAdded = userDAO.addUser(user);
        if (userAdded)
            return user;
        return null;
    }

    public User loginUser(String username, String password) {
        User user = userDAO.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}