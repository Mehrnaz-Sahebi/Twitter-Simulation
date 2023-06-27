package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.common.*;
import model.javafx_action.JavaFXImpl;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private TextField rightside_dayTxt;

    @FXML
    private TextField rightside_emailText;

    @FXML
    private TextField rightside_firstnameText;

    @FXML
    private TextField rightside_lastnameText;

    @FXML
    private TextField rightside_monthTxt;
    @FXML
    private TextField rightside_yearTxt;
    @FXML
    private TextField rightside_passText;

    @FXML
    private TextField rightside_passrepeatTxt;

    @FXML
    private TextField rightside_phonenumberText;
    @FXML
    private TextField rightside_usernameText;



    @FXML
    private Button side_Login_btn;
    @FXML
    private Button rightside_signUp_Btn;



    @FXML
    private Label username_alert;
    @FXML
    private Label birthdate_alert;



    @FXML
    private Label email_alert;

    @FXML
    private Label first_name_alert;

    @FXML
    private Label last_name_alert;
    @FXML
    private Label pass_alert;

    @FXML
    private Label pass_repeat_alert;

    @FXML
    private Label phonenumber_alert;

    @FXML
    private Label region_alert;
    @FXML
    private Label sign_up_alert;

    @FXML
    private ChoiceBox<String> chiceBox;
    private String countries[] = {"Afghanistan","Albania","Algeria","Andorra","Angola","Anguilla","Antigua &amp; Barbuda",
            "Argentina","Armenia","Aruba","Australia","Austria","Azerbaijan","Bahamas","Bahrain","Bangladesh","Barbados",
            "Belarus","Belgium","Belize","Benin","Bermuda","Bhutan","Bolivia","Bosnia &amp; Herzegovina","Botswana","Brazil",
            "British Virgin Islands","Brunei","Bulgaria","Burkina Faso","Burundi","Cambodia","Cameroon","Cape Verde",
            "Cayman Islands","Chad","Chile","China","Colombia","Congo","Cook Islands","Costa Rica","Cote D Ivoire","Croatia",
            "Cruise Ship","Cuba","Cyprus","Czech Republic","Denmark","Djibouti","Dominica","Dominican Republic","Ecuador",
            "Egypt","El Salvador","Equatorial Guinea","Estonia","Ethiopia","Falkland Islands","Faroe Islands","Fiji","Finland",
            "France","French Polynesia","French West Indies","Gabon","Gambia","Georgia","Germany","Ghana","Gibraltar","Greece",
            "Greenland","Grenada","Guam","Guatemala","Guernsey","Guinea","Guinea Bissau","Guyana","Haiti","Honduras","Hong Kong",
            "Hungary","Iceland","India","Indonesia","Iran","Iraq","Ireland","Isle of Man","Israel","Italy","Jamaica","Japan",
            "Jersey","Jordan","Kazakhstan","Kenya","Kuwait","Kyrgyz Republic","Laos","Latvia","Lebanon","Lesotho","Liberia",
            "Libya","Liechtenstein","Lithuania","Luxembourg","Macau","Macedonia","Madagascar","Malawi","Malaysia","Maldives",
            "Mali","Malta","Mauritania","Mauritius","Mexico","Moldova","Monaco","Mongolia","Montenegro","Montserrat",
            "Morocco","Mozambique","Namibia","Nepal","Netherlands","Netherlands Antilles","New Caledonia","New Zealand",
            "Nicaragua","Niger","Nigeria","Norway","Oman","Pakistan","Palestine","Panama","Papua New Guinea","Paraguay",
            "Peru","Philippines","Poland","Portugal","Puerto Rico","Qatar","Reunion","Romania","Russia","Rwanda",
            "Saint Pierre &amp; Miquelon","Samoa","San Marino","Satellite","Saudi Arabia","Senegal","Serbia","Seychelles",
            "Sierra Leone","Singapore","Slovakia","Slovenia","South Africa","South Korea","Spain","Sri Lanka",
            "St Kitts &amp; Nevis","St Lucia","St Vincent","St. Lucia","Sudan","Suriname","Swaziland","Sweden","Switzerland",
            "Syria","Taiwan","Tajikistan","Tanzania","Thailand","Timor L'Este","Togo","Tonga","Trinidad &amp; Tobago",
            "Tunisia","Turkey","Turkmenistan","Turks &amp; Caicos","Uganda","Ukraine","United Arab Emirates","United Kingdom",
            "Uruguay", "Uzbekistan","Venezuela","Vietnam","Virgin Islands (US)","Yemen","Zambia","Zimbabwe"};
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
        TwitterApplication.signInPage((Stage) side_Login_btn.getScene().getWindow(),signUpController.getSocket(),signUpController.getWriter(),signUpController.getJwt());
    }

    @FXML
    void setSign_up_button(ActionEvent event) {
        boolean isAllowedToRegister = true;
        String username = rightside_usernameText.getText();

        username = rightside_usernameText.getText();
        if(Validate.NotBlank(username)){
            username_alert.setText("enter the username");
            isAllowedToRegister = false;
        }
        String firstName = null;

        firstName = rightside_firstnameText.getText();
        if(Validate.NotBlank(firstName)) {
            first_name_alert.setText("enter your first name");
            isAllowedToRegister = false;
        }
        String lastName = null;
        lastName = rightside_lastnameText.getText();
        if(Validate.NotBlank(lastName)) {
            last_name_alert.setText("enter your last name");
            isAllowedToRegister = false;
        }
        String email = null;
        email = rightside_emailText.getText();
        if(Validate.NotBlank(email)) {
            email_alert.setText("enter your email");
            isAllowedToRegister = false;
        }else if (Validate.validateEmail(email) != ResponseOrErrorType.SUCCESSFUL) {
            email_alert.setText("Invalid Email");
            isAllowedToRegister = false;
        }
        String phoneNumber = null;
        phoneNumber = rightside_phonenumberText.getText();
        if(Validate.NotBlank(phoneNumber)) {
            phonenumber_alert.setText("enter your phone number");
            isAllowedToRegister = false;
        }else if (phoneNumber.length()!=11 ) {
            phonenumber_alert.setText("Invalid phone number");
            isAllowedToRegister = false;
            try {
                Integer.parseInt(phoneNumber);
            }catch (NumberFormatException e){
                phonenumber_alert.setText("Invalid phone number");
                isAllowedToRegister = false;
            }
        }


        String password = null;

        password = rightside_passText.getText();
        if(Validate.NotBlank(password)){
            pass_alert.setText("enter your password");
            isAllowedToRegister = false;
        }else if (Validate.validPass(password) != ResponseOrErrorType.SUCCESSFUL) {
            pass_alert.setText("Invalid password(correct pass = 8chars in both upper and lower case)");
            isAllowedToRegister = false;
        }
        String repeatPass = null;

        repeatPass = rightside_passrepeatTxt.getText();
        if(Validate.NotBlank(repeatPass)){
            pass_repeat_alert.setText("repeat your password");
            isAllowedToRegister = false;
        }else if (!repeatPass.equals(repeatPass)) {
            pass_repeat_alert.setText("Password doesn't match with its repetition");
            isAllowedToRegister = false;
        }
        String region = null;

        region = chiceBox.getValue();
        if(Validate.NotBlank(region)){
            region_alert.setText("enter your country");
            isAllowedToRegister = false;
        }
        String year = null;
        String month = null;
        String day = null;
        StringBuilder birthdateSB = new StringBuilder();

        year = rightside_yearTxt.getText();
        month = rightside_monthTxt.getText();
        day = rightside_dayTxt.getText();
        birthdateSB.append(year);
        birthdateSB.append("/");
        birthdateSB.append(month);
        birthdateSB.append("/");
        birthdateSB.append(day);
        Date birthdate =null;
        if(Validate.NotBlank(day, month, year)){
            birthdate_alert.setText("Enter your birth date completely");
            isAllowedToRegister = false;
        }else if(Validate.validateDateFormat(birthdateSB.toString()) != ResponseOrErrorType.SUCCESSFUL) {
            birthdate_alert.setText("Invalid birth date");
            isAllowedToRegister = false;
        }
        try {
            birthdate = new Date(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day));
        } catch (Exception e){
            isAllowedToRegister =false;
            birthdate_alert.setText("Invalid birth date");
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
                        email_alert.setText("");
                        birthdate_alert.setText("");
                        pass_repeat_alert.setText("");
                        phonenumber_alert.setText("");
                        first_name_alert.setText("");
                        last_name_alert.setText("");
                        sign_up_alert.setText("");
                        region_alert.setText("");
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
            sign_up_alert.setText(errorMsg);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chiceBox.getItems().addAll(countries);
    }
}