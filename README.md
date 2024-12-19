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
5. **Git installed on your machine.**

## Installation

### Step 1: Clone the Repository

Ensure Git is installed on your system. If not, install it from [Git Downloads](https://git-scm.com/downloads).

Run the following command to clone the repository:

```bash
git clone https://github.com/AyKrimino/donation-system-java-swing.git
```

Navigate to the project directory:

```bash
cd donation-system-java-swing
```

### Step 2: Set Up the Database

1. Create a MySQL database named `donation_db`.
2. Run the SQL schema provided in the `database/schema.sql` file to set up the tables.
3. Update the `.env` file with your database connection details:

```
DB_URL=jdbc:mysql://localhost:3306/YOUR_DATABASE_NAME
DB_USER=YOUR_DATABASE_USERNAME
DB_PASSWORD=YOUR_DATABASE_PASSWORD
```

### Step 3: Run the Application

#### Option 1: Run Directly Using IntelliJ IDEA

1. Open the project in IntelliJ IDEA.
2. Configure the classpath to include the `mysql-connector-j-9.1.0.jar` file located in the `lib/` directory.
3. Run the `Main` class to start the application.

#### Option 2: Run from the Terminal

1. Ensure the `mysql-connector-j-9.1.0.jar` file is in the `lib/` directory.
2. Compile the application:

```bash
javac -cp ".:./lib/mysql-connector-j-9.1.0.jar" app/*.java dao/*.java gui/*.java models/*.java services/*.java utils/*.java -d bin/
```

3. Run the application:

```bash
java -cp ".:./bin:./lib/mysql-connector-j-9.1.0.jar" app.Main
```

### Step 4: Add the MySQL Connector to `lib/`

Ensure the `mysql-connector-j-9.1.0.jar` file is available in the `lib/` directory. If not, download it from [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/).

## Usage
1. **Login**: Enter your credentials to log in as a donor or association user.
2. **Dashboard Navigation**:
   - Donors: Manage your donations and view collections.
   - Associations: View available donations and collect them.
3. **Real-time Updates**: Changes made by donors or associations are reflected immediately in the database.

## Diagrams

### Class Diagram
![Alt text](diagrams/classDiagram.avif)

### Use Case Diagram
![Alt text](diagrams/useCaseDiagram.avif)


## Screenshots
![Alt text](screenshots/welcome-page.avif)
![Alt text](screenshots/login-page.avif)
![Alt text](screenshots/register-donor.avif)
![Alt text](screenshots/register-association.avif)
![Alt text](screenshots/donations.avif)
![Alt text](screenshots/available-donations.avif)
![Alt text](screenshots/donor-dashboard.avif)
![Alt text](screenshots/association-dashboard.avif)

## Contributions
Contributions are welcome! Feel free to fork this repository and submit pull requests with improvements or fixes.

## License
This project is licensed under the [MIT License](LICENSE).
