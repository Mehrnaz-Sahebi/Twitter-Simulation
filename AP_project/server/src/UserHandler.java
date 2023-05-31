import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class UserHandler implements UserPages {

    @Override
    public <T> SocketModel signInPage(String userName, String password) throws SQLException {
        T out = SQLConnection.getUsers().select(new UserToBeSigned(userName, password));

        if (out instanceof UserToBeSigned) {
            return new SocketModel(Api.TYPE_SIGNIN, (UserToBeSigned)out);
        } else {
            return new SocketModel(Api.TYPE_SIGNIN, (ResponseOrErrorType) out, null);
        }
    }



    @Override
    public SocketModel signUpPage(String username, String firstName, String lastName, String email, String phoneNumber, String password, String passRepitition, String birthDate) throws SQLException, ParseException {
        //TODO show the countries list

        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(birthDate);//casting the str to Date class
        ResponseOrErrorType isValidDateFormat = Validate.validateDateFormat(birthDate);
        ResponseOrErrorType isValidEmail = Validate.validateEmail(email);
        ResponseOrErrorType isValidPass = Validate.validPass(password);
        boolean isEqualRepeatedPass = password.equals(passRepitition);

        //checkTheFormatAndAllowedConditions
        if (isValidDateFormat != ResponseOrErrorType.INVALID_DATEFORMAT){
            //TODO send response for client and come back
        }
        if (isValidEmail != ResponseOrErrorType.INVALID_EMAIL){
            //TODO send response for client and come back
        }
        if (isValidPass != ResponseOrErrorType.INVALID_PASS){
            //TODO send response for client and come back
        }
        if (!isEqualRepeatedPass){
            //TODO send response for client and come back
        }
        if (email.isBlank() && phoneNumber.isBlank()){
            //TODO send response that enter at least one of these items
        }
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
        UsersTable out = SQLConnection.getUsers();
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
