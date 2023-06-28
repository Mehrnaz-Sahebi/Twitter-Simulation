package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import model.common.Countries;
import model.common.ResponseOrErrorType;
import model.common.User;
import model.common.Validate;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.zip.InflaterInputStream;

public class EditProfController implements Initializable {
    private Socket socket;
    private ObjectOutputStream writer;
    private String jwt;
    private User user;
    private EditProfController editProfileController;

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setWriter(ObjectOutputStream writer) {
        this.writer = writer;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUserProfController(EditProfController controller){
        this.editProfileController = controller;
    }
    @FXML
    private Label Name_Lbl;

    @FXML
    private Button Save_Btn;

    @FXML
    private Label Username_Lbl;

    @FXML
    private TextField bio_txt;

    @FXML
    private ToggleGroup birthdate_choices;

    @FXML
    private ChoiceBox<String> chiceBox;

    @FXML
    private Circle circle_prof;

    @FXML
    private Label day_lbl;

    @FXML
    private Label email_lbl;

    @FXML
    private TextField link_txt;

    @FXML
    private TextField loca_txt;

    @FXML
    private Label month_lbl;

    @FXML
    private RadioButton nope_birth;

    @FXML
    private RadioButton nope_reg;

    @FXML
    private Label phonenumber_lbl;

    @FXML
    private ToggleGroup reg_choices;

    @FXML
    private Label region_lbl;

    @FXML
    private Label year_lbl;

    @FXML
    private RadioButton yep_birth;

    @FXML
    private RadioButton yep_reg;

    @FXML
    void EditBirthdate(MouseEvent event) {
        String year = null, month = null, day = null;
        boolean isAllowedToEdit = true;
        try {
            year = year_lbl.getText();
            month = month_lbl.getText();
            day = day_lbl.getText();
        }catch (NullPointerException e){
            // TODO show alert label that date shouldn't be empty
            isAllowedToEdit = false;
        }
        StringBuilder birthdateSB = new StringBuilder();
        birthdateSB.append(year);
        birthdateSB.append("/");
        birthdateSB.append(month);
        birthdateSB.append("/");
        birthdateSB.append(day);
        Date birthdate =null;
        if(!Validate.NotBlank(day, month, year)){
            // TODO show alert label that date shouldn't be empty
            isAllowedToEdit = false;
        }else if(Validate.validateDateFormat(birthdateSB.toString()) != ResponseOrErrorType.SUCCESSFUL) {
            // TODO show alert label that date should be valid
            isAllowedToEdit = false;
        }
        try {
            birthdate = new Date(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day));
        } catch (Exception e){
            isAllowedToEdit =false;
        }
        if (isAllowedToEdit){
            user.setBirthDate(birthdate);
        }
    }

    @FXML
    void EditEmail(MouseEvent event) {
        String email = null;
        boolean isAllowedToEdit = true;
        try {
            email = email_lbl.getText();
        }catch (NullPointerException e){
            // TODO show alert label that date shouldn't be empty
            isAllowedToEdit = false;
        }
        if (isAllowedToEdit){
            user.setEmail(email);
        }
    }

    @FXML
    void EditPhone(MouseEvent event) {
        String phone = null;
        boolean isAllowedToEdit = true;
        try {
            phone = phonenumber_lbl.getText();
        }catch (NullPointerException e){
            // TODO show alert label that date shouldn't be empty
            isAllowedToEdit = false;
        }
        if (isAllowedToEdit){
            user.setPhoneNumber(phone);
        }
    }

    @FXML
    void EditRegion(MouseEvent event) {
        String region = null;
        boolean isAllowedToEdit = true;
        try {
            region = chiceBox.getValue();
        }catch (NullPointerException e){
            // TODO show alert label that date shouldn't be empty
            isAllowedToEdit = false;
        }
        if (isAllowedToEdit){
            user.setLocation(region); // TODO Region is incomplete
        }
    }

    @FXML
    void editName(MouseEvent event) {

    }

    @FXML
    void editProfilePhoto(MouseEvent event) {

    }

    @FXML
    void editUsername(MouseEvent event) {

    }

    @FXML
    void saveProfile(ActionEvent event) {

    }
    public void prepareProf() {
        setUsername_Lbl("@" + user.getUsername());
        setName_Lbl(user.getFirstName() + " " + user.getLastName());
        setBio_labl(user.getBio());
        setLocation_Labl(user.getLocation());
        setCircle_prof("C:\\Users\\TUF GAMING\\Desktop\\Java programing\\project\\AP_Project1402\\images\\popular-social-media-creative-Twitter-logo-png.png");
//      TODO   user.getAvatar()
    }


    public void setBio_labl(String bio_labl) {
        bio_txt.setText(bio_labl);
    }

//   TODO public void setBirthdate_Labl(Label birthdate_Labl) {
//        Birthdate_Labl = birthdate_Labl;
//    }


//  TODO  public void setLink_hyper(Hyperlink link_hyper) {
//        Link_hyper = link_hyper;
//    }

    public void setLocation_Labl(String location_Labl) {
        loca_txt.setText(location_Labl);
    }



    public void setName_Lbl(String name_Lbl) {
        Name_Lbl.setText(name_Lbl);
    }

    public void setUsername_Lbl(String  username_Lbl) {
        Username_Lbl.setText(username_Lbl);
    }

    public void setCircle_prof(String profUrl) {
        if (profUrl != null && !profUrl.isBlank()){
            Image prof = new Image(profUrl);
            circle_prof.setFill(new ImagePattern(prof));
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chiceBox.getItems().addAll(Countries.countries);
    }
}
