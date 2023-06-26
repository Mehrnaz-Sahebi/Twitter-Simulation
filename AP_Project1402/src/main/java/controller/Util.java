package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.common.UserToBeSigned;

import java.io.IOException;

public class Util {
    public static <T> T changeScene(Stage stage, String fxmlFile, String title, String username){
        Parent root = null;
        FXMLLoader fxmlLoader = new FXMLLoader(Util.class.getResource(fxmlFile));
//        if (username != null) {
//        }
            try {
                root = fxmlLoader.load();
                Scene scene = new Scene(root);
                stage.setTitle(title);
                stage.setScene(scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        return  fxmlLoader.getController();
    }
    public synchronized static void openLoginForm(String username, String pass ,String jwt) {
        UserToBeSigned user = new UserToBeSigned();
        user.setUsername(username);
        user.setPassword(pass);

//        SendMessage.write(socket, new SocketModel(Api.TYPE_SIGNIN, user,jwt), writer);

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
