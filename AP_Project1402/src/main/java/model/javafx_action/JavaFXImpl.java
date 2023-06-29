package model.javafx_action;

import model.common.*;
import model.client.*;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class JavaFXImpl {
    public synchronized static void login(String username , String pass , Socket socket, ObjectOutputStream writer , String jwt) {
        UserToBeSigned user = new UserToBeSigned();
        user.setUsername(username);
        user.setPassword(pass);
        SendMessage.write(socket, new SocketModel(Api.TYPE_SIGNIN, user,jwt), writer);
    }
    public synchronized static void signUp(UserToBeSigned user, Socket socket, ObjectOutputStream writer, String jwt){
        SendMessage.write(socket, new SocketModel(Api.TYPE_SIGNUP, user,jwt), writer);
    }
    public synchronized static void seeThisUserProf(String username, Socket socket, ObjectOutputStream writer, String jwt){
        UserToBeSigned user = new UserToBeSigned();
        user.setUsername(username);
        SendMessage.write(socket, new SocketModel(Api.TYPE_SEE_PROF, user,jwt), writer);
    }
    public synchronized static void changeProf(User user, Socket socket, ObjectOutputStream writer, String jwt){
        SendMessage.write(socket, new SocketModel(Api.TYPE_Update_PROF, user,jwt), writer);
    }
    public synchronized static void goToEditProfPage(User user, Socket socket, ObjectOutputStream writer, String jwt) {
        SendMessage.write(socket, new SocketModel(Api.TYPE_GO_TO_EDIT_PROF, user, jwt), writer);
    }
    public synchronized static void addTweet(Tweet tweet, Socket socket, ObjectOutputStream writer, String jwt){
        SendMessage.write(socket, new SocketModel(Api.TYPE_WRITING_TWEET, tweet,jwt), writer);
    }
    public synchronized static void searchWord(Socket socket, String word, ObjectOutputStream writer){
        SendMessage.write(socket, new SocketModel(Api.TYPE_USER_SEARCH,word), writer);
    }
}
