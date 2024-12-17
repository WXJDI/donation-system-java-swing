package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;

import app.GlobalConstants;
import gui.components.CollectDonationDialog;
import models.Donation;
import services.AssociationService;
import models.Association;

public class AssociationPanel extends JPanel {
    private JTable donationTable;
    private DefaultTableModel tableModel;
    private AssociationService associationService;
    private Association currentAssociation;

    private JLabel titleLabel;
    private JButton addDonationButton;
    private JButton viewDonationButton;

    public AssociationPanel(Association association) {
        this.currentAssociation = association;
        this.associationService = new AssociationService();

        setLayout(new BorderLayout());

        titleLabel = new JLabel("Available donations", JLabel.CENTER);
        titleLabel.setFont(GlobalConstants.TITLE_FONT);
        titleLabel.setForeground(GlobalConstants.SECONDARY_COLOR);
        add(titleLabel, BorderLayout.NORTH); // Title at the top

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

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton collectDonationButton = new JButton("Collect Donation");
        collectDonationButton.setFont(GlobalConstants.LABEL_FONT);
        collectDonationButton.setBackground(GlobalConstants.ADD_BUTTON_BG_COLOR);
        collectDonationButton.setForeground(GlobalConstants.DELETE_BUTTON_FG_COLOR);
        collectDonationButton.setPreferredSize(GlobalConstants.BUTTON_SIZE);
        collectDonationButton.setFocusPainted(false);
        collectDonationButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        collectDonationButton.addActionListener(e -> handleCollectDonation());

        buttonPanel.add(collectDonationButton);
        add(buttonPanel, BorderLayout.SOUTH);

        loadDonations();
    }

    private void customizeTable() {
        donationTable.setRowHeight(30);

        donationTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
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

        CollectDonationDialog dialog = new CollectDonationDialog((JFrame) SwingUtilities.getWindowAncestor(this), availableQuantity);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            int quantityToCollect = dialog.getQuantityToCollect();

            boolean success = associationService.collectDonation(currentAssociation.getId(), donationId, quantityToCollect);

            if (success) {
                JOptionPane.showMessageDialog(this, "Donation collected successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadDonations();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to collect donation. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}