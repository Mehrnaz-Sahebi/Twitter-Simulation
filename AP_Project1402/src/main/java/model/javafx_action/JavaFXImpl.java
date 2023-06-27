package model.javafx_action;

import model.common.Api;
import model.common.SocketModel;
import model.common.User;
import model.common.UserToBeSigned;
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
}
