package controller;

import javafx.scene.Node;
import javafx.stage.Stage;
import model.client.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.common.SocketModel;
import model.common.Validate;
import model.javafx_action.JavaFXImpl;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class LogInController {

    @FXML
    private Label username_alert;
    @FXML
    private Label pass_alert;

    @FXML
    private Label login_alert;

    @FXML
    private Button side_signupBtn;
    @FXML
    private Button rightside_loginBtn;

    @FXML
    private TextField rightside_usernameText;
    @FXML
    private TextField rightside_passText;
    private Socket socket;
    private ObjectOutputStream writer;
    private String jwt;
    private static LogInController logInController ;

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setWriter(ObjectOutputStream writer) {
        this.writer = writer;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public void setLogInController(LogInController logInController) {
        this.logInController = logInController;

    }

    public static LogInController getInstance() {
        return logInController;
    }


    public void setSign_up_button(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        TwitterApplication.signUpPage(stage, socket, writer, jwt);
    }

    public void setLog_in_button(ActionEvent event) {
        String userName = null, pass = null;
        boolean isAllowed = true;
        try {
            userName = rightside_usernameText.getText();
            if (!Validate.NotBlank(userName)){
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            username_alert.setText("enter the username");
            isAllowed = false;
        }
        try {
            pass = rightside_passText.getText();
            if (!Validate.NotBlank(pass)){
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            pass_alert.setText("enter the password");
            isAllowed = false;
        }
        Thread threadTask = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        pass_alert.setText("");
                        username_alert.setText("");
                    }
                });
            }
        });
        threadTask.start();
        if (isAllowed){
            JavaFXImpl.login(userName, pass, socket, writer, jwt);
        }
    }

    public void addLabel(String errorMsg) {
        login_alert.setText(errorMsg);
        Thread threadTask = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                       login_alert.setText("");
                    }
                });
            }
        });
        threadTask.start();

    }
}
