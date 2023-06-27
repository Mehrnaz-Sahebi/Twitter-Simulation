package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

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
}
