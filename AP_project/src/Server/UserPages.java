package Server;

import User.SocketModel;

import java.sql.SQLException;

public interface UserPages {

    public <T> SocketModel signInPage(String userName, String password) throws SQLException;//sign in and sign up should be shown, if the sign up is successful, then the home page is the same.

    SocketModel signUpPage(String username, String firstName, String lastName, String email, String phoneNumber, String password, String birthDate) throws SQLException;

    public void homePage();
    public void addTweet();
    public void searchInUsers();//for follow or block or unfollow
    public void userInfoPage();
    public void showTimeLine();

}
