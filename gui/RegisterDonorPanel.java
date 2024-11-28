package gui;

import models.Donor;
import services.DonorService;

import javax.swing.*;
import java.awt.*;

public class RegisterDonorPanel extends JPanel {
    public RegisterDonorPanel(JPanel mainPanel, CardLayout cardLayout) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel usernameLabel = new JLabel("username: ");
        JTextField usernameField = new JTextField();

        JLabel emailLabel = new JLabel("email: ");
        JTextField emailField = new JTextField();

        JLabel nameLabel = new JLabel("name: ");
        JTextField nameField = new JTextField();

        JLabel addressLabel = new JLabel("address: ");
        JTextField addressField = new JTextField();

        JLabel passwordLabel = new JLabel("password: ");
        JPasswordField passwordField = new JPasswordField();

        JLabel confirmPasswordLabel = new JLabel("confirm password: ");
        JPasswordField confirmPasswordField = new JPasswordField();

        JButton registerDonorButton = new JButton("register donor");
        registerDonorButton.addActionListener(actionEvent -> {
            DonorService donorService = new DonorService();
            String username = usernameField.getText();
            String name = nameField.getText();
            String email = emailField.getText();
            String address = addressField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "passwords does not match!");
                return;
            }
            Donor donor = donorService.registerDonorUser(username, password, email, name, address);
            if (donor == null) {
                JOptionPane.showMessageDialog(this, "An error has occurred. Try again!");
                return;
            }
            JOptionPane.showMessageDialog(this, "Donor registered!");
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
        add(addressLabel);
        add(addressField);
        add(passwordLabel);
        add(passwordField);
        add(confirmPasswordLabel);
        add(confirmPasswordField);
        add(registerDonorButton);
        add(loginButton);
    }
}