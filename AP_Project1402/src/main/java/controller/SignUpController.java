package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.client.SendMessage;
import model.common.*;
import model.console_action.ConsoleUtil;
import model.javafx_action.JavaFXImpl;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Date;

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
    private Label alert;
    @FXML
    private TextField region_text;

    @FXML
    private TextField year_text;
    private Socket socket;
    private ObjectOutputStream writer;
    private String jwt;
    private static SignUpController signUpController;

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setWriter(ObjectOutputStream writer) {
        this.writer = writer;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public void setSignUpController(SignUpController signUpController) {

        SignUpController.signUpController = signUpController;
    }

    public static SignUpController getInstance() {
        return signUpController;
    }


    public Socket getSocket() {
        return socket;
    }

    public ObjectOutputStream getWriter() {
        return writer;
    }

    public String getJwt() {
        return jwt;
    }

    @FXML
    void setLog_in_button(ActionEvent event) {
        TwitterApplication.signInPage((Stage) log_in_button.getScene().getWindow(),signUpController.getSocket(),signUpController.getWriter(),signUpController.getJwt());
    }

    @FXML
    void setSign_up_button(ActionEvent event) {
        boolean isAllowedToRegister = true;
        String username = username_text.getText();

        username = username_text.getText();
        if(username == null || username.equals("")){
            username_alert.setText("enter the username");
            isAllowedToRegister = false;
        }
        String firstName = null;

        firstName = first_name_text.getText();
        if(firstName==null || firstName.equals("")) {
            alert.setText("enter your first name");
            isAllowedToRegister = false;
        }
        String lastName = null;
        lastName = last_name_text.getText();
        if(lastName == null || lastName.equals("")) {
            alert.setText("enter your last name");
            isAllowedToRegister = false;
        }
        String email = null;
        email = email_text.getText();
        if(email == null || email.equals("")) {
            alert.setText("enter your email");
            isAllowedToRegister = false;
        }
        if (Validate.validateEmail(email) != ResponseOrErrorType.SUCCESSFUL) {
            alert.setText("Invalid Email");
            isAllowedToRegister = false;
        }
        String phoneNumber = null;
        phoneNumber = phone_number_text.getText();
        if(phoneNumber == null || phoneNumber.equals("")) {
            alert.setText("enter your phone number");
            isAllowedToRegister = false;
        }
//        if (phoneNumber.length()!=11 ) {
//            alert.setText("Invalid phone number");
//            isAllowedToRegister = false;
//        }
//        try {
//            Integer.parseInt(phoneNumber);
//        }catch (NumberFormatException e){
//            alert.setText("Invalid phone number");
//            isAllowedToRegister = false;
//        }
        String password = null;

        password = password_text.getText();
        if(password == null || password.equals("")){
            password_alert.setText("enter your password");
            isAllowedToRegister = false;
        }
        if (Validate.validPass(password) != ResponseOrErrorType.SUCCESSFUL) {
            password_alert.setText("Invalid password(correct pass = 8chars in both upper and lower case)");
            isAllowedToRegister = false;
        }
        String repeatPass = null;

        repeatPass = pass_rep_text.getText();
        if(repeatPass == null || repeatPass.equals("")){
            alert.setText("repeat your password");
            isAllowedToRegister = false;
        }
        if (!repeatPass.equals(repeatPass)) {
            alert.setText("Password doesn't match with its repetition");
            isAllowedToRegister = false;
        }
        //TODO show the countries list
        String region = null;

        region = region_text.getText();
        if(region == null || region.equals("")){
            alert.setText("Enter your country");
            isAllowedToRegister = false;
        }
        String year = null;
        String month = null;
        String day = null;
        StringBuilder birthdateSB = new StringBuilder();

        year = year_text.getText();
        month = month_text.getText();
        day = day_text.getText();
        if(day == null || month == null || year == null || day.equals("") || month.equals("") || year.equals("")){
            alert.setText("Enter your birth date completely");
            isAllowedToRegister = false;
        }
        birthdateSB.append(year);
        birthdateSB.append("/");
        birthdateSB.append(month);
        birthdateSB.append("/");
        birthdateSB.append(day);
        if(Validate.validateDateFormat(birthdateSB.toString()) != ResponseOrErrorType.SUCCESSFUL) {
            alert.setText("Invalid birth date");
            isAllowedToRegister = false;
        }
        Date birthdate =null;
        try {
            birthdate = new Date(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day));
        } catch (Exception e){
            isAllowedToRegister =false;
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
                        password_alert.setText("");
                        username_alert.setText("");
                        alert.setText("");
                    }
                });
            }
        });
        threadTask.start();
        if(isAllowedToRegister) {
            UserToBeSigned user = new UserToBeSigned(username, password, firstName, lastName, email, phoneNumber, birthdate, region);
            JavaFXImpl.signUp(user, signUpController.getSocket(), signUpController.getWriter(), signUpController.getJwt());
        }
    }
    public void addLabel(String errorMsg) {
        if (errorMsg.equals("User not found")) {
            alert.setText(errorMsg);
        }
    }

}