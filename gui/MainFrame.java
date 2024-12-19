package gui;

import app.GlobalConstants;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JPanel mainPanel;
    public CardLayout cardLayout;

    public MainFrame() {
        setTitle("Donation System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(GlobalConstants.FRAME_SIZE);
        setLocationRelativeTo(null);
        setResizable(false);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel loginPanel = new LoginPanel(mainPanel, cardLayout);
        JPanel registerDonorPanel = new RegisterDonorPanel(mainPanel, cardLayout);
        JPanel registerAssociationPanel = new RegisterAssociationPanel(mainPanel, cardLayout);
        JPanel welcomePanel = new WelcomePanel(mainPanel, cardLayout);

        mainPanel.add(loginPanel, "LOGIN");
        mainPanel.add(registerDonorPanel, "REGISTER_DONOR");
        mainPanel.add(registerAssociationPanel, "REGISTER_ASSOCIATION");
        mainPanel.add(welcomePanel, "WELCOME_PANEL");

        add(mainPanel);

        cardLayout.show(mainPanel, "WELCOME_PANEL");
    }
}
