package gui;

import app.GlobalConstants;
import models.DonationCollection;
import services.DonationCollectionService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;

public class AssociationDashboardPanel extends JPanel {
    private JTable collectedDonationsTable;
    private DefaultTableModel tableModel;
    private DonationCollectionService donationCollectionService;

    public AssociationDashboardPanel(int associationId) {
        donationCollectionService = new DonationCollectionService();

        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Collected Donations", JLabel.CENTER);
        titleLabel.setFont(GlobalConstants.TITLE_FONT);
        titleLabel.setForeground(GlobalConstants.SECONDARY_COLOR);
        add(titleLabel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"Type", "Description", "Quantity", "Donor", "Date"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        collectedDonationsTable = new JTable(tableModel);

        customizeTable();

        JScrollPane scrollPane = new JScrollPane(collectedDonationsTable);
        add(scrollPane, BorderLayout.CENTER);

        loadCollectedDonations(associationId);
    }

    public void loadCollectedDonations(int associationId) {
        ArrayList<DonationCollection> donationCollections = donationCollectionService.getDonationsCollectedByAssociation(associationId);
        tableModel.setRowCount(0);

        for (DonationCollection donationCollection : donationCollections) {
            tableModel.addRow(new Object[]{
                    donationCollection.getType(),
                    donationCollection.getDescription(),
                    donationCollection.getQuantity(),
                    donationCollection.getAssociationName(),
                    donationCollection.getDateDonationCollected()
            });
        }
    }

    private void customizeTable() {
        collectedDonationsTable.setRowHeight(30);

        collectedDonationsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        collectedDonationsTable.getTableHeader().setFont(GlobalConstants.TABLE_COLUMN_NAME_FONT);
        collectedDonationsTable.setFont(GlobalConstants.LABEL_FONT);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < collectedDonationsTable.getColumnCount(); i++) {
            collectedDonationsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        int[] columnWidths = {70, 300, 80, 100, 100};
        for (int i = 0; i < columnWidths.length; i++) {
            TableColumn column = collectedDonationsTable.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }

        collectedDonationsTable.getTableHeader().setReorderingAllowed(false);
        collectedDonationsTable.getTableHeader().setResizingAllowed(false);

        collectedDonationsTable.setGridColor(GlobalConstants.TABLE_GRID_COLOR);
        collectedDonationsTable.setSelectionBackground(GlobalConstants.TABLE_SELECTION_BG);
        collectedDonationsTable.setSelectionForeground(GlobalConstants.TABLE_SELECTION_FG);
    }
}
