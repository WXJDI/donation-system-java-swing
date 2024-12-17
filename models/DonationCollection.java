package models;

import java.time.LocalDate;

public class DonationCollection {
    private int id;
    private Donor donor;
    private Association association;
    private int quantity;
    private LocalDate dateDonationCollected;
    private String type;
    private String description;
    private String associationName;

    public DonationCollection(int id, Donor donor, Association association, int quantity, LocalDate dateDonationCollected) {
        this.id = id;
        this.donor = donor;
        this.association = association;
        this.quantity = quantity;
        this.dateDonationCollected = dateDonationCollected;
    }

    public DonationCollection(int id, String type, String description, int quantity, String associationName, LocalDate dateDonationCollected) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.quantity = quantity;
        this.associationName = associationName;
        this.dateDonationCollected = dateDonationCollected;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAssociationName() {
        return associationName;
    }

    public void setAssociationName(String associationName) {
        this.associationName = associationName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Donor getDonor() {
        return donor;
    }

    public void setDonor(Donor donor) {
        this.donor = donor;
    }

    public Association getAssociation() {
        return association;
    }

    public void setAssociation(Association association) {
        this.association = association;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getDateDonationCollected() {
        return dateDonationCollected;
    }

    public void setDateDonationCollected(LocalDate dateDonationCollected) {
        this.dateDonationCollected = dateDonationCollected;
    }
}