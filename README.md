# Donation Management System

## Overview
The Donation Management System is a Java Swing application designed to facilitate the management of donations between **donors** and **associations**. This application provides an intuitive graphical user interface for:

- **Donors** to manage their donations (create, update, delete, view).
- **Associations** to collect donations and manage their records.

## Features
### Donors
1. **Donation Management**: Donors can create, update, delete, and view their donations.
2. **Dashboard**: Donors have a dashboard listing all their donations and showing the details of collections made by associations.

### Associations
1. **Donation Collection**: Associations can view all available donations and collect them in specified quantities.
2. **Dashboard**: Associations have a dashboard that lists all the donations they have collected.

## Prerequisites
1. **Java Development Kit (JDK)** installed.
2. **MySQL Database** setup.
3. **IntelliJ IDEA** for running the project.
4. **MySQL Connector/J** jar file version `9.1.0` added to the `lib` directory.

## Setting up the Application

### 1. Database Setup
Execute the following SQL schema to create the required tables:

```sql
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
```

### 2. `.env` Configuration
Create a `.env` file in the root directory with a content like that:

```
DB_URL=jdbc:mysql://localhost:3306/your_db_name
DB_USER=your_db_username
DB_PASSWORD=your_db_password
```

### 3. Adding MySQL Connector
Download the [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/) version `9.1.0`.
Place the jar file (`mysql-connector-j-9.1.0.jar`) in the `lib` directory (relative to the project structure).

## Running the Application
Since the application uses a custom structure and is not Maven-based, follow these steps:

1. Open the project in IntelliJ IDEA.
2. Configure the Run/Debug configuration with the following command:
   ```
   -cp ".:../lib/mysql-connector-j-9.1.0.jar"
   ```
3. Run the application using IntelliJ IDEA.

## Usage
1. **Login**: Enter your credentials to log in as a donor or association user.
2. **Dashboard Navigation**:
   - Donors: Manage your donations and view collections.
   - Associations: View available donations and collect them.
3. **Real-time Updates**: Changes made by donors or associations are reflected immediately in the database.

## Screenshots
![Alt text](screenshots/welcome-page.avif)
![Alt text](screenshots/login-page.avif)
![Alt text](screenshots/register-donor.avif)
![Alt text](screenshots/register-association.avif)
![Alt text](screenshots/donations.avif)
![Alt text](screenshots/available-donations.avif)
![Alt text](screenshots/donation-dashboard.avif)
![Alt text](screenshots/association-dashboard.avif)

## Contributions
Contributions are welcome! Feel free to fork this repository and submit pull requests with improvements or fixes.

## License
This project is licensed under the [MIT License](LICENSE).
