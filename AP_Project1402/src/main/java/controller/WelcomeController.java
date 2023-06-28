package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.client.ListenerForFX;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class WelcomeController {


    @FXML
    private Button sign_up_button;

    @FXML
    private Button sign_in_button;
    @FXML
    private Label alert_label;
    private Socket socket;
    private ObjectOutputStream writer;
    private String jwt;

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setWriter(ObjectOutputStream writer) {
        this.writer = writer;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    @FXML
    public void goToSignUpPage(ActionEvent event) {
        Stage stage = (Stage) sign_up_button.getScene().getWindow();
        TwitterApplication.signUpPage(stage,socket,writer,jwt);
    }
    @FXML
    public void goToSignInPage(ActionEvent event) {
        Stage stage = (Stage) sign_in_button.getScene().getWindow();
        TwitterApplication. signInPage(stage,socket,writer,jwt);
    }
    public void addAlert(String alert){
        alert_label.setText(alert);
        Thread threadTask = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        alert_label.setText("");
                    }
                });
            }
        });
        threadTask.start();
    }

}

