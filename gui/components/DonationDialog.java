package gui.components;

import app.GlobalConstants;

import javax.swing.*;
import java.awt.*;

public class DonationDialog extends JDialog {
    private boolean confirmed;

    public DonationDialog(Component parent, String title, JPanel formPanel, String confirmButtonText, Runnable onConfirmAction) {
        super((Frame) SwingUtilities.getWindowAncestor(parent), title, true); // Modal dialog
        confirmed = false;

        setLayout(new BorderLayout());

        add(formPanel, BorderLayout.CENTER);

        JButton confirmButton = new JButton(confirmButtonText);
        confirmButton.setFont(GlobalConstants.LABEL_FONT);
        confirmButton.setBackground(GlobalConstants.BUTTON_BG_COLOR);
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setPreferredSize(GlobalConstants.BUTTON_SIZE);
        confirmButton.setFocusPainted(false);
        confirmButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(GlobalConstants.LABEL_FONT);
        cancelButton.setBackground(GlobalConstants.BUTTON_BG_COLOR);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setPreferredSize(GlobalConstants.BUTTON_SIZE);
        cancelButton.setFocusPainted(false);
        cancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        confirmButton.addActionListener(e -> {
            confirmed = true;
            onConfirmAction.run();
            dispose();
        });
        cancelButton.addActionListener(e -> dispose());

        pack();
        setLocationRelativeTo(parent);
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
