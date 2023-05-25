package User;

import java.util.ArrayList;

public class User {
    private String username;
    private String firstNameAndLastName;//first name and last name are seperated by a space
    private String email;
    private String phoneNumber;
    private String password;
    private String regionOrCountry;
    private String birthDate;
    private String SignUpDate;
    private String lastModifiedDate;
    // avatar and header
    private String bio;
    ArrayList<User> followings;
    ArrayList<User> followers;
    ArrayList<User> blockList;
//    ArrayList<Tweet> tweets;
    //methods
    public void tweet(){}
    public void reTweet(){}
    public void reply(){}
}
