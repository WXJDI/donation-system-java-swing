package gui;

import app.GlobalConstants;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private JPanel mainPanel;
    public CardLayout cardLayout;

    public MainFrame() {
        // Frame Properties
        setTitle("Donation System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(GlobalConstants.FRAME_SIZE);
        setLocationRelativeTo(null); // Center the window
        setResizable(false);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel loginPanel = new LoginPanel(mainPanel, cardLayout);
        JPanel registerDonorPanel = new RegisterDonorPanel(mainPanel, cardLayout);
        JPanel registerAssociationPanel = new RegisterAssociationPanel(mainPanel, cardLayout);

        mainPanel.add(loginPanel, "LOGIN");
        mainPanel.add(registerDonorPanel, "REGISTER_DONOR");
        mainPanel.add(registerAssociationPanel, "REGISTER_ASSOCIATION");

        add(mainPanel);

        cardLayout.show(mainPanel, "LOGIN"); // Initial panel is loginPanel
    }
}
