# Twitter Simulation

A simple implementation of a Twitter-like platform. This project was created as part of an Advanced Programming course and allows users to perform essential social media activities in an intuitive and lightweight way.

## Features
### User Management

- Sign up, log in, and manage your account.
- View and edit your profile picture.
- View other users' profile pictures.

### Timeline & Tweets

- Explore a timeline that displays tweets from users you follow.
- Tweet, retweet, reply, and quote other users' tweets.
- Attach photos to your tweets.
- Like and unlike tweets.
- Filter tweets by hashtags, user mentions, or keywords.

### Search

- Search for users.
- Search for specific hashtags.

### Social Interactions

- Follow, unfollow, and block users.

### Messaging

- Send and receive direct messages.

## Technologies Used

- **Java**
- **JavaFX** for UI design
- **CSS** for styling
- **MySQL** for database management

## Installation

1. Clone this repository to your local machine:
```bash
git clone https://github.com/Mehrnaz-Sahebi/twitter-simulation.git  
```
2. Set up the MySQL database:

    - Create a MySQL database for the project.
    - Import any provided SQL scripts (if available) to create the necessary tables.
    - Update the database connection information in the file located at       `model/database/SQLConnection` (line 37).
    Replace the placeholders with your actual database credentials:
  ```bash
connection = DriverManager.getConnection("jdbc:mysql://<hostname>:<port>/<database_name>", "<username>", "<password>");
```
3. Open the project in your preferred Java IDE (e.g., IntelliJ IDEA or Eclipse).

4. Set up and run both the **server** and **client**:
    - First, run the server file.
    - Then, run the client file.
    - Both must be running simultaneously for the application to work correctly.

## Usage

1. Launch the application.
2. Sign up to create an account.
3. Use the intuitive interface to:
    - Post tweets and interact with others.
    - Explore your timeline and filter tweets.
    - Manage social interactions and direct messaging.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Authors  
- **Mehrnaz Sahebi** [GitHub Profile](https://github.com/Mehrnaz-Sahebi)  
- **Mahsa Nasehi** [GitHub Profile](https://github.com/MahsaNasehi)

---
### About  

This project was developed as part of my **Advanced Programming course** to demonstrate object-oriented programming, database integration, and UI design principles.
