package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import model.common.User;
import model.javafx_action.JavaFXImpl;


import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;

public class UsersProfileController {

    private Socket socket;
    private ObjectOutputStream writer;
    private String jwt;
    private User user;
    private UsersProfileController usersProfileController;

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

    public void setUserProfController(UsersProfileController controller){
        this.usersProfileController = controller;
    }


    @FXML
    private Label Bio_labl;

    @FXML
    private Label Birthdate_Labl;

    @FXML
    private Button EditProf_Btn;

    @FXML
    private Label Followers_Lbl;

    @FXML
    private Label Followers_that_you_know_Labl;

    @FXML
    private Label Followings_Lbl;

    @FXML
    private Hyperlink Link_hyper;

    @FXML
    private Label Location_Labl;

    @FXML
    private Label PhotosVid_Labl;

    @FXML
    private Label Registeration_date_labl;

    @FXML
    private Label Tweets_Lbl;
    @FXML
    private Label Name_Lbl;
    @FXML
    private Label Username_Lbl;
    @FXML
    private Label alert_lbl;
    @FXML
    private Circle circle_prof;

    @FXML
    private Label num_of_followers;

    @FXML
    private Label num_of_followings;

    @FXML
    private Label num_of_tweets;

    @FXML
    void EditProfile(ActionEvent event) {
        JavaFXImpl.goToEditProfPage(user, socket, writer, jwt);
    }

    @FXML
    void goToLink(ActionEvent event) {

    }

    @FXML
    void showFollowers(MouseEvent event) {

    }

    @FXML
    void showFollowings(MouseEvent event) {

    }

    @FXML
    void showProfilePhoto(MouseEvent event) {

    }

    @FXML
    void showTweets(MouseEvent event) {

    }

    public void prepareProf() {
        setUsername_Lbl("@" + user.getUsername());
        setName_Lbl(user.getFirstName() + " " + user.getLastName());
        setBio_labl(user.getBio());
        setLocation_Labl(user.getLocation());
        setNum_of_followers(user.getNumOfFollowers());
        setNum_of_followings(user.getNumOfFollowings());
        setCircle_prof("C:\\Users\\TUF GAMING\\Desktop\\Java programing\\project\\AP_Project1402\\images\\popular-social-media-creative-Twitter-logo-png.png");
//      TODO   user.getAvatar()
    }


    public void setBio_labl(String bio_labl) {
        Bio_labl.setText(bio_labl);
    }

//   TODO public void setBirthdate_Labl(Label birthdate_Labl) {
//        Birthdate_Labl = birthdate_Labl;
//    }


//  TODO  public void setLink_hyper(Hyperlink link_hyper) {
//        Link_hyper = link_hyper;
//    }

    public void setLocation_Labl(String location_Labl) {
        Location_Labl.setText(location_Labl);
    }

    public void setRegisteration_date_labl(Date registeration_date_labl) {
        Registeration_date_labl.setText(registeration_date_labl.toString());
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

    public void setNum_of_followers(int numOfFollowers) {
        num_of_followers.setText(String.valueOf(numOfFollowers));
    }

    public void setNum_of_followings(int numOfFollowings) {
        num_of_followings.setText(String.valueOf(numOfFollowings));
    }

    public void addLabel(String msg) {
        alert_lbl.setText(msg);
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
                        alert_lbl.setText("");
                    }
                });
            }
        });
        threadTask.start();


    }

//   TODO public void setNum_of_tweets(Label num_of_tweets) {
//        this.num_of_tweets = num_of_tweets;
//    }
}

