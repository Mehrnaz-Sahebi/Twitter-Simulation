package controller;

import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class TweetWithoutImageComponent extends AnchorPane implements Initializable {
    private HBox hBox;




    private VBox vBox;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hBox = new HBox();
        hBox.setPrefWidth(830-113);
        hBox.setPrefHeight(HBox.USE_COMPUTED_SIZE);
        hBox.setMinHeight(HBox.USE_COMPUTED_SIZE);
        hBox.setMaxWidth(830-113);

    }
}
