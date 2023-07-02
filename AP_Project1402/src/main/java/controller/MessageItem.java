package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import model.common.Message;

import java.io.File;

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

    public void prepare(Message msg){
        Msg_Lbl.setText(msg.getMsg());
        name_Lbl.setText(usernameOfThisUser);
        Image image = null;
        if(msg.getAvtar()==null || msg.getAvtar().equals("") || !new File(msg.getAvtar()).exists()){
            File imageFile = new File("AP_Project1402//images//download2.png");
            image = new Image(imageFile.getAbsolutePath());
        }else {
            File imageFile = new File(msg.getAvtar());
            image = new Image(imageFile.getAbsolutePath());
        }
        profCircle.setFill(new ImagePattern(image));
    }

}

