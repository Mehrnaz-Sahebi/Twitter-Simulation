package Server;
import DataBase.SQLConnection;
import User.FakeUser;
import User.SocketModel;
import User.Api;

import java.sql.SQLException;

public class UserHandler implements UserPages{

    @Override
    public SocketModel signInPage(String userName, String password) {
        FakeUser out = SQLConnection.getUsers().select(new FakeUser(userName, password));

        if (out == null) {
            return new SocketModel(Api.TYPE_LOGIN, "Username or password is wrong!", null);
        } else {
            return new SocketModel(Api.TYPE_LOGIN, out);
        }
    }

    @Override
    public SocketModel signUpPage(String username, String firstName, String lastName, String email, String phoneNumber, String password, String birthDate) throws SQLException {
        FakeUser userModule = new FakeUser(username, password, firstName, lastName, email, phoneNumber, birthDate);
        if (SQLConnection.getUsers().exists(userModule.getUsername())) {
            return new SocketModel(Api.TYPE_SIGNUP, "This username already exists!", false);
        }

        if (SQLConnection.getUsers().insert(userModule)) {
            return new SocketModel(Api.TYPE_SIGNUP, true);
        } else {
            return new SocketModel(Api.TYPE_SIGNUP, "Something went wrong!", false);
        }
    }

    @Override
    public void homePage() {

    }

    @Override
    public void addTweet() {

    }

    @Override
    public void searchInUsers() {

    }

    @Override
    public void userInfoPage() {

    }

    @Override
    public void showTimeLine() {

    }
}
