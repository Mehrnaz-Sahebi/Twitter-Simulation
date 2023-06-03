import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PagesToBeShownToUser {

    public static  <T> SocketModel signInPage(UserToBeSigned user) throws SQLException {
        T out = SQLConnection.getUsers().select(user);

        if (out instanceof UserToBeSigned) {
            return new SocketModel(Api.TYPE_SIGNIN, (UserToBeSigned)out);
        } else {
            return new SocketModel(Api.TYPE_SIGNIN, (ResponseOrErrorType) out, null);
        }
    }


    public static SocketModel signUpPage(UserToBeSigned userModule) throws SQLException, ParseException {

        UserToBeSigned userModule = new UserToBeSigned(username, password, firstName, lastName, email, phoneNumber, date);
        UsersTable table = SQLConnection.getUsers();
        if (table.userNameExists(username)){
            return new SocketModel(Api.TYPE_SIGNUP, ResponseOrErrorType.DUPLICATE_USERNAME, false);
        }
        if (table.emailExists(email)){
            return new SocketModel(Api.TYPE_SIGNUP, ResponseOrErrorType.DUPLICATE_EMAIL, false);
        }
        if (table.phoneNumberExists(phoneNumber)){
            return new SocketModel(Api.TYPE_SIGNUP, ResponseOrErrorType.DUPLICATE_PHONENUMBER, false);
        }
        if (table.insert(userModule)) {
            return new SocketModel(Api.TYPE_SIGNUP, ResponseOrErrorType.SUCCESSFUL, true);
        } else {
            return new SocketModel(Api.TYPE_SIGNUP, ResponseOrErrorType.UNSUCCESSFUL, false);
        }
    }

    public static ResponseOrErrorType setProfileAvatar(String JWT, String pathAvatar) {
        if(SafeRunning.safe(() -> {
            SQLConnection.getUsers().updateAvatar(JWT, pathAvatar);
        })){
            return ResponseOrErrorType.SUCCESSFUL;
        }else {
            return ResponseOrErrorType.UNSUCCESSFUL;
        }
    }

    public static ResponseOrErrorType setProfileHeader(String JWT, String pathHeader) {
        if (SafeRunning.safe(() -> {
            SQLConnection.getUsers().updateHeader(JWT, pathHeader);
        })){
            return ResponseOrErrorType.SUCCESSFUL;
        }else {
            return ResponseOrErrorType.UNSUCCESSFUL;
        }
    }

    public static ResponseOrErrorType setProfileBio(String JWT, String bio) {
        final int MAX_LENGTH = 160;
        if (bio.length() > MAX_LENGTH){
            return ResponseOrErrorType.OUT_OF_BOUND_LENGTH;
        }
        if (SafeRunning.safe(() -> {
            SQLConnection.getUsers().updateBio(JWT, bio);
        })){
            return ResponseOrErrorType.SUCCESSFUL;
        }else {
            return ResponseOrErrorType.UNSUCCESSFUL;
        }

    }

    public static ResponseOrErrorType setLocation(String JWT, String location) {
        if (SafeRunning.safe(() -> {
            SQLConnection.getUsers().updateLocation(JWT, location);
        })){
            return ResponseOrErrorType.SUCCESSFUL;
        }else {
            return ResponseOrErrorType.UNSUCCESSFUL;
        }
    }

    public static ResponseOrErrorType setWebsite(String JWT, String website) {
        ResponseOrErrorType isValidLink = Validate.validateWebsite(website);
        if (isValidLink == ResponseOrErrorType.INVALID_LINK){
            //TODO handle it
        }
        if (SafeRunning.safe(() -> {
            SQLConnection.getUsers().updateWebsite(JWT, website);
        })){
            return ResponseOrErrorType.SUCCESSFUL;
        }else {
            return ResponseOrErrorType.UNSUCCESSFUL;
        }
    }

    public static ResponseOrErrorType follow(String JWTusername, String followingUsername){
        UsersTable out = SQLConnection.getUsers();
        User currentUser = null;
        try {
            currentUser = out.getUserFromDatabase(JWTusername);
            currentUser.follow(followingUsername);
        } catch (SQLException e) {
            return ResponseOrErrorType.UNSUCCESSFUL;
        }
        return ResponseOrErrorType.SUCCESSFUL;
    }

    public static ResponseOrErrorType unfollow(String JWTusername, String unfollowingUsername){
        UsersTable out = SQLConnection.getUsers();
        User currentUser = null;
        try {
            currentUser = out.getUserFromDatabase(JWTusername);
            currentUser.unfollow(unfollowingUsername);
        } catch (SQLException e) {
            return ResponseOrErrorType.UNSUCCESSFUL;
        }
        return ResponseOrErrorType.SUCCESSFUL;
    }

    public static ResponseOrErrorType block(String JWTusername, String blockingUsername){
        UsersTable out = SQLConnection.getUsers();
        User currentUser = null;
        try {
            currentUser = out.getUserFromDatabase(JWTusername);
            currentUser.block(blockingUsername);
        } catch (SQLException e) {
            return ResponseOrErrorType.UNSUCCESSFUL;
        }
        return ResponseOrErrorType.SUCCESSFUL;
    }

    public static SocketModel addTweet(Tweet tweet) {
        return TweetsFileConnection.addTweet(tweet);
    }
    public SocketModel tweetShowPage(){
        return null;
    }

    public void homePage() {

    }

    public static void searchInUsers(String key) throws SQLException {
        UsersTable out = SQLConnection.getUsers();
        out.searchInUsers(key);
        //write the hashset to the client
    }

    public static void goToTheUsersProfile(String userName) throws SQLException {///////////////////////////////////////////////////////////////////////
        UsersTable out = SQLConnection.getUsers();
        User profileUser = out.getUserFromDatabase(userName);
        //TODO show the profile in the scenebuilder
    }
}
