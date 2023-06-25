package main.java.controller;

import com.example.view.common.src.UserToBeSigned;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class Util {
    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username){
        Parent root = null;
        if (username != null){


            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Util.class.getResource(fxmlFile));
                root = fxmlLoader.load();
                LoggedInController loggedInController = fxmlLoader.getController();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
    public synchronized static void openLoginForm(String username, String pass ,String jwt) {
        UserToBeSigned user = new UserToBeSigned();
        user.setUsername(username);
        user.setPassword(pass);

        SendMessage.write(socket, new SocketModel(Api.TYPE_SIGNIN, user,jwt), writer);

//        SocketApi.getInstance().writeAndListen(
//                new SocketModel(Api.TYPE_SIGNIN, user),
//                model -> {
//                    UserToBeSigned result = model.get();
//                    if (result == null) {
//                        System.out.println(model.message);
//                        openAccountMenu();
//                    } else {
//                        openChatPage(result);
//                    }
//                }
//        );
    }
}
