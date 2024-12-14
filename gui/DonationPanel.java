package gui;

import gui.components.DonationDialog;
import gui.components.DonationForm;
import models.Donation;
import models.Donor;
import services.DonationService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DonationPanel extends JPanel {
    private Donor currentDonor;
    private DonationService donationService;
    private JTable donationTable;
    private DefaultTableModel tableModel;

    public DonationPanel(Donor donor) {
        this.currentDonor = donor;
        this.donationService = new DonationService();

        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"ID", "Type", "Description", "Quantity", "Available"}, 0);
        donationTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(donationTable);

        JButton addDonationButton = new JButton("Add new donation");
        JButton editDonationButton = new JButton("Edit donation");
        JButton deleteDonationButton = new JButton("Delete donation");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addDonationButton);
        buttonPanel.add(editDonationButton);
        buttonPanel.add(deleteDonationButton);

        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        loadDonations();

        addDonationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                openAddDonationPopup();
            }
        });

        editDonationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int selectedRow = donationTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int donationId = (int) tableModel.getValueAt(selectedRow, 0);
                    openEditDonationPopup(donationId);
                } else {
                    JOptionPane.showMessageDialog(DonationPanel.this, "Please select a donation to edit.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deleteDonationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int selectedRow = donationTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int donationId = (int) tableModel.getValueAt(selectedRow, 0);
                    int confirmation = JOptionPane.showConfirmDialog(DonationPanel.this, "Are you sure you want to delete this donation?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                    if (confirmation == JOptionPane.YES_OPTION) {
                        if (donationService.deleteDonationById(donationId)) {
                            JOptionPane.showMessageDialog(DonationPanel.this, "Donation deleted successfully.");
                            loadDonations();
                        } else {
                            JOptionPane.showMessageDialog(DonationPanel.this, "Failed to delete donation.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(DonationPanel.this, "Please select a donation to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void loadDonations() {
        tableModel.setRowCount(0);
        ArrayList<Donation> donations = donationService.getDonationsForDonor(currentDonor.getId());
        for (Donation donation : donations) {
            tableModel.addRow(new Object[]{
                    donation.getId(),
                    donation.getType(),
                    donation.getDescription(),
                    donation.getQuantity(),
                    donation.isAvailable(),
            });
        }
    }

    public void openAddDonationPopup() {
        DonationForm donationForm = new DonationForm(currentDonor, null);

        DonationDialog dialog = new DonationDialog(
                this,
                "Add Donation",
                donationForm,
                "Add Donation",
                () -> {
                    Donation donation = donationForm.getDonation(false);
                    if (donation != null) {
                        JOptionPane.showMessageDialog(this, "Donation added successfully.");
                        loadDonations();
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to add donation.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
        );
        dialog.setVisible(true);
    }

    public void openEditDonationPopup(int donationId) {
        Donation existingDonation = donationService.getDonationById(currentDonor, donationId);
        if (existingDonation != null) {
            DonationForm donationForm = new DonationForm(currentDonor, existingDonation);

            DonationDialog dialog = new DonationDialog(
                    this, // Pass the JPanel as the parent
                    "Edit Donation",
                    donationForm,
                    "Save Changes",
                    () -> {
                        Donation updatedDonation = donationForm.getDonation(true);
                        if (updatedDonation != null) {
                            JOptionPane.showMessageDialog(this, "Donation updated successfully.");
                            loadDonations();
                        } else {
                            JOptionPane.showMessageDialog(this, "Failed to update donation.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
            );

            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Donation not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}