import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;

public class UsersTable extends AbstractTable {

    private static final String TABLE_NAME = "users" ;
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_FIRSTNAME = "first_name";
    private static final String COLUMN_LASTNAME = "last_name";
    private static final String COLUMN_PHONE_NUMBER = "phone_number";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_AVATAR = "avatar";
    private static final String COLUMN_HEADER = "header";
    private static final String COLUMN_REGION = "country";
    private static final String COLUMN_BIRTHDATE = "birthdate";
    private static final String COLUMN_SIGNUPDATE ="signUpDate";///
    private static final String COLUMN_LASTMODIFIEDDATE ="lastModifiedDate";///
    private static final String COLUMN_BIO = "bio";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_WEBSITE = "website";
    @Override
    public void createTable() {
        executeUpdate("CREATE TABLE IF NOT EXISTS " +TABLE_NAME+ " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_FIRSTNAME + " TEXT, " +
                COLUMN_LASTNAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_PHONE_NUMBER + " TEXT, " +
                COLUMN_AVATAR + " TEXT, " +
                COLUMN_HEADER + " TEXT, " +
                COLUMN_BIO + " TEXT, " +
                COLUMN_REGION + " TEXT, " +
                COLUMN_BIRTHDATE + " DATE, " +
                COLUMN_SIGNUPDATE + " DATE, " +
                COLUMN_LASTMODIFIEDDATE + " DATE, " +
                COLUMN_LOCATION + " TEXT, " +
                COLUMN_WEBSITE + " TEXT, " +
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
                    COLUMN_EMAIL + ", " +
                    COLUMN_AVATAR + ", " +
                    COLUMN_HEADER + ", " +
                    COLUMN_REGION + ", " +
                    COLUMN_BIRTHDATE + ", " +
                    COLUMN_SIGNUPDATE + ", " +
                    COLUMN_LASTMODIFIEDDATE + ", " +
                    COLUMN_BIO + ", " +
                    COLUMN_LOCATION + ", " +
                    COLUMN_WEBSITE +
                    ") VALUES (?, ?, ?, ?, ?, ? ,? ,? ,? ,? ,? ,? ,? ,? ,?)";

            PreparedStatement statement = getConnection().prepareStatement(query);
            statement.setString(1, userModel.getUsername().trim());
            statement.setString(2, userModel.getPassword().trim());
            statement.setString(3, userModel.getFirstName().trim());
            statement.setString(4, userModel.getLastName().trim());
            statement.setString(5, userModel.getPhoneNumber().trim());
            statement.setString(6, userModel.getEmail().trim());
            statement.setString(7,null);
            statement.setString(8,null);
            statement.setString(9,null);
            statement.setDate(10,null);
            statement.setDate(11,null);
            statement.setDate(12,null);
            statement.setString(13,null);
            statement.setString(14,null);
            statement.setString(15,null);
            statement.executeUpdate();
            statement.close();
        });
    }
    //check followers and blocklist too
    public synchronized void updateUsername(String username, String newUsername) throws SQLException{
        String query = "UPDATE " + TABLE_NAME + "SET " + COLUMN_USERNAME + "= '"+newUsername+"'" + "WHERE "+ COLUMN_USERNAME + "= '"+username+"'";
        PreparedStatement statement = getConnection().prepareStatement(query);
        ResultSet set = statement.executeQuery();
        set.close();
    }
    public synchronized void updatePassword(String username, String newPassword) throws SQLException{
        String query = "UPDATE " + TABLE_NAME + "SET " + COLUMN_PASSWORD + "= '"+newPassword+"'" + "WHERE "+ COLUMN_USERNAME + "= '"+username+"'";
        PreparedStatement statement = getConnection().prepareStatement(query);
        ResultSet set = statement.executeQuery();
        set.close();
    }
    public synchronized void updateFirstName(String username, String newFirstName) throws SQLException{
        String query = "UPDATE " + TABLE_NAME + "SET " + COLUMN_FIRSTNAME + "= '"+newFirstName+"'" + "WHERE "+ COLUMN_USERNAME + "= '"+username+"'";
        PreparedStatement statement = getConnection().prepareStatement(query);
        ResultSet set = statement.executeQuery();
        set.close();
    }
    public synchronized void updateLastName(String username, String newLastName) throws SQLException{
        String query = "UPDATE " + TABLE_NAME + "SET " + COLUMN_LASTNAME + "= '"+newLastName+"'" + "WHERE "+ COLUMN_USERNAME + "= '"+username+"'";
        PreparedStatement statement = getConnection().prepareStatement(query);
        ResultSet set = statement.executeQuery();
        set.close();
    }
    public synchronized void updatePhoneNumber(String username, String newPhoneNumber) throws SQLException{
        String query = "UPDATE " + TABLE_NAME + "SET " + COLUMN_PHONE_NUMBER + "= '"+newPhoneNumber+"'" + "WHERE "+ COLUMN_USERNAME + "= '"+username+"'";
        PreparedStatement statement = getConnection().prepareStatement(query);
        ResultSet set = statement.executeQuery();
        set.close();
    }
    public synchronized void updateEmail(String username, String newEmail) throws SQLException{
        String query = "UPDATE " + TABLE_NAME + "SET " + COLUMN_EMAIL + "= '"+newEmail+"'" + "WHERE "+ COLUMN_USERNAME + "= '"+username+"'";
        PreparedStatement statement = getConnection().prepareStatement(query);
        ResultSet set = statement.executeQuery();
        set.close();
    }
    public synchronized void updateAvatar(String username, String newAvatar) throws SQLException{
        String query = "UPDATE " + TABLE_NAME + "SET " + COLUMN_AVATAR + "= '"+newAvatar+"'" + "WHERE "+ COLUMN_USERNAME + "= '"+username+"'";
        PreparedStatement statement = getConnection().prepareStatement(query);
        ResultSet set = statement.executeQuery();
        set.close();
    }
    public synchronized void updateHeader(String username, String newHeader) throws SQLException{
        String query = "UPDATE " + TABLE_NAME + "SET " + COLUMN_HEADER + "= '"+newHeader+"'" + "WHERE "+ COLUMN_USERNAME + "= '"+username+"'";
        PreparedStatement statement = getConnection().prepareStatement(query);
        ResultSet set = statement.executeQuery();
        set.close();
    }
    public synchronized void updateRegion(String username, String newRegion) throws SQLException{
        String query = "UPDATE " + TABLE_NAME + "SET " + COLUMN_REGION + "= '"+newRegion+"'" + "WHERE "+ COLUMN_USERNAME + "= '"+username+"'";
        PreparedStatement statement = getConnection().prepareStatement(query);
        ResultSet set = statement.executeQuery();
        set.close();
    }
    //Reminder: check if the date works like this
    public synchronized void updateBirthDate(String username, Date newDate) throws SQLException{
        String query = "UPDATE " + TABLE_NAME + "SET " + COLUMN_BIRTHDATE + "= '"+newDate+"'" + "WHERE "+ COLUMN_USERNAME + "= '"+username+"'";
        PreparedStatement statement = getConnection().prepareStatement(query);
        ResultSet set = statement.executeQuery();
        set.close();
    }
    public synchronized void updateSignUp(String username, Date newSignUpDate) throws SQLException{
        String query = "UPDATE " + TABLE_NAME + "SET " + COLUMN_SIGNUPDATE + "= '"+newSignUpDate+"'" + "WHERE "+ COLUMN_USERNAME + "= '"+username+"'";
        PreparedStatement statement = getConnection().prepareStatement(query);
        ResultSet set = statement.executeQuery();
        set.close();
    }public synchronized void updateLastModifiedDate(String username, Date newLastModifiedDate) throws SQLException{
        String query = "UPDATE " + TABLE_NAME + "SET " + COLUMN_LASTMODIFIEDDATE + "= '"+newLastModifiedDate+"'" + "WHERE "+ COLUMN_USERNAME + "= '"+username+"'";
        PreparedStatement statement = getConnection().prepareStatement(query);
        ResultSet set = statement.executeQuery();
        set.close();
    }
    public synchronized void updateBio(String username, String newBio) throws SQLException{
        String query = "UPDATE " + TABLE_NAME + "SET " + COLUMN_BIO + "= '"+newBio+"'" + "WHERE "+ COLUMN_USERNAME + "= '"+username+"'";
        PreparedStatement statement = getConnection().prepareStatement(query);
        ResultSet set = statement.executeQuery();
        set.close();
    }
    public synchronized void updateLocation(String username, String newLocation) throws SQLException{
        String query = "UPDATE " + TABLE_NAME + "SET " + COLUMN_LOCATION + "= '"+newLocation+"'" + "WHERE "+ COLUMN_USERNAME + "= '"+username+"'";
        PreparedStatement statement = getConnection().prepareStatement(query);
        ResultSet set = statement.executeQuery();
        set.close();
    }
    public synchronized void updateWebsite(String username, String newWebsite) throws SQLException{
        String query = "UPDATE " + TABLE_NAME + "SET " + COLUMN_WEBSITE + "= '"+newWebsite+"'" + "WHERE "+ COLUMN_USERNAME + "= '"+username+"'";
        PreparedStatement statement = getConnection().prepareStatement(query);
        ResultSet set = statement.executeQuery();
        set.close();
    }
    public <T> T select(FakeUser userModel) throws SQLException, SQLException {
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
    public synchronized HashSet<String> searchInUsers(String searchKey) throws SQLException {
        String query1 = "SELECT " + COLUMN_USERNAME + " FROM " + TABLE_NAME +  " WHERE " + COLUMN_USERNAME + " LIKE " + "'%" + searchKey + "%'" ;
        String query2 = "SELECT " + COLUMN_USERNAME + " FROM " + TABLE_NAME +  " WHERE " + COLUMN_FIRSTNAME + " LIKE " + "'%" + searchKey + "%'" ;
        String query3 = "SELECT " + COLUMN_USERNAME + " FROM " + TABLE_NAME +  " WHERE " + COLUMN_LASTNAME + " LIKE " + "'%" + searchKey + "%'" ;
        PreparedStatement statement1 = getConnection().prepareStatement(query1);
        PreparedStatement statement2 = getConnection().prepareStatement(query2);
        PreparedStatement statement3 = getConnection().prepareStatement(query3);
        ResultSet set1 = statement1.executeQuery();
        ResultSet set2 = statement2.executeQuery();
        ResultSet set3 = statement3.executeQuery();
        HashSet<String> userNames = new HashSet<>();
        while (set1.next()){
            userNames.add(set1.getString(COLUMN_USERNAME));
        }
        while (set2.next()){
            userNames.add(set2.getString(COLUMN_USERNAME));
        }
        while (set3.next()){
            userNames.add(set3.getString(COLUMN_USERNAME));
        }
        set1.close();
        set2.close();
        set3.close();
        statement1.close();
        statement2.close();
        statement3.close();
        return userNames;
    }
    public User getUserFromDatabase(String username) throws SQLException {
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE " + COLUMN_USERNAME + " = '"+username+"'" ;
        PreparedStatement statement = getConnection().prepareStatement(query);
        ResultSet set = statement.executeQuery();
        User user = new User(set.getString("username"),
                set.getString("password"),
                set.getString("first_name"),
                set.getString("last_name"),
                set.getString("email"),
                set.getString("phone_number"),
                set.getString("avatar"),
                set.getString("header"),
                set.getString("country"),
                set.getDate("birthdate"),
                set.getDate("signUpDate"),
                set.getDate("lastModifiedDate"),
                set.getString("bio"),
                set.getString("location"),
                set.getString("website"));
        FollowTable followTable = new FollowTable();
        user.setFollowers(followTable.getFollowers(username));
        user.setFollowings(followTable.getFollowings(username));
        user.setBlackList(new BlockTable().getBlockings(username));
        return user;
    }
}

