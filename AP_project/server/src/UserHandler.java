

import java.sql.Date;
import java.sql.SQLException;

public class UserHandler implements UserPages {

    @Override
    public <T> SocketModel signInPage(String userName, String password) throws SQLException {
        T out = SQLConnection.getUsers().select(new FakeUser(userName, password));

        if (out == null) {
            return new SocketModel(Api.TYPE_SIGNIN, (ResponseOrErrorType) out, null);
        } else {
            return new SocketModel(Api.TYPE_SIGNIN, (FakeUser)out);
        }
    }



    @Override
    public SocketModel signUpPage(String username, String firstName, String lastName, String email, String phoneNumber, String password, Date birthDate) throws SQLException {
        //boolean isValidFormat = Validate.validateDateFormat(birthDate);
        boolean isValidEmail = Validate.validateEmail(email);
//        if (!isValidFormat){
            //ask again for birthdate
//        }
//        if (//valid format of email){
//    }
        FakeUser userModule = new FakeUser(username, password, firstName, lastName, email, phoneNumber, birthDate);
        TableOfUsers table = SQLConnection.getUsers();
        if (table.userNameExists(username)){
            return new SocketModel(Api.TYPE_SIGNUP, ResponseOrErrorType.DUPLICATE_USERNAME, false);
        }
        if (table.emailExists(email)){
            return new SocketModel(Api.TYPE_SIGNUP, ResponseOrErrorType.DUPLICATE_EMAIL, false);
        }
        if (table.phoneNumberExists(phoneNumber)){
            return new SocketModel(Api.TYPE_SIGNUP, ResponseOrErrorType.DUPLICATE_PHONENUMBER, false);
        }
        if (SQLConnection.getUsers().insert(userModule)) {
            UserInfoTable userTab = SQLConnection.getInstance().createUserTables(username);
            userTab.insert(userModule);
            return new SocketModel(Api.TYPE_SIGNUP, ResponseOrErrorType.SUCCESSFUL, true);
        } else {
            return new SocketModel(Api.TYPE_SIGNUP, ResponseOrErrorType.UNSUCCESSFUL, false);
        }
    }

    @Override
    public void setProfileAvatar(String pathAvatar) {
        SafeRunning.safe(() -> {
            user.setProfileHeader(pathAvatar);//user is doing? JSON seb tokens
        });
    }

    @Override
    public void setProfileHeader(String pathHeader) {
        SafeRunning.safe(() -> {
            user.setProfileHeader(pathHeader);
        });
    }

    @Override
    public void setProfileBio(String bio) {
        SafeRunning.safe(() -> {
            user.setProfileBio(bio);
        });
    }
    @Override
    public SocketModel addTweet(Tweet tweet) {
        return TweetsFileConnection.addTweet(tweet);
    }
    public SocketModel tweetShowPage(){
        return null;
    }
    @Override
    public void homePage() {

    }
    @Override
    public void searchInUsers(String key) throws SQLException {
        TableOfUsers out = SQLConnection.getUsers();
        out.searchInUsers(key);
        //write the hashset to the client
    }

    @Override
    public void goToTheUsersProfile(String userName) {///////////////////////////////////////////////////////////////////////

    }

    @Override
    public void userInfoPage() {

    }

    @Override
    public void showTimeLine() {

    }
}
