package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.common.ResponseOrErrorType;
import model.common.SocketModel;
import model.common.User;
import model.database.SQLConnection;
import model.database.UsersTable;

import java.io.IOException;
import java.sql.SQLException;

public class Util {
    public static <T> T changeScene(Stage stage, String fxmlFile, String title){
        Parent root = null;
        FXMLLoader fxmlLoader = new FXMLLoader(Util.class.getResource(fxmlFile));
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
    public static User getUserFromDB(String userName, boolean othersProf){///////////////////////////////////////////////////////////////////////
        UsersTable out = SQLConnection.getUsers();
        try {
            User profileUser = out.getUserFromDatabase(userName);
            if (othersProf){
                profileUser.setPassword(null);
                profileUser.setEmail(null);
//                profileUser.set
            }
            return profileUser;
        } catch (SQLException e) {
            System.out.println("db disconnected");
        }
        return null;
    }
}
