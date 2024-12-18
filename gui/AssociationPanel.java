package gui;

import app.GlobalConstants;
import gui.components.CollectDonationDialog;
import models.Association;
import models.Donation;
import services.AssociationService;
import services.DonationCollectionService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;

public class AssociationPanel extends JPanel {
    private JTable donationTable;
    private DefaultTableModel tableModel;
    private AssociationService associationService;
    private DonationCollectionService donationCollectionService;
    private Association currentAssociation;

    private JLabel titleLabel;

    public AssociationPanel(Association association, JPanel mainPanel, CardLayout cardLayout) {
        this.currentAssociation = association;
        this.associationService = new AssociationService();
        this.donationCollectionService = new DonationCollectionService();

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());

        titleLabel = new JLabel("Available Donations", JLabel.CENTER);
        titleLabel.setFont(GlobalConstants.TITLE_FONT);
        titleLabel.setForeground(GlobalConstants.SECONDARY_COLOR);
        topPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton collectDonationButton = new JButton("Collect Donation");
        collectDonationButton.setFont(GlobalConstants.LABEL_FONT);
        collectDonationButton.setBackground(GlobalConstants.BUTTON_BG_COLOR);
        collectDonationButton.setForeground(Color.WHITE);
        collectDonationButton.setPreferredSize(GlobalConstants.BUTTON_SIZE);
        collectDonationButton.setFocusPainted(false);
        collectDonationButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        collectDonationButton.addActionListener(e -> handleCollectDonation());

        JButton associationDashboardButton = new JButton("Dashboard");
        associationDashboardButton.setFont(GlobalConstants.LABEL_FONT);
        associationDashboardButton.setBackground(GlobalConstants.BUTTON_BG_COLOR);
        associationDashboardButton.setForeground(Color.WHITE);
        associationDashboardButton.setFocusPainted(false);
        associationDashboardButton.setPreferredSize(GlobalConstants.BUTTON_SIZE);
        associationDashboardButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        associationDashboardButton.addActionListener(actionEvent -> {
            AssociationDashboardPanel associationDashboardPanel = new AssociationDashboardPanel(currentAssociation.getId(), mainPanel, cardLayout);
            mainPanel.add(associationDashboardPanel, "ASSOCIATION_DASHBOARD_PANEL");
            cardLayout.show(mainPanel, "ASSOCIATION_DASHBOARD_PANEL");
        });

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(GlobalConstants.LABEL_FONT);
        logoutButton.setBackground(GlobalConstants.BUTTON_BG_COLOR);
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setPreferredSize(GlobalConstants.BUTTON_SIZE);
        logoutButton.setFocusPainted(false);
        logoutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logoutButton.addActionListener(actionEvent -> {
            cardLayout.show(mainPanel, "LOGIN");
        });

        buttonPanel.add(collectDonationButton);
        buttonPanel.add(associationDashboardButton);
        buttonPanel.add(logoutButton);

        topPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Type", "Description", "Quantity", "Available"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        donationTable = new JTable(tableModel);
        customizeTable();

        JScrollPane scrollPane = new JScrollPane(donationTable);
        add(scrollPane, BorderLayout.CENTER);

        loadDonations();
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

    private void loadDonations() {
        tableModel.setRowCount(0);
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

    private void handleCollectDonation() {
        int selectedRow = donationTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a donation to collect.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int donationId = (int) tableModel.getValueAt(selectedRow, 0);
        int availableQuantity = (int) tableModel.getValueAt(selectedRow, 3);
        if (availableQuantity == 0) {
            JOptionPane.showMessageDialog(this, "At this time, donations are unavailable, but we will let you know if more become available.", "Not Available", JOptionPane.ERROR_MESSAGE);
            return;
        }

        CollectDonationDialog dialog = new CollectDonationDialog((JFrame) SwingUtilities.getWindowAncestor(this), availableQuantity);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            int quantityToCollect = dialog.getQuantityToCollect();

            boolean success = donationCollectionService.collectDonation(currentAssociation.getId(), donationId, quantityToCollect);

            if (success) {
                JOptionPane.showMessageDialog(this, "Donation collected successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadDonations();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to collect donation. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
