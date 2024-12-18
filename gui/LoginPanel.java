package gui;

import app.GlobalConstants;
import dao.AssociationDAO;
import models.Association;
import models.Donor;
import services.DonorService;
import services.UserService;
import utils.ResourceUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginPanel extends JPanel {
    JPanel mainPanel;
    CardLayout cardLayout;

    public LoginPanel(JPanel mainPanel, CardLayout cardLayout) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;

        setLayout(new GridBagLayout());
        setBackground(GlobalConstants.PRIMARY_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER; // Center components

        JLabel loginImage = ResourceUtils.loadImage(GlobalConstants.LOGIN_IMAGE_PATH);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(loginImage, gbc);

        JLabel titleLabel = new JLabel("Welcome to Donation System");
        titleLabel.setFont(GlobalConstants.TITLE_FONT);
        titleLabel.setForeground(GlobalConstants.SECONDARY_COLOR);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(titleLabel, gbc);

        JLabel usernameLabel = new JLabel("Username: ");
        usernameLabel.setFont(GlobalConstants.LABEL_FONT);
        usernameLabel.setForeground(GlobalConstants.BASIC_COLOR);
        gbc.gridwidth = 1;
        add(usernameLabel, gbc);

        JTextField usernameField = new JTextField(20);
        usernameField.setFont(GlobalConstants.INPUT_FONT);
        usernameField.setPreferredSize(GlobalConstants.TEXT_FIELD_SIZE);
        usernameField.setBackground(Color.WHITE);
        usernameField.setBorder(GlobalConstants.TEXT_FIELD_BORDER);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setFont(GlobalConstants.LABEL_FONT);
        passwordLabel.setForeground(GlobalConstants.BASIC_COLOR);
        gbc.gridwidth = 1;
        add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(GlobalConstants.INPUT_FONT);
        passwordField.setPreferredSize(GlobalConstants.TEXT_FIELD_SIZE);
        passwordField.setBackground(Color.WHITE);
        passwordField.setBorder(GlobalConstants.TEXT_FIELD_BORDER);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(GlobalConstants.LABEL_FONT);
        loginButton.setBackground(GlobalConstants.BUTTON_BG_COLOR);
        loginButton.setForeground(Color.WHITE);
        loginButton.setPreferredSize(GlobalConstants.BUTTON_SIZE);
        loginButton.setFocusPainted(false);
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(actionEvent -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            UserService userService = new UserService();
            String userType = String.valueOf(userService.loginUser(username, password));

            if (userType.equals("donor")) {
                DonorService donorService = new DonorService();
                try {
                    Donor donor = donorService.getDonorByUsername(username);
                    if (donor != null) {
                        gui.DonationPanel donationPanel = new gui.DonationPanel(donor, mainPanel, cardLayout);
                        mainPanel.add(donationPanel, "DONATION_PANEL");
                        cardLayout.show(mainPanel, "DONATION_PANEL");
                        usernameField.setText("");
                        passwordField.setText("");
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }

            } else if (userType.equals("association")) {
                AssociationDAO associationDAO = new AssociationDAO();
                Association association = associationDAO.getAssociationByUsername(username);

                if (association != null) {
                    gui.AssociationPanel associationPanel = new gui.AssociationPanel(association, mainPanel, cardLayout);
                    mainPanel.add(associationPanel, "ASSOCIATION_PANEL");
                    cardLayout.show(mainPanel, "ASSOCIATION_PANEL");
                    usernameField.setText("");
                    passwordField.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid credentials!", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(loginButton, gbc);

        JLabel infoLabel = new JLabel("Don't have an account?");
        infoLabel.setFont(GlobalConstants.LABEL_FONT);
        infoLabel.setForeground(GlobalConstants.SECONDARY_COLOR);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(infoLabel, gbc);

        JLabel registerDonorLink = new JLabel("<html><u><b>Register as a Donor</b></u></html>");
        registerDonorLink.setFont(GlobalConstants.LABEL_FONT);
        registerDonorLink.setForeground(GlobalConstants.LINK_COLOR);
        registerDonorLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerDonorLink.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                cardLayout.show(mainPanel, "REGISTER_DONOR");
            }
        });
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(registerDonorLink, gbc);

        JLabel registerAssociationLink = new JLabel("<html><u><b>Register as an Association</b></u></html>");
        registerAssociationLink.setFont(GlobalConstants.LABEL_FONT);
        registerAssociationLink.setForeground(GlobalConstants.LINK_COLOR);
        registerAssociationLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerAssociationLink.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                cardLayout.show(mainPanel, "REGISTER_ASSOCIATION");
            }
        });
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(registerAssociationLink, gbc);
    }
}
