package gui.components;

import app.GlobalConstants;
import models.Donation;
import models.Donor;
import services.DonationService;

import javax.swing.*;
import java.awt.*;

public class DonationForm extends JPanel {
    private JTextField typeField;
    private JTextArea descriptionArea;
    private JSpinner quantitySpinner;
    private Donation donation;
    private DonationService donationService;
    private Donor donor;

    public DonationForm(Donor donor, Donation existingDonation) {
        this.donor = donor;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Type Field
        typeField = new JTextField();
        typeField.setFont(GlobalConstants.INPUT_FONT);
        typeField.setPreferredSize(GlobalConstants.TEXT_FIELD_SIZE);
        typeField.setBackground(Color.WHITE);
        typeField.setBorder(GlobalConstants.TEXT_FIELD_BORDER);

        JLabel typeLabel = new JLabel("Type:");
        typeLabel.setFont(GlobalConstants.LABEL_FONT);
        typeLabel.setForeground(GlobalConstants.BASIC_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(typeLabel, gbc);

        gbc.gridx = 1;
        add(typeField, gbc);

        descriptionArea = new JTextArea(4, 20);
        descriptionArea.setFont(GlobalConstants.INPUT_FONT);
        descriptionArea.setBackground(Color.WHITE);
        descriptionArea.setBorder(GlobalConstants.TEXT_FIELD_BORDER);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setFont(GlobalConstants.LABEL_FONT);
        descriptionLabel.setForeground(GlobalConstants.BASIC_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(descriptionLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);

        quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        JComponent spinnerEditor = quantitySpinner.getEditor();
        if (spinnerEditor instanceof JSpinner.DefaultEditor) {
            JTextField spinnerTextField = ((JSpinner.DefaultEditor) spinnerEditor).getTextField();
            spinnerTextField.setFont(GlobalConstants.INPUT_FONT);
            spinnerTextField.setPreferredSize(GlobalConstants.TEXT_FIELD_SIZE);
            spinnerTextField.setBackground(Color.WHITE);
            spinnerTextField.setBorder(GlobalConstants.TEXT_FIELD_BORDER);
        }

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setFont(GlobalConstants.LABEL_FONT);
        quantityLabel.setForeground(GlobalConstants.BASIC_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        add(quantityLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(quantitySpinner, gbc);

        if (existingDonation != null) {
            typeField.setText(existingDonation.getType());
            descriptionArea.setText(existingDonation.getDescription());
            quantitySpinner.setValue(existingDonation.getQuantity());
            this.donation = existingDonation;
        }

        setPreferredSize(GlobalConstants.FORM_SIZE);
    }

    public Donation getDonation(boolean updating) {
        donationService = new DonationService();
        if (updating) {
            donationService.editDonation(donation.getId(), typeField.getText(), descriptionArea.getText(), (int) quantitySpinner.getValue(), donor);
        } else {
            donation = donationService.createDonation(typeField.getText(), descriptionArea.getText(), (int) quantitySpinner.getValue(), donor);
        }
        return donation;
    }
}
