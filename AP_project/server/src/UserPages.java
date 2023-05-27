

import java.sql.SQLException;

public interface UserPages {

    public <T> SocketModel signInPage(String userName, String password) throws SQLException;//sign in and sign up should be shown, if the sign up is successful, then the home page is the same.

    SocketModel signUpPage(String username, String firstName, String lastName, String email, String phoneNumber, String password, String birthDate) throws SQLException;

    //    SocketModel signUpPage(String username, String firstName, String lastName, String email, String phoneNumber, String password, String birthDate) throws SQLException;
    public void setProfileAvatar(String pathAvatar);
    public void setProfileHeader(String pathHeader);
    public void setProfileBio(String bio);
    public void homePage();

    SocketModel addTweet(Tweet tweet);

    public void searchInUsers(String key) throws SQLException;//for follow or block or unfollow
    public void goToTheUsersProfile(String userName);
    public void userInfoPage();
    public void showTimeLine();
    public SocketModel tweetShowPage();
}
