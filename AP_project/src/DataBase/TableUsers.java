package DataBase;

import Server.ResponseOrErrorType;
import User.FakeUser;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class TableOfUsers extends AbstractTable {

    private static final String TABLE_NAME = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_FIRSTNAME = "first_name";
    private static final String COLUMN_LASTNAME = "last_name";
//    private static final String COLUMN_PHONE_NUMBER = "phone_number";
    private static final String COLUMN_EMAIL = "email";
//    private static final String COLUMN_COUNTRY = "region";


    @Override
    public synchronized void createTable() {
        executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_USERNAME + " string," +
                COLUMN_PASSWORD + " string," +
                COLUMN_FIRSTNAME + " string," +
                COLUMN_LASTNAME + " string," +
                COLUMN_EMAIL + " string" +
                ")");
    }

    public synchronized boolean insert(FakeUser userModel) {
        if (!Validation.allNotBlank(
                userModel.getUsername(),
                userModel.getPassword(),
                userModel.getFirstName(),
                userModel.getLastName(),
                userModel.getEmail()
        )) {
            return false;
        }

        return safe(() -> {
            String query = "INSERT INTO " + TABLE_NAME +
                    "(" +
                    COLUMN_USERNAME + ", " +
                    COLUMN_PASSWORD + ", " +
                    COLUMN_NAME + ", " +
                    COLUMN_EMAIL +
                    ") VALUES (?, ?, ?, ?)";

            PreparedStatement statement = getConnection().prepareStatement(query);
            statement.setString(1, userModel.getUsername().trim());
            statement.setString(2, userModel.getPassword().trim());
            statement.setString(3, userModel.getName().trim());
            statement.setString(4, userModel.getEmail().trim());
            statement.executeUpdate();
            statement.close();
        });
    }

    public synchronized FakeUser select(FakeUser userModel) {
        return safe(() -> {
            String query = "SELECT " + COLUMN_NAME + "," + COLUMN_EMAIL +
                    " FROM " + TABLE_NAME + " WHERE " +
                    COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=? LIMIT 1";

            PreparedStatement statement = getConnection().prepareStatement(query);
            statement.setString(1, userModel.getUsername().trim());
            statement.setString(2, userModel.getPassword().trim());
            ResultSet set = statement.executeQuery();

            UserModel out = null;
            if (set.next()) {
                out = new UserModel();
                out.setUsername(userModel.getUsername());
                out.setName(set.getString(COLUMN_NAME));
                out.setEmail(set.getString(COLUMN_EMAIL));
            }

            set.close();
            statement.close();
            return out;
        }, null);
    }

    public synchronized boolean exists(String userName) throws SQLException {
        String query = "SELECT " + COLUMN_LASTNAME + " FROM " + TABLE_NAME + " WHERE " +
                COLUMN_USERNAME + "=? LIMIT 1";

        PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, userName.trim());
        ResultSet set = statement.executeQuery();

        boolean exists = set.next();
        set.close();
        statement.close();
        return exists;
    }
}
