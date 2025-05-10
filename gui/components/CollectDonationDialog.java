package gui.components;

import app.GlobalConstants;

import javax.swing.*;
import java.awt.*;

public class CollectDonationDialog extends JDialog {
    private boolean isConfirmed;
    private int quantityToCollect;

    public CollectDonationDialog(JFrame parent, int availableQuantity) {
        super(parent, "Collect Donation", true);

        isConfirmed = false;
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel messageLabel = new JLabel("Enter the quantity to collect (Available: " + availableQuantity + "):");
        messageLabel.setFont(GlobalConstants.LABEL_FONT);
        messageLabel.setForeground(GlobalConstants.BASIC_COLOR);
        gbc.gridy = 0;
        add(messageLabel, gbc);

        JTextField quantityField = new JTextField();
        quantityField.setFont(GlobalConstants.INPUT_FONT);
        quantityField.setBackground(Color.WHITE);
        quantityField.setBorder(GlobalConstants.TEXT_FIELD_BORDER);
        gbc.gridy = 1;
        gbc.ipadx = 200;
        gbc.ipady = 10;
        add(quantityField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton confirmButton = new JButton("Confirm");
        confirmButton.setFont(GlobalConstants.LABEL_FONT);
        confirmButton.setBackground(GlobalConstants.BUTTON_BG_COLOR);
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFocusPainted(false);
        confirmButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        confirmButton.addActionListener(e -> {
            try {
                int inputQuantity = Integer.parseInt(quantityField.getText().trim());

                if (inputQuantity > 0 && inputQuantity <= availableQuantity) {
                    quantityToCollect = inputQuantity;
                    isConfirmed = true;
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid quantity. Please enter a value between 1 and " + availableQuantity + ".", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter a numeric value.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(GlobalConstants.LABEL_FONT);
        cancelButton.setBackground(GlobalConstants.BUTTON_BG_COLOR);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        gbc.gridy = 2;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        add(buttonPanel, gbc);

        pack();
        setLocationRelativeTo(parent);
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public int getQuantityToCollect() {
        return quantityToCollect;
    }
}
