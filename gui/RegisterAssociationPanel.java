package gui;

import models.Association;
import services.AssociationService;

import javax.swing.*;
import java.awt.*;

public class RegisterAssociationPanel extends JPanel {
    public RegisterAssociationPanel(JPanel mainPanel, CardLayout cardLayout) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel usernameLabel = new JLabel("username: ");
        JTextField usernameField = new JTextField();

        JLabel emailLabel = new JLabel("email: ");
        JTextField emailField = new JTextField();

        JLabel nameLabel = new JLabel("name: ");
        JTextField nameField = new JTextField();

        JLabel locationLabel = new JLabel("location: ");
        JTextField locationField = new JTextField();

        JLabel passwordLabel = new JLabel("password: ");
        JPasswordField passwordField = new JPasswordField();

        JLabel confirmPasswordLabel = new JLabel("confirm password: ");
        JPasswordField confirmPasswordField = new JPasswordField();

        JButton registerAssociationButton = new JButton("register association");
        registerAssociationButton.addActionListener(actionEvent -> {
            AssociationService associationService = new AssociationService();
            String username = usernameField.getText();
            String name = nameField.getText();
            String email = emailField.getText();
            String location = locationField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "passwords do not match!");
                return;
            }
            Association association = associationService.registerAssociationUser(username, password, email, name, location);
            if (association == null) {
                JOptionPane.showMessageDialog(this, "An error has occurred. Try again!");
                return;
            }
            JOptionPane.showMessageDialog(this, "Association registered!");
        });

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(actionEvent -> {
            cardLayout.show(mainPanel, "LOGIN");
        });

        add(usernameLabel);
        add(usernameField);
        add(emailLabel);
        add(emailField);
        add(nameLabel);
        add(nameField);
        add(locationLabel);
        add(locationField);
        add(passwordLabel);
        add(passwordField);
        add(confirmPasswordLabel);
        add(confirmPasswordField);
        add(registerAssociationButton);
        add(loginButton);
    }
}
