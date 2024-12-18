package gui;

import app.GlobalConstants;
import models.DonationCollection;
import services.DonationCollectionService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;

public class DonorDashboardPanel extends JPanel {
    private JTable collectedDonationsTable;
    private DefaultTableModel tableModel;
    private DonationCollectionService donationCollectionService;

    public DonorDashboardPanel(int donorId, JPanel mainPanel, CardLayout cardLayout) {
        donationCollectionService = new DonationCollectionService();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel titleLabel = new JLabel("Collected donations", JLabel.CENTER);
        titleLabel.setFont(GlobalConstants.TITLE_FONT);
        titleLabel.setForeground(GlobalConstants.SECONDARY_COLOR);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);

        JButton backButton = new JButton("Back");
        backButton.setFont(GlobalConstants.LABEL_FONT);
        backButton.setBackground(GlobalConstants.BUTTON_BG_COLOR);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(actionEvent -> {
            cardLayout.show(mainPanel, "DONATION_PANEL");
        });
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        add(backButton, gbc);

        tableModel = new DefaultTableModel(new Object[]{"Type", "Description", "Quantity", "Association", "Date"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        collectedDonationsTable = new JTable(tableModel) {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component returnComp = super.prepareRenderer(renderer, row, column);
                Color alternateColor = GlobalConstants.TABLE_HEADER_BG;
                Color whiteColor = Color.WHITE;
                if (!returnComp.getBackground().equals(getSelectionBackground())) {
                    Color bg = (row % 2 == 0 ? whiteColor : alternateColor);
                    returnComp.setBackground(bg);
                }
                return returnComp;
            }
        };

        customizeTable();

        JScrollPane scrollPane = new JScrollPane(collectedDonationsTable);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 10, 10, 10);
        add(scrollPane, gbc);

        loadCollectedDonations(donorId);
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

        collectedDonationsTable.setBackground(GlobalConstants.TABLE_BG);
        collectedDonationsTable.getTableHeader().setBackground(GlobalConstants.TABLE_HEADER_BG);
        collectedDonationsTable.getTableHeader().setForeground(Color.WHITE);
    }

    public void loadCollectedDonations(int donorId) {
        ArrayList<DonationCollection> donationCollections = donationCollectionService.getCollectedDonationsForDonor(donorId);
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        Color startColor = GlobalConstants.LIGHT_BLUE_COLOR;
        Color endColor = GlobalConstants.SECONDARY_COLOR;
        int width = getWidth();
        int height = getHeight();

        GradientPaint gradient = new GradientPaint(0, 0, startColor, 0, height, endColor);
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, width, height);
    }
}
