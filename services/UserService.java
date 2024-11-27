package services;

import dao.UserDAO;
import models.User;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    public boolean registerUser(String username, String password, String email) {
        User user = new User(0, username, password, email);
        return userDAO.addUser(user);
    }

    public User loginUser(String username, String password) {
        User user = userDAO.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}