package DataBase;

import Server.ResponseOrErrorType;
import User.FakeUser;
import User.Running.SafeRunning;
import User.Running.Validate;

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
        private static final String COLUMN_PHONE_NUMBER = "phone_number";
    private static final String COLUMN_EMAIL = "email";


    @Override
    public synchronized void createTable() {
        executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_USERNAME + " string," +
                COLUMN_PASSWORD + " string," +
                COLUMN_FIRSTNAME + " string," +
                COLUMN_LASTNAME + " string," +
                COLUMN_EMAIL + " string, " +
                COLUMN_PHONE_NUMBER + " string" +
                ")");
    }

    public synchronized boolean insert(FakeUser userModel) {
        if (!Validate.NotBlank(  //if the info is empty, so returns false
                userModel.getUsername(),
                userModel.getPassword(),
                userModel.getFirstName(),
                userModel.getLastName(),
                userModel.getEmail()//check whether email or phone number, one of the should be non-empty/////
        )) {
            return false;
        }

        return SafeRunning.safe(() -> {
            String query = "INSERT INTO "  + TABLE_NAME +
                    "(" +
                    COLUMN_USERNAME  + ", " +
                    COLUMN_PASSWORD + ", " +
                    COLUMN_FIRSTNAME + ", " +
                    COLUMN_LASTNAME + ", " +
                    COLUMN_PHONE_NUMBER + ", " +
            COLUMN_EMAIL + ") VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = getConnection().prepareStatement(query);
            statement.setString(1, userModel.getUsername().trim());
            statement.setString(2, userModel.getPassword().trim());
            statement.setString(3, userModel.getFirstName().trim());
            statement.setString(4, userModel.getLastName().trim());
            statement.setString(5, userModel.getPhoneNumber().trim());
            statement.setString(6, userModel.getEmail().trim());
            statement.executeUpdate();
            statement.close();
        });
    }

    public <T> T select(FakeUser userModel) throws SQLException {
        String query = "SELECT " + COLUMN_FIRSTNAME + "," + COLUMN_LASTNAME + "," + COLUMN_EMAIL +
                " FROM " + TABLE_NAME + " WHERE " +
                COLUMN_USERNAME + "=?";

        PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, userModel.getUsername().trim());
//            statement.setString(2, userModel.getPassword().trim());
        ResultSet set = statement.executeQuery();
        FakeUser out = null;
        if (set.getString(COLUMN_PASSWORD).equals(userModel.getPassword())){
            if (set.next()) {
                out = new FakeUser();
                out.setUsername(userModel.getUsername());
                out.setFirstName(set.getString(COLUMN_FIRSTNAME));
                out.setLastName(set.getString(COLUMN_LASTNAME));
                out.setEmail(set.getString(COLUMN_EMAIL));
            }

            set.close();
            statement.close();
        }else {
                return (T)ResponseOrErrorType.INVALID_PASS;
        }
        return (T) out;
//        return  SafeRunning.safe(() -> {
//
//
//        }, null);
    }

    public synchronized boolean userNameExists(String userName) throws SQLException {
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
    public synchronized boolean emailExists(String email) throws SQLException {
        String query = "SELECT " + COLUMN_USERNAME + " FROM " + TABLE_NAME + " WHERE " +
                COLUMN_EMAIL + "=? LIMIT 1";

        PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, email.trim());
        ResultSet set = statement.executeQuery();

        boolean exists = set.next();
        set.close();
        statement.close();
        return exists;
    }
    public synchronized boolean phoneNumberExists(String phoneNumber) throws SQLException {
        String query = "SELECT " + COLUMN_USERNAME + " FROM " + TABLE_NAME + " WHERE " +
                COLUMN_PHONE_NUMBER + "=? LIMIT 1";

        PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, phoneNumber.trim());
        ResultSet set = statement.executeQuery();

        boolean exists = set.next();
        set.close();
        statement.close();
        return exists;
    }
}

