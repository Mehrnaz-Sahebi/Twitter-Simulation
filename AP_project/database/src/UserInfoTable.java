
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
    private static final String COLUMN_SIGNUPDATE ="signUpDate";///
    private static final String COLUMN_LASTMODIFIEDDATE ="lastModifiedDate";///
    private static final String COLUMN_BIO = "bio";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_WEBSITE = "website";
    @Override
    public void createTable(String username) {
        TABLE_NAME = username;
        executeUpdate("CREATE TABLE IF NOT EXISTS '"+username+"'" +
                "(" +
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
                userModel.getEmail(),//check whether email or phone number, one of the should be non-empty/////
                userModel.getPhoneNumber()//-->
        )&& Validate.dateNotBlank(userModel.getBirthDate())) {
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
            statement.setDate(6,new java.sql.Date(userModel.getBirthDate().getTime()));
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
