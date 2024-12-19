# Donation Management System

## Table of Contents
- [Overview](#overview)
- [Features](#features)
  - [Donors](#donors)
  - [Associations](#associations)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
  - [Step 1: Clone the Repository](#step-1-clone-the-repository)
  - [Step 2: Set Up the Database](#step-2-set-up-the-database)
  - [Step 3: Run the Application](#step-3-run-the-application)
    - [Option 1: Run Directly Using IntelliJ IDEA](#option-1-run-directly-using-intellij-idea)
    - [Option 2: Run from the Terminal](#option-2-run-from-the-terminal)
  - [Step 4: Add the MySQL Connector to `lib/`](#step-4-add-the-mysql-connector-to-lib)
- [Usage](#usage)
- [Diagrams](#diagrams)
  - [Class Diagram](#class-diagram)
  - [Use Case Diagram](#use-case-diagram)
- [Screenshots](#screenshots)
- [Contributions](#contributions)
- [License](#license)

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
![Class Diagram](diagrams/classDiagram.avif)

### Use Case Diagram
![Use Case Diagram](diagrams/useCaseDiagram.avif)

## Screenshots

- **Welcome Page** <br>
  ![Welcome Page](screenshots/welcome-page.avif)

- **Login Page** <br>
  ![Login Page](screenshots/login-page.avif)

- **Register Donor Page** <br>
  ![Register Donor Page](screenshots/register-donor.avif)

- **Register Association Page** <br>
  ![Register Association Page](screenshots/register-association.avif)

- **Donations Page** <br>
  ![Donations Page](screenshots/donations.avif)

- **Available Donations Page** <br>
  ![Available Donations Page](screenshots/available-donations.avif)

- **Donor Dashboard** <br>
  ![Donor Dashboard](screenshots/donor-dashboard.avif)

- **Association Dashboard** <br>
  ![Association Dashboard](screenshots/association-dashboard.avif)

## Contributions
Contributions are welcome! Feel free to fork this repository and submit pull requests with improvements or fixes.

## License
This project is licensed under the [MIT License](LICENSE).
