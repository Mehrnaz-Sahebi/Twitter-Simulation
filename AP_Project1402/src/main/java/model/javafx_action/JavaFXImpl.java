package model.javafx_action;

import model.common.Api;
import model.common.SocketModel;
import model.common.UserToBeSigned;
import model.client.*;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class JavaFXImpl {
    public synchronized static void Login(String username , String pass , Socket socket, ObjectOutputStream writer , String jwt) {
        UserToBeSigned user = new UserToBeSigned();
        user.setUsername(username);
        user.setPassword(pass);
        SendMessage.write(socket, new SocketModel(Api.TYPE_SIGNIN, user,jwt), writer);
    }
}
