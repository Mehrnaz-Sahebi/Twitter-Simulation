
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserInfoTable extends AbstractTable {
    private static String TABLE_NAME ;
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
    private static final String COLUMN_BIO = "bio";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_WEBSITE = "website";
    private static final String COLUMN_FOLLOWERS = "followers";
    private static final String COLUMN_FOLLOWINGS = "followings";
    private static final String COLUMN_BLOCKLIST = "blocklist";
    @Override
    public void createTable(String username) {
        TABLE_NAME = username;
        executeUpdate("CREATE TABLE IF NOT EXISTS " + username +
                "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_USERNAME + " string," +
                COLUMN_PASSWORD + " string," +
                COLUMN_FIRSTNAME + " string," +
                COLUMN_LASTNAME + " string," +
                COLUMN_EMAIL + " string, " +
                COLUMN_PHONE_NUMBER + " string" +
                COLUMN_AVATAR + " string," +
                COLUMN_HEADER + " string," +
                COLUMN_BIO + " string," +
                COLUMN_REGION + " string," +
                COLUMN_BIRTHDATE + " string, " +
                COLUMN_LOCATION + " string" +
                COLUMN_WEBSITE + " string," +
                COLUMN_FOLLOWERS + " int," +
                COLUMN_FOLLOWINGS + " int," +
                COLUMN_BLOCKLIST + " string," +
                ")");
    }
    public synchronized boolean insert(FakeUser userModel) {
        if (!Validate.NotBlank(  //if the info is empty, so returns false
                userModel.getUsername(),
                userModel.getPassword(),
                userModel.getFirstName(),
                userModel.getLastName(),
                userModel.getEmail(),//check whether email or phone number, one of the should be non-empty/////
                userModel.getBirthDate(),
                userModel.getPhoneNumber()//-->
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
                    COLUMN_BIRTHDATE + ", " +
                    COLUMN_EMAIL + ") VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = getConnection().prepareStatement(query);
            statement.setString(1, userModel.getUsername().trim());
            statement.setString(2, userModel.getPassword().trim());
            statement.setString(3, userModel.getFirstName().trim());
            statement.setString(4, userModel.getLastName().trim());
            statement.setString(5, userModel.getPhoneNumber().trim());
            statement.setString(6, userModel.getBirthDate().trim());
            statement.setString(7, userModel.getEmail().trim());
            statement.executeUpdate();
            statement.close();
        });
    }

    public synchronized void setProfileAvatar(String avatarPath) throws SQLException {
        String query = "INSERT INTO "  + TABLE_NAME +
                "(" + COLUMN_AVATAR + ") VALUES (?)";

        PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, avatarPath.trim());
    }
    public synchronized void setProfileHeader(String headerPath) throws SQLException {
        String query = "INSERT INTO "  + TABLE_NAME +
                "(" + COLUMN_HEADER + ") VALUES (?)";

        PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, headerPath.trim());
    }
    public synchronized void setProfileBio(String bio) throws SQLException {
        String query = "INSERT INTO "  + TABLE_NAME +
                "(" + COLUMN_BIO + ") VALUES (?)";

        PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, bio.trim());
    }


}
