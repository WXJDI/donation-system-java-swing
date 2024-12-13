package gui.components;

import models.Donation;
import models.Donor;
import services.DonationService;

import javax.swing.*;
import java.awt.*;

public class DonationForm extends JPanel {
    private JTextField typeField;
    private JTextField descriptionField;
    private JSpinner quantitySpinner;
    private Donation donation;
    private DonationService donationService;
    private Donor donor;

    public DonationForm(Donor donor, Donation existingDonation) {
        this.donor = donor;

        setLayout(new GridLayout(4, 2, 10, 10));

        typeField = new JTextField();
        descriptionField = new JTextField();
        quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));

        if (existingDonation != null) {
            typeField.setText(existingDonation.getType());
            descriptionField.setText(existingDonation.getDescription());
            quantitySpinner.setValue(existingDonation.getQuantity());
            this.donation = existingDonation;
        }

        JLabel typeLabel = new JLabel("Type:");
        add(typeLabel);
        add(typeField);

        JLabel descriptionLabel = new JLabel("Description:");
        add(descriptionLabel);
        add(descriptionField);

        JLabel quantityLabel = new JLabel("Quantity:");
        add(quantityLabel);
        add(quantitySpinner);
    }

    public Donation getDonation(boolean updating) {
        donationService = new DonationService();
        if (updating) {
            donationService.editDonation(donation.getId(), typeField.getText(), descriptionField.getText(), (int) quantitySpinner.getValue(), donor);
        } else {
            donation = donationService.createDonation(typeField.getText(), descriptionField.getText(), (int) quantitySpinner.getValue(), donor);
        }
        return donation;
    }
}
