package model.common;

import model.database.BlockTable;
import model.database.FollowTable;
import model.database.SQLConnection;
import model.server.TweetsFileConnection;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;

public class User implements Serializable {



    private int databaseId;
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
    private String birthdateForProf;
    private String location;
    private String website;
    private String linkProf;
    private HashSet<String> followings = new HashSet<>();
    private int numOfFollowings;
    private HashSet<String> followers = new HashSet<>();
    private HashSet<String> blocker = new HashSet<>();
    private HashSet<String> blockings = new HashSet<>();
    private int numOfFollowers;
    private int numberOfTweets;
    private boolean toShowLocInProf = false;
    private boolean toShowBirthInProf = false;
    private boolean toShowRegInProf = false;

    public User(String username, String password, String firstName, String lastName, String email, String phoneNumber, String avatar, String header, String regionOrCountry, Date birthDate, Date signUpDate, Date lastModifiedDate, String bio, String location, String website) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.avatar = avatar;
        this.header = header;
        this.regionOrCountry = regionOrCountry;
        this.birthDate = birthDate;
        SignUpDate = signUpDate;
        this.lastModifiedDate = lastModifiedDate;
        this.bio = bio;
        this.location = location;
        this.website = website;
    }
    public void setDatabaseId(int databaseId) {
        this.databaseId = databaseId;
    }

    public void setBlocker(HashSet<String> blocker) {
        this.blocker = blocker;
    }

    public void setBlockings(HashSet<String> blockings) {
        this.blockings = blockings;
    }

    public HashSet<String> getBlocker() {
        return blocker;
    }

    public HashSet<String> getBlockings() {
        return blockings;
    }

    public int getDatabaseId() {
        return databaseId;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthdateForProf() {
        return birthdateForProf;
    }

    public void setBirthdateForProf(String birthdateForProf) {
        this.birthdateForProf = birthdateForProf;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getRegionOrCountry() {
        return regionOrCountry;
    }

    public void setRegionOrCountry(String regionOrCountry) {
        this.regionOrCountry = regionOrCountry;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getSignUpDate() {
        return SignUpDate;
    }

    public void setSignUpDate(Date signUpDate) {
        SignUpDate = signUpDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public HashSet<String> getFollowings() {
        return followings;
    }

    public void setFollowings(HashSet<String> followings) {
        this.followings = followings;
    }

    public HashSet<String> getFollowers() {
        return followers;
    }

    public int getNumOfFollowings() {
        numOfFollowings = followings.size();
        return numOfFollowings;
    }

    public int getNumOfFollowers() {
        numOfFollowers = followers.size();
        return numOfFollowers;
    }

    public void setFollowers(HashSet<String> followers) {
        this.followers = followers;
    }


    //methods
    public void tweet(Tweet tweet){
        TweetsFileConnection.addTweet(tweet);
    }
    public void reTweet(Tweet tweet){
        TweetsFileConnection.tweetGetRetweeted(tweet,username);
    }
    public void reply(Reply reply){
        TweetsFileConnection.tweetRecievesAReply(reply);
    }
    public void follow(String followingUsername){
        SafeRunning.safe(()-> {
            FollowTable table = SQLConnection.getInstance().followTable;
            table.firstFollowsSecond(username, followingUsername);
        });
    }

    public void unfollow(String followingUsername){
        SafeRunning.safe(()-> {
            FollowTable table = SQLConnection.getInstance().followTable;
            table.firstUnfollowsSecond(username, followingUsername);
        });
    }
    public void block(String blockingUsername){
        SafeRunning.safe(()-> {
            BlockTable table = SQLConnection.getInstance().blockTable;
            table.firstBlocksSecend(username, blockingUsername);
        });
    }

    public void unblock(String blockingUsername){
        SafeRunning.safe(()-> {
            BlockTable table = SQLConnection.getInstance().blockTable;
            table.firstUnblockSecend(username, blockingUsername);
        });
    }
    public boolean doesFollow(String username){
        for (String str:followings) {
            if(str.equals(username)){
                return true;
            }
        }
        return false;
    }
    public boolean doesBlock(String username){
        for (String str:blockings) {
            if(str.equals(username)){
                return true;
            }
        }
        return false;
    }


    public void setToShowLocInProf(boolean toShowLocInProf) {
        this.toShowLocInProf = toShowLocInProf;
    }

    public void setToShowBirthInProf(boolean toShowBirthInProf) {
        this.toShowBirthInProf = toShowBirthInProf;
    }

    public void setToShowRegInProf(boolean toShowRegInProf) {
        this.toShowRegInProf = toShowRegInProf;
    }

    public boolean isToShowLocInProf() {
        return toShowLocInProf;
    }

    public boolean isToShowBirthInProf() {
        return toShowBirthInProf;
    }

    public boolean isToShowRegInProf() {
        return toShowRegInProf;
    }
}
