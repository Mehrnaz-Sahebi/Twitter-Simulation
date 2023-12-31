package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.common.Countries;
import model.common.ResponseOrErrorType;
import model.common.User;
import model.common.Validate;
import model.javafx_action.JavaFXImpl;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
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
    private ImageView header_imgview;

    @FXML
    private Label day_lbl;

    @FXML
    private Label email_lbl;

    @FXML
    private TextField link_txt;


    @FXML
    private Button Back_btn;
    @FXML
    private TextField profilePath_txt;

    @FXML
    private TextField headerPath_txt;

    @FXML
    private Label month_lbl;

    @FXML
    private RadioButton nope_birth;

    @FXML
    private RadioButton nope_reg;
    @FXML
    private RadioButton nope_loc;

    @FXML
    private RadioButton yep_loc;

    @FXML
    private Label phonenumber_lbl;
    @FXML
    private Label alert_Lbl;

    @FXML
    private ToggleGroup reg_choices;
    @FXML
    private Button deleteHeader_btn;

    @FXML
    private Button dlt_avatar_btn;
    @FXML
    private Button dlt_bio_btn;

    @FXML
    private Button dlt_link_btn;
    @FXML
    private Label region_lbl;

    @FXML
    private Label year_lbl;

    @FXML
    private RadioButton yep_birth;

    @FXML
    private RadioButton yep_reg;

    @FXML
    private TextField username_Txt;
    @FXML
    private TextField phone_Txt;
    @FXML
    private TextField name_Txt;

    @FXML
    private TextField email_Txt;
    @FXML
    private TextField location_Txt;
    @FXML
    private Label header_alert;
    @FXML
    private Label avatar_alert;
    private String header_path = null;
    private String avatar_path = null;


    @FXML
    void EditBirthdate(MouseEvent event) {
//        String year = null, month = null, day = null;
//        boolean isAllowedToEdit = true;
//        try {
//            year = year_lbl.getText();
//            month = month_lbl.getText();
//            day = day_lbl.getText();
//        }catch (NullPointerException e){
//            // TODO show alert label that date shouldn't be empty
//            isAllowedToEdit = false;
//        }
//        StringBuilder birthdateSB = new StringBuilder();
//        birthdateSB.append(year);
//        birthdateSB.append("/");
//        birthdateSB.append(month);
//        birthdateSB.append("/");
//        birthdateSB.append(day);
//        Date birthdate =null;
//        if(!Validate.NotBlank(day, month, year)){
//            // TODO show alert label that date shouldn't be empty
//            isAllowedToEdit = false;
//        }else if(Validate.validateDateFormat(birthdateSB.toString()) != ResponseOrErrorType.SUCCESSFUL) {
//            // TODO show alert label that date should be valid
//            isAllowedToEdit = false;
//        }
//        try {
//            birthdate = new Date(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day));
//        } catch (Exception e){
//            isAllowedToEdit =false;
//        }
//        if (isAllowedToEdit){
//            user.setBirthDate(birthdate);
//        }
    }

    @FXML
    void EditEmail(MouseEvent event) {
//        String email = null;
//        boolean isAllowedToEdit = true;
//        try {
//            email = email_lbl.getText();
//        }catch (NullPointerException e){
//            // TODO show alert label that date shouldn't be empty
//            isAllowedToEdit = false;
//        }
//        if (isAllowedToEdit){
//            user.setEmail(email);
//        }
    }

    @FXML
    void EditPhone(MouseEvent event) {
//        String phone = null;
//        boolean isAllowedToEdit = true;
//        try {
//            phone = phonenumber_lbl.getText();
//        }catch (NullPointerException e){
//            // TODO show alert label that date shouldn't be empty
//            isAllowedToEdit = false;
//        }
//        if (isAllowedToEdit){
//            user.setPhoneNumber(phone);
//        }
    }

    @FXML
    void EditRegion(MouseEvent event) {
//        String region = null;
//        boolean isAllowedToEdit = true;
//        try {
//            region = chiceBox.getValue();
//        }catch (NullPointerException e){
//            // TODO show alert label that date shouldn't be empty
//            isAllowedToEdit = false;
//        }
//        if (isAllowedToEdit){
//            user.setLocation(region); // TODO Region is incomplete
//        }
    }

    @FXML
    void editName(MouseEvent event) {
//        String name = null;
//        boolean isAllowedToEdit = true;
//        try {
//            name = Name_Lbl.getText();
//        }catch (NullPointerException e){
//            // TODO show alert label that date shouldn't be empty
//            isAllowedToEdit = false;
//        }
//        if (isAllowedToEdit){
//            user.setPhoneNumber(name);
//        }
    }

    @FXML
    void editProfilePhoto(MouseEvent event) {
//        String path = null;
//        boolean isAllowedToEdit = true;
//        try {
//            path = profilePath_txt.getText();
//        }catch (NullPointerException e){
//            // TODO show alert label that date shouldn't be empty
//            isAllowedToEdit = false;
//        }
//        if (isAllowedToEdit){
//            user.setAvatar(path);
//        }
    }

    @FXML
    void editUsername(MouseEvent event) {
//        String username = null;
//        boolean isAllowedToEdit = true;
//        try {
//            username = Username_Lbl.getText();
//        }catch (NullPointerException e){
//            // TODO show alert label that date shouldn't be empty
//            isAllowedToEdit = false;
//        }
//        if (isAllowedToEdit){
//            user.setUsername(username);
//        }
    }

    @FXML
    void deleteAvatar(ActionEvent event) {
//        File imagefile = new File("images\\download2.png");
        File imagefile = new File("AP_Project1402\\images\\download2.png");
        Image image = new Image(imagefile.toURI().toString());

        circle_prof.setFill(new ImagePattern(image));
        profilePath_txt.setText("");
        user.setAvatar(null);
    }

    @FXML
    void deleteHeader(ActionEvent event) {
//        Image image = new Image("images\\R6wDve.jpg");
//        header_imgview.setImage(image);
        header_imgview.setImage(null); // TODO doubtful
        headerPath_txt.setText("");
        user.setHeader(null);
    }
    @FXML
    void deleteBio(ActionEvent event) {
        bio_txt.setText("");
        user.setBio(null);
    }
    @FXML
    void deleteLink(ActionEvent event) {
        link_txt.setText("");
        //TODO
    }
    @FXML
    void GoBack(ActionEvent event) {
        TwitterApplication.goBackProfilePage((Stage) ((Node) event.getSource()).getScene().getWindow(), socket, writer, jwt, user);
    }
    @FXML
    void saveProfile(ActionEvent event) {
        String name = null;
        boolean isAllowedToEdit = true;
        try {
            name = name_Txt.getText();
            String parts[] = name.split(" ");
            user.setFirstName(parts[0]);
            user.setLastName(parts[1]);
        }catch (NullPointerException e){
            alert_Lbl.setText("*Alert* :" + "enter name");
            isAllowedToEdit = false;
        }


        String username = null;
        try {
            username = username_Txt.getText();
            user.setUsername(username);
        }catch (NullPointerException e){
            alert_Lbl.setText(alert_Lbl.getText() + "\n" + "*Alert* :" + "enter username");
            isAllowedToEdit = false;
        }

        String path = null;
        if(avatar_path!=null && !avatar_path.equals("")){
            if(new File(avatar_path).exists()){
                path = "AP_Project1402//images//avatar-images//" + username + ".png";
//                path = "images//avatar-images//" + username + ".png";
                File newImageFile = new File(path);
                try {
                    Files.copy(new File(avatar_path).toPath(), newImageFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    addAvatarAlert("Couldn't save the photo. Try again.");
                }
                user.setAvatar(path);
            }
        }



        String headerPath = null;
        if(header_path!=null && !header_path.equals("")){
            if(new File(header_path).exists()){
                headerPath = "AP_Project1402//images//header-images//" + username + ".png";
//                path = "images//header-images" + username + ".png";
                File newImageFile = new File(headerPath);
                try {
                    Files.copy(new File(header_path).toPath(), newImageFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    addAvatarAlert("Couldn't save the photo. Try again.");
                }
                user.setHeader(headerPath);
            }
        }


        String email = null;
        try {
            email = email_Txt.getText();
            if (Validate.validateEmail(email) != ResponseOrErrorType.SUCCESSFUL) {
                alert_Lbl.setText("Invalid Email");
                isAllowedToEdit = false;
                throw new NullPointerException();
            }
            user.setEmail(email);
        }catch (NullPointerException e){
            alert_Lbl.setText(alert_Lbl.getText() + "\n" + "*Alert* :" + "enter valid email");
            isAllowedToEdit = false;
        }

        String phone = null;
        try {
            phone = phone_Txt.getText();
            if (phone.length()!=11 ) {
                alert_Lbl.setText("Invalid phone number");
                isAllowedToEdit = false;
                try {
                    Integer.parseInt(phone);
                }catch (NumberFormatException e){
                    phone_Txt.setText("Invalid phone number");
                    isAllowedToEdit = false;
                }
            }
            user.setPhoneNumber(phone);
        }catch (NullPointerException e){
            alert_Lbl.setText(alert_Lbl.getText() + "\n" + "*Alert* :" + "enter phone-number");
            isAllowedToEdit = false;
        }
        String region = null;
        try {
            region = chiceBox.getValue();
            user.setRegionOrCountry(region); // TODO Region is incomplete
        }catch (NullPointerException e){
            alert_Lbl.setText(alert_Lbl.getText() + "\n" + "*Alert* :" + "choose the country");
            isAllowedToEdit = false;
        }

        String location = null;
        try {
            location = location_Txt.getText();
            user.setLocation(location);
        }catch (NullPointerException ignored){
        }

        String link = null;
        try {
            link = link_txt.getText();
            user.setWebsite(link);
        }catch (NullPointerException ignored){
        }

        String year = null;
        String month = null;
        String day = null;
        StringBuilder birthdateSB = new StringBuilder();

        year = year_lbl.getText();
        month = month_lbl.getText();
        day = day_lbl.getText();
        birthdateSB.append(year);
        birthdateSB.append("/");
        birthdateSB.append(month);
        birthdateSB.append("/");
        birthdateSB.append(day);
        Date birthdate =null;
        if(!Validate.NotBlank(day, month, year)){
            alert_Lbl.setText("Enter your birth date completely");
            isAllowedToEdit = false;
        }else if(Validate.validateDateFormat(birthdateSB.toString()) != ResponseOrErrorType.SUCCESSFUL) {
            alert_Lbl.setText("Invalid birth date");
            isAllowedToEdit = false;
        }
        try {
            birthdate = new Date(Integer.parseInt(year) - 1900 ,Integer.parseInt(month) - 1,Integer.parseInt(day));
        } catch (Exception e){
            isAllowedToEdit =false;
            alert_Lbl.setText("Invalid birth date");
        }


        String bio = null;
        try {
            bio = bio_txt.getText();
            if (bio.length() > 160){
                alert_Lbl.setText(alert_Lbl.getText() + "\n" + "more than 160 chars in bio!");
                isAllowedToEdit = false;
                throw new NullPointerException();
            }
            user.setBio(bio);
        }catch (NullPointerException ignored){
        }

        if(yep_loc.isSelected()){
            user.setToShowLocInProf(true);
            user.setLocation(region);
        }else if (nope_loc.isSelected()){
            user.setToShowLocInProf(false);
            user.setLocation(null);
        }

        if(yep_birth.isSelected()){
            user.setToShowBirthInProf(true);
        }else if (nope_birth.isSelected()){
            user.setToShowBirthInProf(false);
        }

        if(yep_reg.isSelected()){
            user.setToShowRegInProf(true);
        }else if (nope_reg.isSelected()){
            user.setToShowRegInProf(false);
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
                    alert_Lbl.setText("");
                }
            });
        }
    });
        threadTask.start();
        if (isAllowedToEdit){
            //TODO
            JavaFXImpl.changeProf(user, socket, writer, jwt);
        }
    }
    public void prepareProf() {
        setUsername_Lbl(user.getUsername());
        setName_Lbl(user.getFirstName() + " " + user.getLastName());
        setBio_labl(user.getBio());
        setCountry_Labl(user.getRegionOrCountry());
        setLocation_Labl(user.getLocation());
        setCircle_prof(user.getAvatar());
        setEmail_lbl(user.getEmail());
        setPhonenumber_lbl(user.getPhoneNumber());
        setBirthdate_Labl(user.getBirthDate());
        setHeader_prof(user.getHeader());
        setLink_hyper(user.getWebsite());
    }


    public void setBio_labl(String bio_labl) {
        if (Validate.NotBlank(bio_labl) && !bio_labl.equals("null")){
            bio_txt.setText(bio_labl);
        }
    }

    public void setBirthdate_Labl(Date birthdate_Labl) {//TODO , comented in order to prevent error
        String parts[] = birthdate_Labl.toString().split("-");
        year_lbl.setText(parts[0]);
        month_lbl.setText(parts[1]);
        day_lbl.setText(parts[2]);
    }


  public void setLink_hyper(String link_hyper) {
        link_txt.setText(link_hyper);
    }

    public void setCountry_Labl(String location_Labl) {
            region_lbl.setText(location_Labl);
            chiceBox.setValue(location_Labl);
    }

    public void setLocation_Labl(String location_Labl) {
        try {
            String rest = location_Txt.getText();
            location_Txt.setText(rest + location_Labl);
        }catch (NullPointerException e){
            location_Txt.setText(location_Labl);
        }

    }
    public void setName_Lbl(String name_Lbl) {
        Name_Lbl.setText(name_Lbl);
        name_Txt.setText(name_Lbl);
    }

    public void setUsername_Lbl(String  username_Lbl) {
        Username_Lbl.setText(username_Lbl);
        username_Txt.setText(username_Lbl);
    }
    public void setEmail_lbl(String email) {
        email_lbl.setText(email);
        email_Txt.setText(email);
    }
    public void setPhonenumber_lbl(String phonenumberLbl) {
        phonenumber_lbl.setText(phonenumberLbl);
        phone_Txt.setText(phonenumberLbl);
    }


    public void setCircle_prof(String profUrl) {
        if (Validate.NotBlank(profUrl)&&new File(profUrl).exists()){
            File imagefile = new File(profUrl);
            Image prof = new Image(imagefile.getAbsolutePath());
            circle_prof.setFill(new ImagePattern(prof));
            profilePath_txt.setText(profUrl);
        }else {
            File imagefile = new File("AP_Project1402//images//download2.png");
//            File imagefile = new File("images\\download2.png");
            Image prof = new Image(imagefile.getAbsolutePath());
            circle_prof.setFill(new ImagePattern(prof));
        }

    }
    public void setHeader_prof(String profUrl) {
        if (Validate.NotBlank(profUrl) && new File(profUrl).exists()){
            File imagefile = new File(profUrl);
            Image prof = new Image(imagefile.getAbsolutePath());
            header_imgview.setImage(prof);
            headerPath_txt.setText(profUrl);
        }

    }
    public void  addLabel(String errorMsg){
        alert_Lbl.setText(errorMsg);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chiceBox.getItems().addAll(Countries.countries);
    }


    //images stuff
    @FXML
    public void uploadHeader(){
        FileChooser fil_chooser = new FileChooser();

        File file = fil_chooser.showOpenDialog((Stage) circle_prof.getScene().getWindow());

        if (file != null) {
            if (checkHeaderSize(file)) {
                header_path = file.getAbsolutePath();
                headerPath_txt.setText(header_path);
            } else {
                addHeaderAlert("The image should be 1500*500 not larger than 2MB");
            }
        }
    }
    public void uploadAvatar(){
        FileChooser fil_chooser = new FileChooser();

        File file = fil_chooser.showOpenDialog((Stage) circle_prof.getScene().getWindow());

        if (file != null) {
            if (checkAvatarSize(file)) {
                avatar_path = file.getAbsolutePath();
                profilePath_txt.setText(avatar_path);
            } else {
                addAvatarAlert("The image should be 400*400 not larger than 1MB");
            }
        }
    }
    public void addHeaderAlert(String alert) {
        header_alert.setText(alert);
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
                        header_alert.setText("");
                    }
                });
            }
        });
        threadTask.start();
    }
    public void addAvatarAlert(String alert) {
        avatar_alert.setText(alert);
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
                        avatar_alert.setText("");
                    }
                });
            }
        });
        threadTask.start();
    }
    public boolean checkHeaderSize (File imgFile) {
        int pos = imgFile.getName().lastIndexOf(".");
        if (pos == -1)
            addHeaderAlert("No extension for file: " + imgFile.getAbsolutePath());
        String suffix = imgFile.getName().substring(pos + 1);
        Iterator<ImageReader> iter = ImageIO.getImageReadersBySuffix(suffix);
        while(iter.hasNext()) {
            ImageReader reader = iter.next();
            try {
                ImageInputStream stream = new FileImageInputStream(imgFile);
                reader.setInput(stream);
                int width = reader.getWidth(reader.getMinIndex());
                int height = reader.getHeight(reader.getMinIndex());
                if(width!=1500 || height!=500){
                    return false;
                }
                if(Files.size(imgFile.toPath())/Math.pow(2,20)>2){
                    return false;
                }
            } catch (IOException e) {
                addHeaderAlert("Error reading: " + imgFile.getAbsolutePath());
            } finally {
                reader.dispose();
            }
        }
        return true;
    }
    public boolean checkAvatarSize (File imgFile) {
        int pos = imgFile.getName().lastIndexOf(".");
        if (pos == -1)
            addAvatarAlert("No extension for file: " + imgFile.getAbsolutePath());
        String suffix = imgFile.getName().substring(pos + 1);
        Iterator<ImageReader> iter = ImageIO.getImageReadersBySuffix(suffix);
        while(iter.hasNext()) {
            ImageReader reader = iter.next();
            try {
                ImageInputStream stream = new FileImageInputStream(imgFile);
                reader.setInput(stream);
                int width = reader.getWidth(reader.getMinIndex());
                int height = reader.getHeight(reader.getMinIndex());
                System.out.println(width );
                System.out.println(height);
                if(width!=400 || height!=400){
                    System.out.println("pix");
                    return false;
                }
                if(Files.size(imgFile.toPath())/Math.pow(2,20)>1 ){
                    System.out.println("size");
                    return false;
                }
            } catch (IOException e) {
                addAvatarAlert("Error reading: " + imgFile.getAbsolutePath());
            } finally {
                reader.dispose();
            }
        }
        return true;
    }
}
