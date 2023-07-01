package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

public class MessageItem {
    private String usernameOfThisUser;

    public void setUsernameOfThisUser(String usernameOfThisUser) {
        this.usernameOfThisUser = usernameOfThisUser;
    }

    @FXML
    private Label Msg_Lbl;

    @FXML
    private Label name_Lbl;

    @FXML
    private Circle profCircle;

    public void prepare(String msg){
        Msg_Lbl.setText(msg);
        name_Lbl.setText(usernameOfThisUser);
    }

}

