package services;

import dao.UserDAO;
import models.User;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class UserService {
    private UserDAO userDAO = new UserDAO();

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

    public User loginUser(String username, String password) {
        User user = userDAO.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}