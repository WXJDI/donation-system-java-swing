package gui;

import app.GlobalConstants;
import gui.components.DonationDialog;
import gui.components.DonationForm;
import models.Donation;
import models.Donor;
import services.DonationService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;


public class DonationPanel extends JPanel {
    private Donor currentDonor;
    private DonationService donationService;
    private JTable donationTable;
    private DefaultTableModel tableModel;

    public DonationPanel(Donor donor, JPanel mainPanel, CardLayout cardLayout) {
        this.currentDonor = donor;
        this.donationService = new DonationService();

        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"ID", "Type", "Description", "Quantity", "Available"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        donationTable = new JTable(tableModel);

        customizeTable();

        JScrollPane scrollPane = new JScrollPane(donationTable);

        JButton addDonationButton = new JButton("Add new donation");
        addDonationButton.setFont(GlobalConstants.LABEL_FONT);
        addDonationButton.setBackground(GlobalConstants.ADD_BUTTON_BG_COLOR);
        addDonationButton.setForeground(GlobalConstants.ADD_BUTTON_FG_COLOR);
        addDonationButton.setFocusPainted(false);
        addDonationButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JButton editDonationButton = new JButton("Edit donation");
        editDonationButton.setFont(GlobalConstants.LABEL_FONT);
        editDonationButton.setBackground(GlobalConstants.EDIT_BUTTON_BG_COLOR);
        editDonationButton.setForeground(GlobalConstants.EDIT_BUTTON_FG_COLOR);
        editDonationButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JButton deleteDonationButton = new JButton("Delete donation");
        deleteDonationButton.setFont(GlobalConstants.LABEL_FONT);
        deleteDonationButton.setBackground(GlobalConstants.DELETE_BUTTON_BG_COLOR);
        deleteDonationButton.setForeground(GlobalConstants.DELETE_BUTTON_FG_COLOR);
        deleteDonationButton.setFocusPainted(false);
        deleteDonationButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JButton donorDashboardButton = new JButton("Dashboard");
        donorDashboardButton.setFont(GlobalConstants.LABEL_FONT);
        donorDashboardButton.setBackground(GlobalConstants.BUTTON_BG_COLOR);
        donorDashboardButton.setForeground(Color.WHITE);
        donorDashboardButton.setFocusPainted(false);
        donorDashboardButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(GlobalConstants.LABEL_FONT);
        logoutButton.setBackground(GlobalConstants.BUTTON_BG_COLOR);
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addDonationButton);
        buttonPanel.add(editDonationButton);
        buttonPanel.add(deleteDonationButton);
        buttonPanel.add(donorDashboardButton);
        buttonPanel.add(logoutButton);

        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        loadDonations();

        addDonationButton.addActionListener(actionEvent -> openAddDonationPopup());
        editDonationButton.addActionListener(actionEvent -> {
            int selectedRow = donationTable.getSelectedRow();
            if (selectedRow >= 0) {
                int donationId = (int) tableModel.getValueAt(selectedRow, 0);
                openEditDonationPopup(donationId);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a donation to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteDonationButton.addActionListener(actionEvent -> {
            int selectedRow = donationTable.getSelectedRow();
            if (selectedRow >= 0) {
                int donationId = (int) tableModel.getValueAt(selectedRow, 0);
                int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this donation?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    if (donationService.deleteDonationById(donationId)) {
                        JOptionPane.showMessageDialog(this, "Donation deleted successfully.");
                        loadDonations();
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to delete donation.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a donation to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        donorDashboardButton.addActionListener(actionEvent -> {
            DonorDashboardPanel donorDashboardPanel = new DonorDashboardPanel(currentDonor.getId());
            mainPanel.add(donorDashboardPanel, "DONOR_DASHBOARD_PANEL");
            cardLayout.show(mainPanel, "DONOR_DASHBOARD_PANEL");
        });

        logoutButton.addActionListener(actionEvent -> {
            cardLayout.show(mainPanel, "LOGIN");
        });
    }

    private void customizeTable() {
        donationTable.setRowHeight(30);

        donationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        donationTable.getTableHeader().setFont(GlobalConstants.TABLE_COLUMN_NAME_FONT);
        donationTable.setFont(GlobalConstants.LABEL_FONT);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < donationTable.getColumnCount(); i++) {
            donationTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        int[] columnWidths = {50, 120, 330, 80, 70};
        for (int i = 0; i < columnWidths.length; i++) {
            TableColumn column = donationTable.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }

        donationTable.getTableHeader().setReorderingAllowed(false);
        donationTable.getTableHeader().setResizingAllowed(false);

        donationTable.setGridColor(GlobalConstants.TABLE_GRID_COLOR);
        donationTable.setSelectionBackground(GlobalConstants.TABLE_SELECTION_BG);
        donationTable.setSelectionForeground(GlobalConstants.TABLE_SELECTION_FG);
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
                    this,
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
