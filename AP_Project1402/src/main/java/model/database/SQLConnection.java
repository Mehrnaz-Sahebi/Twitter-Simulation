package model.database;


import model.common.SafeRunning;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class SQLConnection {

//    private static final File DB_FILE = new File("database.db");
    private static SQLConnection instance = null;

    public static SQLConnection getInstance() {
        if (instance == null) {
            instance = new SQLConnection();
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

    public void connect() throws SQLException, ClassNotFoundException {
        if (connection == null) {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "Ma@12345");
//            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myschema", "root", "Mehrnaz@1383#");
        }
        createTables();
    }

    public void close() {
        if (connection != null) {
            SafeRunning.safe(connection::close);
            instance = null;
        }
    }

    public Connection getConnection() {
        return connection;
    }

    // TABLES
    final UsersTable users = new UsersTable();

    private void createTables() {
        users.createTable();
        createFollowTable();
        createBlockTable();
    }

    public static UsersTable getUsers() {
        return getInstance().users;
    }
    public static final FollowTable followTable = new FollowTable();
    public FollowTable createFollowTable() {
        followTable.createTable();
        return followTable;
    }

    public static FollowTable getFollowTable() {
        return followTable;
    }

    public static final BlockTable blockTable = new BlockTable();
    public BlockTable createBlockTable() {
        blockTable.createTable();
        return blockTable;
    }

    public static BlockTable getBlockTable() {
        return blockTable;
    }
}
