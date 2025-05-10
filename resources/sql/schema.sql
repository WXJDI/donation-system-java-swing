CREATE TABLE User (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE Donor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    user_id INT NOT NULL,
    CONSTRAINT fk_donor_user FOREIGN KEY (user_id) REFERENCES User(id)
    ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Association (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    location VARCHAR(255) NOT NULL,
    user_id INT NOT NULL,
    CONSTRAINT fk_association_user FOREIGN KEY (user_id) REFERENCES User(id)
    ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Donation (
    id INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    quantity INT NOT NULL,
    isAvailable BOOLEAN DEFAULT TRUE,
    donor_id INT NOT NULL,
    CONSTRAINT fk_donation_donor FOREIGN KEY (donor_id) REFERENCES Donor(id)
    ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE DonationCollection (
    id INT AUTO_INCREMENT PRIMARY KEY,
    donation_id INT NOT NULL,
    association_id INT NOT NULL,
    quantity INT NOT NULL,
    dateDonationCollected DATE NOT NULL,
    CONSTRAINT fk_donation FOREIGN KEY (donation_id) REFERENCES Donation(id)
    ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_association FOREIGN KEY (association_id) REFERENCES Association(id)
    ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT chk_quantity_positive CHECK (quantity >= 0)
);
