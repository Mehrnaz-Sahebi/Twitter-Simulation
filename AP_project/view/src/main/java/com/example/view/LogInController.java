package com.example.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class LogInController {

    @FXML
    private Label password_text;
    @FXML
    private Label username_text;

    @FXML
    private Label password_alert;
    @FXML
    private Label username_alert;

    @FXML
    private Button sign_up_button;
    @FXML
    private Button log_in_button;


    public void setSign_up_button(ActionEvent event){
        Util.changeScene(event,"sign_up.fxml","sign up",null);
    }
    public void setLog_in_button(ActionEvent event){
        String userName = username_text.getText();
        String pass = password_text.getText();
        if (userName != null && pass != null){

            Util.changeScene(event,"logged_in.fxml","logged in",userName,);
        }else {
            if (userName == null){
                username_alert.setText("enter the username");
            }
            if (pass == null){
                password_alert.setText("enter the password");
            }
        }
    }
    public void getInfo(){


    }
}
