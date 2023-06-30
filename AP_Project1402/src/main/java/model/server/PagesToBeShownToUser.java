package model.server;
import model.common.*;
import model.database.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

public class PagesToBeShownToUser {

    public static  <T> SocketModel signInPage(UserToBeSigned user) throws SQLException {
        T out = SQLConnection.getUsers().select(user);

        if (out instanceof UserToBeSigned) {
            return new SocketModel(Api.TYPE_SIGNIN, user);
        } else {
            return new SocketModel(Api.TYPE_SIGNIN, (ResponseOrErrorType) out, (Object) null);
        }
    }


    public static SocketModel signUpPage(UserToBeSigned userModule) throws SQLException {
        UsersTable table = SQLConnection.getUsers();
        if (table.userNameExists(userModule.getUsername())){
            return new SocketModel(Api.TYPE_SIGNUP, ResponseOrErrorType.DUPLICATE_USERNAME, false);
        }
        if (table.emailExists(userModule.getEmail())){
            return new SocketModel(Api.TYPE_SIGNUP, ResponseOrErrorType.DUPLICATE_EMAIL, true);
        }
        if (table.phoneNumberExists(userModule.getPhoneNumber())){
            return new SocketModel(Api.TYPE_SIGNUP, ResponseOrErrorType.DUPLICATE_PHONENUMBER, false);
        }
        if (table.insert(userModule)) {
            userModule.setUserDbId(table.getUserFromDatabase(userModule.getUsername()).getDatabaseId());
            return new SocketModel(Api.TYPE_SIGNUP, ResponseOrErrorType.SUCCESSFUL, true);
        } else {
            return new SocketModel(Api.TYPE_SIGNUP, ResponseOrErrorType.UNSUCCESSFUL, false);
        }
    }

