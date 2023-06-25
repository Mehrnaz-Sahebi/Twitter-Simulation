package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SignUpController {

    @FXML
    private TextField day_text;

    @FXML
    private TextField email_text;

    @FXML
    private TextField first_name_text;

    @FXML
    private TextField last_name_text;

    @FXML
    private Button log_in_button;

    @FXML
    private TextField month_text;

    @FXML
    private TextField pass_rep_text;

    @FXML
    private Label password_alert;

    @FXML
    private TextField password_text;

    @FXML
    private TextField phone_number_text;

    @FXML
    private Button sign_up_button;

    @FXML
    private Label username_alert;

    @FXML
    private TextField username_text;

    @FXML
    private TextField year_text;

    @FXML
    void setLog_in_button(ActionEvent event) {
        Util.changeScene(event,"log_in.fxml","log in",null);    }

    @FXML
    void setSign_up_button(ActionEvent event) {

    }

}