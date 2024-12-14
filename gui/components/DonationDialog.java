package gui.components;

import app.GlobalConstants;

import javax.swing.*;
import java.awt.*;

public class DonationDialog extends JDialog {
    private boolean confirmed;

    public DonationDialog(Component parent, String title, JPanel formPanel, String confirmButtonText, Runnable onConfirmAction) {
        // Find the closest window ancestor
        super((Frame) SwingUtilities.getWindowAncestor(parent), title, true); // Modal dialog
        confirmed = false;

        // Set up dialog layout
        setLayout(new BorderLayout());

        // Add form panel
        add(formPanel, BorderLayout.CENTER);

        // Create buttons
        JButton confirmButton = new JButton(confirmButtonText);
        confirmButton.setFont(GlobalConstants.LABEL_FONT);
        confirmButton.setBackground(GlobalConstants.OK_BUTTON_BG_COLOR);
        confirmButton.setForeground(GlobalConstants.OK_BUTTON_FG_COLOR);
        confirmButton.setPreferredSize(GlobalConstants.BUTTON_SIZE);
        confirmButton.setFocusPainted(false);
        confirmButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(GlobalConstants.LABEL_FONT);
        cancelButton.setBackground(GlobalConstants.CANCEL_BUTTON_BG_COLOR);
        cancelButton.setForeground(GlobalConstants.CANCEL_BUTTON_FG_COLOR);
        cancelButton.setPreferredSize(GlobalConstants.BUTTON_SIZE);
        cancelButton.setFocusPainted(false);
        cancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Add button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button actions
        confirmButton.addActionListener(e -> {
            confirmed = true;
            onConfirmAction.run(); // Execute the custom action
            dispose();
        });
        cancelButton.addActionListener(e -> dispose());

        // Set dialog properties
        pack();
        setLocationRelativeTo(parent);
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
