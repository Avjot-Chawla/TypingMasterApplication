# Typing Master Application

A JavaFX-based GUI application that helps users improve their typing skills with practice exercises and real-time performance tracking. The application features user authentication, accuracy monitoring, words-per-minute (WPM) tracking, and an SQLite database for progress storage.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Installation and Setup](#installation-and-setup)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Contributing](#contributing)
- [Authors and Acknowledgements](#authors-and-acknowledgements)

## Overview

The **Typing Master Application** is designed to provide an engaging and efficient typing practice environment. It includes features such as:

- **User Authentication**: Secure login and signup system.
- **Typing Practice**: Interactive typing sessions with word accuracy monitoring.
- **Performance Metrics**: Tracks **words per minute (WPM), accuracy, invalid words, and total words typed**.
- **Database Storage**: User data and typing history are stored in an **SQLite database** for easy access and analysis.

## Features

- **User Module**
  - Secure **login and signup**.
  - Personalized typing statistics and history tracking.

- **Typing Practice**
  - Different difficulty levels for practice.
  - Tracks words per minute (WPM) and accuracy.
  - Displays session statistics, including **total words typed, average WPM, and invalid words**.

- **Database Integration**
  - Uses **SQLite** for storing user credentials and typing records.
  - Secure storage and retrieval of user data.

## Technologies Used

- **Programming Language**: Java (JDK 8+)
- **Framework**: JavaFX (for GUI)
- **Database**: SQLite
- **Development Environment**: IntelliJ IDEA / Eclipse

## Installation and Setup

### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/TypingMasterApplication.git
cd TypingMasterApplication
```

### 2. Install Dependencies
- Ensure **Java 8 or later** is installed.
- Install **JavaFX** libraries.
- Install **SQLite** (bundled with the application).

### 3. Configure Database
- Run the following SQL commands to create the necessary database tables:
```sql
CREATE TABLE Users (
    Username TEXT PRIMARY KEY NOT NULL,
    Password TEXT NOT NULL
);

CREATE TABLE History (
    Username TEXT NOT NULL,
    DateTime TEXT NOT NULL,
    WPM INTEGER DEFAULT 0 NOT NULL,
    Accuracy INTEGER DEFAULT 0 NOT NULL,
    Invalid INTEGER DEFAULT 0 NOT NULL
);
```

## Usage

1. **Run the application**:
   ```bash
   java -jar TypingMaster.jar
   ```
2. **Login or Sign Up** to create an account.
3. **Start a typing session** to practice and track progress.
4. **View performance statistics** after each session.

## Project Structure

```
TypingMasterApplication/
├── src/                  # Main source code
│   ├── com/example/      # Java classes
│   │   ├── Typing_Master_Main.java   # Entry point of the application
│   │   ├── Login_Controller.java     # Handles user authentication
│   │   ├── Game_Controller.java      # Manages typing game logic
│   │   ├── DatabaseManager.java      # Handles SQLite database interactions
│   │   ├── Signup_Controller.java    # Manages user registration
│   └── fxml/             # JavaFX FXML files for UI
│       ├── Login.fxml
│       ├── Signup.fxml
│       ├── Game.fxml
│       ├── PlayGame.fxml
├── database/             # SQLite database storage
│   ├── TypingMasterDB.db
├── images/               # Image assets used in the UI
├── README.md             # This file
├── TypingMaster.jar      # Compiled runnable JAR file
└── LICENSE               # License file
```

## Contributing

Contributions are welcome! If you want to improve this project:
1. Fork the repository.
2. Create a new branch (`feature-improvement`).
3. Make changes and test them.
4. Submit a pull request.

## Authors and Acknowledgements

- **Avjot Singh Chawla** – RA2211003010584
- **Harsh Jaiswal** – RA2211003010580
- **Abhishek Singh** – RA2211003010543
- **Project Supervisor:** Dr. R. Thamizhamuthu  
  *Department of Computing Technologies, SRM Institute of Science and Technology*
  
