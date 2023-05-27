import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class User implements Serializable {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String avatar;
    private String header;
    private String regionOrCountry;
    private Date birthDate;
    private Date SignUpDate;
    private Date lastModifiedDate;
    private String bio;
    private String location;
    private String website;
    private HashSet<String> followings;
    private HashSet<String> followers;
    private HashSet<String> blackList;

    //methods
    public void tweet(){}
    public void reTweet(){}
    public void reply(){}
}
