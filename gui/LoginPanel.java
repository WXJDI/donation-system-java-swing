package gui;

import services.UserService;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    public LoginPanel(JPanel mainPanel, CardLayout cardLayout) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel usernameLabel = new JLabel("Username: ");
        JTextField usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password");
        JPasswordField passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(actionEvent -> {
            UserService userService = new UserService();
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (userService.loginUser(username, password) != null) {
                JOptionPane.showMessageDialog(this, "Login Successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Credentials!");
            }
        });

        JButton registerDonorButton = new JButton("register as a donor");
        registerDonorButton.addActionListener(actionEvent -> {
            cardLayout.show(mainPanel, "REGISTER_DONOR");
        });

        JButton registerAssociationButton = new JButton("register as an association");
        registerAssociationButton.addActionListener(actionEvent -> {
            cardLayout.show(mainPanel, "REGISTER_ASSOCIATION");
        });

        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);
        add(registerDonorButton);
        add(registerAssociationButton);
    }
}