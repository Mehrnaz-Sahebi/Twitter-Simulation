

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;

public interface UserPages {

    public <T> SocketModel signInPage(String userName, String password) throws SQLException;//sign in and sign up should be shown, if the sign up is successful, then the home page is the same.


    SocketModel signUpPage(String username, String firstName, String lastName, String email, String phoneNumber, String password, String passRepitition, String birthDate) throws SQLException, ParseException;

    public ResponseOrErrorType setProfileAvatar(String JWT, String pathAvatar);
    public ResponseOrErrorType setProfileHeader(String JWT, String pathHeader);
    public ResponseOrErrorType setProfileBio(String JWT, String bio);
    public ResponseOrErrorType setLocation(String JWT, String location);
    public ResponseOrErrorType setWebsite(String JWT, String website);
    public ResponseOrErrorType follow(String JWTusername, String followingUsername) throws SQLException;
    public ResponseOrErrorType unfollow(String JWTusername, String unfollowingUsername) throws SQLException;
    public ResponseOrErrorType block(String JWTusername, String blockingUsername) throws SQLException;
    public void homePage();

    SocketModel addTweet(Tweet tweet);

    public void searchInUsers(String key) throws SQLException;//for follow or block or unfollow
    public void goToTheUsersProfile(String userName) throws SQLException;
    public void userInfoPage();
    public void showTimeLine();
    public SocketModel tweetShowPage();
}
