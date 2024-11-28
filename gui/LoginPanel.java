package gui;

import javax.swing.*;

import services.UserService;

public class LoginPanel extends JPanel {
    public LoginPanel() {
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

        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);
    }
}