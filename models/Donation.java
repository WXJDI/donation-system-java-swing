package models;

public class Donation {
    private int id;
    private String type;
    private String description;
    private int quantity;
    private boolean isAvailable;
    private Donor donor;

    public Donation(int id, String type, String description, int quantity, Donor donor) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.quantity = quantity;
        this.isAvailable = quantity > 0;
        this.donor = donor;
    }
    public Donation(int id, String type, String description, int quantity, boolean isAvailable, int donorId) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.quantity = quantity;
        this.isAvailable = isAvailable;
        this.donor = new Donor(); // Create a Donor object
        this.donor.setId(donorId); // Only set the donor ID
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Donor getDonor() {
        return donor;
    }

    public void setDonor(Donor donor) {
        this.donor = donor;
    }

    @Override
    public String toString() {
        return "Donation{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", isAvailable=" + isAvailable +
                ", donor=" + donor +
                '}';
    }
}