    public static ResponseOrErrorType updateProfile(User thisUser) {
        if(SafeRunning.safe(() -> {
            UsersTable out = SQLConnection.getUsers();
            out.updateUsername(thisUser.getDatabaseId(), thisUser.getUsername());
            out.updateHeader(thisUser.getDatabaseId(), thisUser.getHeader());
            out.updateBio(thisUser.getDatabaseId(), thisUser.getBio());
            out.updateWebsite(thisUser.getDatabaseId(), thisUser.getWebsite());
            out.updateAvatar(thisUser.getDatabaseId(), thisUser.getAvatar());
            out.updateLocation(thisUser.getDatabaseId(), thisUser.getLocation());
            out.updateBirthDate(thisUser.getDatabaseId(), thisUser.getBirthDate());
            out.updateFirstName(thisUser.getDatabaseId(), thisUser.getFirstName());
            out.updateLastName(thisUser.getDatabaseId(), thisUser.getLastName());
            out.updateEmail(thisUser.getDatabaseId(), thisUser.getEmail());
            out.updatePassword(thisUser.getDatabaseId(), thisUser.getPassword());
            out.updatePhoneNumber(thisUser.getDatabaseId(), thisUser.getPhoneNumber());
            out.updateRegion(thisUser.getDatabaseId(), thisUser.getRegionOrCountry());
        })){
            return ResponseOrErrorType.SUCCESSFUL;
        }else {
            return ResponseOrErrorType.UNSUCCESSFUL;
        }
    }

//    public static ResponseOrErrorType setProfileHeader(String JWT, String pathHeader) {
//        if (SafeRunning.safe(() -> {
//            SQLConnection.getUsers()
//        })){
//            return ResponseOrErrorType.SUCCESSFUL;
//        }else {
//            return ResponseOrErrorType.UNSUCCESSFUL;
//        }
//    }

//    public static ResponseOrErrorType setProfileBio(String JWT, String bio) {
//        final int MAX_LENGTH = 160;
//        if (bio.length() > MAX_LENGTH){
//            return ResponseOrErrorType.OUT_OF_BOUND_LENGTH;
//        }
//        if (SafeRunning.safe(() -> {
//            SQLConnection.getUsers().updateBio(JWT, bio);
//        })){
//            return ResponseOrErrorType.SUCCESSFUL;
//        }else {
//            return ResponseOrErrorType.UNSUCCESSFUL;
//        }
//
//    }

//    public static ResponseOrErrorType setLocation(String JWT, String location) {
//        if (SafeRunning.safe(() -> {
//            SQLConnection.getUsers().updateLocation(JWT, location);
//        })){
//            return ResponseOrErrorType.SUCCESSFUL;
//        }else {
//            return ResponseOrErrorType.UNSUCCESSFUL;
//        }
//    }

//    public static ResponseOrErrorType setWebsite(String JWT, String website) {
//        ResponseOrErrorType isValidLink = Validate.validateWebsite(website);
//        if (isValidLink == ResponseOrErrorType.INVALID_LINK){
//            //TODO handle it
//        }
//        if (SafeRunning.safe(() -> {
//            SQLConnection.getUsers().updateWebsite(JWT, website);
//        })){
//            return ResponseOrErrorType.SUCCESSFUL;
//        }else {
//            return ResponseOrErrorType.UNSUCCESSFUL;
//        }
//    }

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
        System.out.println("4");
        return TweetsFileConnection.addTweet(tweet);
    }
    public static SocketModel makeATimeLine(String username){
        ArrayList<Tweet> timeLine = new ArrayList<Tweet>();
        try {
            timeLine = TweetsFileConnection.makeATimeLine(username);
        }catch (Exception e){
            return new SocketModel(Api.TYPE_LOADING_TIMELINE,ResponseOrErrorType.UNSUCCESSFUL_FILE,timeLine);
        }
        return new SocketModel(Api.TYPE_LOADING_TIMELINE,ResponseOrErrorType.SUCCESSFUL,timeLine);
    }
    public static SocketModel unLikeTweet(Tweet tweet , String unlikerUsername){
        if(TweetsFileConnection.tweetGetUnLiked(tweet,unlikerUsername)){
            return new SocketModel(Api.TYPE_UNLIKE,ResponseOrErrorType.SUCCESSFUL);
        }
        return new SocketModel(Api.TYPE_UNLIKE,ResponseOrErrorType.UNSUCCESSFUL_FILE);
    }
    public static SocketModel likeTweet(Tweet tweet , String likerUsername){
        if(TweetsFileConnection.tweetGetLiked(tweet,likerUsername)){
            return new SocketModel(Api.TYPE_LIKE,ResponseOrErrorType.SUCCESSFUL);
        }
        return new SocketModel(Api.TYPE_LIKE,ResponseOrErrorType.UNSUCCESSFUL_FILE);
    }
    public static SocketModel undoReTweet(Tweet tweet , String username){
        if(TweetsFileConnection.tweetGetUnRetweeted(tweet,username)){
            return new SocketModel(Api.TYPE_UNDO_RETWEET,ResponseOrErrorType.SUCCESSFUL);
        }
        return new SocketModel(Api.TYPE_UNDO_RETWEET,ResponseOrErrorType.UNSUCCESSFUL_FILE);
    }
    public static SocketModel reTweet(Tweet tweet , String username){
        if(TweetsFileConnection.tweetGetRetweeted(tweet,username)){
            return new SocketModel(Api.TYPE_RETWEET,ResponseOrErrorType.SUCCESSFUL);
        }
        return new SocketModel(Api.TYPE_RETWEET,ResponseOrErrorType.UNSUCCESSFUL_FILE);
    }
    public static SocketModel quoteTweet(QuoteTweet quoteTweet){
        if(TweetsFileConnection.tweetGetsQuoted(quoteTweet)){
            return new SocketModel(Api.TYPE_QUOTE_TWEET,ResponseOrErrorType.SUCCESSFUL);
        }
        return new SocketModel(Api.TYPE_QUOTE_TWEET,ResponseOrErrorType.UNSUCCESSFUL_FILE);
    }
    public static SocketModel reply(Reply reply){
        if(TweetsFileConnection.tweetRecievesAReply(reply)){
            return new SocketModel(Api.TYPE_REPLY,ResponseOrErrorType.SUCCESSFUL);
        }
        return new SocketModel(Api.TYPE_REPLY,ResponseOrErrorType.UNSUCCESSFUL_FILE);
    }
    public static void setFollowingsOfUser(String username1 , String username2){

        UsersTable out = SQLConnection.getUsers();
        try {
            User profileUser = out.getUserFromDatabase(username1);
            profileUser.getFollowings().add(username2);
        } catch (SQLException e) {
            System.out.println("sql disconnected");
        }
    }
    public static void setFollowersOfUser(String username1 , String username2){

        UsersTable out = SQLConnection.getUsers();
        try {
            User profileUser = out.getUserFromDatabase(username1);
            profileUser.getFollowers().add(username1);
        } catch (SQLException e) {
            System.out.println("sql disconnected");
        }
    }
    public static SocketModel firstFollowsSecond(String username1 , String username2){
        FollowTable followTable = new FollowTable();
        try {
            followTable.firstFollowsSecond(username1,username2);
            setFollowersOfUser(username1, username2);
            setFollowingsOfUser(username1, username2);
            return goToTheUsersProfile(username2, true);
        }catch (SQLException e){
            return new SocketModel(null, ResponseOrErrorType.UNSUCCESSFUL, false);
        }
    }
    public static SocketModel firstUnFollowsSecond(String username1 , String username2){
        FollowTable followTable = new FollowTable();
        try {
            followTable.firstUnfollowsSecond(username1,username2);
            return goToTheUsersProfile(username2, true);
        }catch (SQLException e){
            return new SocketModel(null, ResponseOrErrorType.UNSUCCESSFUL, false);
        }
    }public static SocketModel firstBlocksSecond(String username1 , String username2){
        BlockTable blockTable = new BlockTable();
        try {
            blockTable.firstBlocksSecend(username1,username2);
            return goToTheUsersProfile(username2, true);
        }catch (SQLException e){
            return new SocketModel(null, ResponseOrErrorType.UNSUCCESSFUL, false);
        }
    }
    public static SocketModel firstUnBlocksSecond(String username1 , String username2){
        BlockTable blockTable = new BlockTable();
        try {
            blockTable.firstUnblockSecend(username1,username2);
            return goToTheUsersProfile(username2, true);
        }catch (SQLException e){
            return new SocketModel(null, ResponseOrErrorType.UNSUCCESSFUL, false);
        }
    }
    public static HashSet<User> searchInUsers(String key) throws SQLException {
        UsersTable out = SQLConnection.getUsers();
        return out.searchInUsers(key);
    }

    public static SocketModel goToTheUsersProfile(String userName, boolean othersProf){///////////////////////////////////////////////////////////////////////
        UsersTable out = SQLConnection.getUsers();
        try {
            User profileUser = out.getUserFromDatabase(userName);
            if (othersProf){
                profileUser.setPassword(null);
                profileUser.setEmail(null);
//                profileUser.set
            }
            return new SocketModel(null, ResponseOrErrorType.SUCCESSFUL, profileUser);
        } catch (SQLException e) {
            return new SocketModel(null, ResponseOrErrorType.USER_NOTFOUND, false);
        }
    }

}
