package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

import models.Donation;
import services.AssociationService;
import models.Association;

public class AssociationPanel extends JPanel {
    private JTable donationTable;
    private DefaultTableModel tableModel;
    private AssociationService associationService;
    private Association currentAssociation;

    // UI Components
    private JLabel titleLabel;
    private JButton addDonationButton;
    private JButton viewDonationButton;

    public AssociationPanel(Association association) {
        this.currentAssociation = association;
        this.associationService = new AssociationService();

        // Set the layout for the panel
        setLayout(new BorderLayout());

        // Title Label
        titleLabel = new JLabel("Association Donations", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH); // Title at the top

        // Create the Table and its Scroll Pane
        tableModel = new DefaultTableModel(new Object[]{"ID", "Type", "Description", "Quantity", "Available"}, 0);
        donationTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(donationTable);

        // Add the scroll pane to the center of the panel
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton collectDonationButton = new JButton("Collect Donation");
        collectDonationButton.addActionListener(e -> handleCollectDonation());

        buttonPanel.add(collectDonationButton);
        add(buttonPanel, BorderLayout.SOUTH);


        // Load donations specific to this association
        loadDonations();
    }

    // Load donations into the table
    private void loadDonations() {
        tableModel.setRowCount(0); // Clear existing rows
        ArrayList<Donation> donations = associationService.getAvailableDonations();

        for (Donation donation : donations) {
            tableModel.addRow(new Object[]{
                    donation.getId(),
                    donation.getType(),
                    donation.getDescription(),
                    donation.getQuantity(),
                    donation.isAvailable()
            });
        }
    }

    // Placeholder for adding a new donation
    private void addDonation() {
        // Code for adding a donation will go here
        System.out.println("Add donation button clicked");
    }

    // Placeholder for viewing a selected donation
    private void viewDonation() {
        int selectedRow = donationTable.getSelectedRow();
        if (selectedRow != -1) {
            int donationId = (int) tableModel.getValueAt(selectedRow, 0);
            // Code to view details of the selected donation
            System.out.println("View donation with ID: " + donationId);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a donation to view.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void handleCollectDonation() {
        int selectedRow = donationTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a donation to collect.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get the selected donation's ID and current quantity
        int donationId = (int) tableModel.getValueAt(selectedRow, 0);
        int availableQuantity = (int) tableModel.getValueAt(selectedRow, 3);

        // Popup to input the quantity to collect
        String quantityInput = JOptionPane.showInputDialog(this, "Enter the quantity to collect (Available: " + availableQuantity + "):", "Collect Donation", JOptionPane.PLAIN_MESSAGE);

        // Check if input is valid
        if (quantityInput == null || quantityInput.isEmpty()) {
            return; // User cancelled the input
        }

        try {
            int quantityToCollect = Integer.parseInt(quantityInput);

            if (quantityToCollect <= 0 || quantityToCollect > availableQuantity) {
                JOptionPane.showMessageDialog(this, "Invalid quantity. Please enter a value between 1 and " + availableQuantity + ".", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                boolean success = associationService.collectDonation(currentAssociation.getId(), donationId, quantityToCollect);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Donation collected successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadDonations(); // Refresh the table
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to collect donation. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a numeric value.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



}
