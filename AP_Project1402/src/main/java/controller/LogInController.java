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
        Util.changeScene(stage, "SignUp.fxml", "sign up", null);
    }

    public void setLog_in_button(ActionEvent event) {
        String userName = null, pass = null;
        try {

            userName = rightside_usernameText.getText();
        } catch (NullPointerException e) {
            username_alert.setText("enter the username");
        }
        try {
            pass = rightside_passText.getText();
        } catch (NullPointerException e) {
            pass_alert.setText("enter the password");
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

        if (!userName.isBlank() && !pass.isBlank()) {
            JavaFXImpl.login(userName, pass, socket, writer, jwt);

        } else if (userName.isBlank()) {
            username_alert.setText("enter the username");
        } else if (pass.isBlank()) {
            pass_alert.setText("enter the password");
        }
    }

    public void addLabel(String errorMsg) {
        if (errorMsg.equals("User not found")) {
            login_alert.setText(errorMsg);
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
                       login_alert.setText("");
                    }
                });
            }
        });
        threadTask.start();

    }
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        try {
//            listenerForFX = new ListenerForFX(new Socket("127.0.0.1", 8080));
//        } catch (IOException e) {
//            System.out.println("Server isn't available");
//        }

//        anchor_pane_txt.heightProperty().addListener(new ChangeListener<Number>() {
//            @Override
//            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
//
//            }
//        });


}
