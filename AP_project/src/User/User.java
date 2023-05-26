package User;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
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
<<<<<<< HEAD
    //    ArrayList<Common.Tweet> tweets;
=======
//    ArrayList<Common.Tweet> tweets;
>>>>>>> fa5af0753254ec148910ebcb95c1d645961e36ac
    //methods
    public void tweet(){}
    public void reTweet(){}
    public void reply(){}
}
