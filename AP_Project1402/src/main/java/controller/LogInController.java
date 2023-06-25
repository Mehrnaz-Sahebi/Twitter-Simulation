package controller;

import controller.client.Client;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.common.SocketModel;
import model.console_action.ConsoleImpl;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class LogInController implements Initializable {

    @FXML
    private AnchorPane anchor_pane_txt;


    @FXML
    private Label username_alert;
    @FXML
    private Label pass_alert;

    @FXML
    private Label login_alert;

    @FXML
    private Button sign_up_button;
    @FXML
    private Button log_in_button;

    @FXML
    private TextField username_field;
    @FXML
    private TextField password_field;
    private Client client;



    public void setSign_up_button(ActionEvent event){
        Util.changeScene(event,"sign_up.fxml","sign up",null);
    }
    public void setLog_in_button(ActionEvent event){
        String userName = null, pass = null;
        try {

            userName = username_field.getText();
        }catch (NullPointerException e){
            username_alert.setText("enter the username");
        }
        try {
            pass = password_field.getText();
        }catch (NullPointerException e){
            pass_alert.setText("enter the password");
        }
        Thread threadTask = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
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
//        login_alert.setText("uxa");

        if (!userName.isBlank() && !pass.isBlank()){
            SocketModel thisModel = ConsoleImpl.LoginForm(userName, pass, client);

            client.write(thisModel);
            client.recieveMessageFromServer(event);
//            Util.changeScene(event,"logged_in.fxml","logged in",userName);
        } else if (userName.isBlank()) {
            username_alert.setText("enter the username");
        } else if (pass.isBlank()) {
            pass_alert.setText("enter the password");
        }
//        else {
//            if (userName == null){
//
//            }
//            if (pass == null){
//
//            }
//        }
    }
    public void addLabel(String errorMsg){
        if (errorMsg.equals("User not found")){
            login_alert.setText(errorMsg); ////// error
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            client = new Client(new Socket("127.0.0.1", 8080));
        } catch (IOException e) {
            System.out.println("Server isn't available");
        }

//        anchor_pane_txt.heightProperty().addListener(new ChangeListener<Number>() {
//            @Override
//            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
//
//            }
//        });



    }
}
