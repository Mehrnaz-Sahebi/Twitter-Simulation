package DataBase;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {

    private static final File DB_FILE = new File("database.db");
    private static SQLConnection instance = null;

    public static SQLConnection getInstance() {
        if (instance == null) {
            synchronized (DB_FILE) {
                if (instance == null) {
                    instance = new SQLConnection();
                }
            }
        }

        return instance;
    }

    public synchronized static void closeConnection() {
        if (instance != null) {
            instance.close();
        }
    }

    // CONNECTION
    Connection connection = null;

    private SQLConnection() {
    }

    public void connect() throws SQLException {
        if (connection != null) return;

        connection = DriverManager.getConnection("jdbc:sqlite:" + DB_FILE.getAbsolutePath());
        if (connection != null) {
            createTables();
        }
    }

    public void close() {
        if (connection != null) {
            safe(connection::close);
            instance = null;
        }
    }

    public Connection getConnection() {
        return connection;
    }

    // TABLES
    final TableOfUsers users = new TableOfUsers();

    private void createTables() {
        users.createTable();
    }

    public static TableOfUsers getUsers() {
        return getInstance().users;
    }
}
