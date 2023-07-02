package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.client.SendMessage;
import model.common.*;
import model.database.SQLConnection;
import model.javafx_action.JavaFXImpl;
import model.server.PagesToBeShownToUser;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.ResourceBundle;

public class ProfileOfOthersController implements Initializable {


    private Socket socket;
    private ObjectOutputStream writer;
    private String jwt;
    private User user;
    private String usernameOfThisUser;
    private String backPage;
    private ProfileOfOthersController usersProfileController;

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setBackPage(String backPage) {
        this.backPage = backPage;
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

    public void setUsernameOfThisUser(String usernameOfThisUser) {
        this.usernameOfThisUser = usernameOfThisUser;
    }

    public void setUserProfController(ProfileOfOthersController controller){
        this.usersProfileController = controller;
    }

    @FXML
    private Label Bio_labl;

    @FXML
    private Label Birthdate_Labl;

    @FXML
    private Label Followers_that_you_know_Labl;

    @FXML
    private Hyperlink Link_hyper;

    @FXML
    private Label Location_Labl;

    @FXML
    private Label Name_Lbl;

    @FXML
    private Label PhotosVid_Labl;

    @FXML
    private Label Registeration_date_labl;

    @FXML
    private Label Username_Lbl;

    @FXML
    private Label alert_lbl;

    @FXML
    private Label resultLbl;

    @FXML
    private Circle circle_prof;

    @FXML
    private ImageView header_imgView;

    @FXML
    private Label num_of_followers;

    @FXML
    private Label num_of_followings;

    @FXML
    private Label num_of_tweets;
    @FXML
    private Label msgLbl;

    @FXML
    private TextField sendMsgField;
    @FXML
    private Button sendBtn;

    @FXML
    private VBox showingAnchor_pane;

    @FXML
    private Button un_follow_Btn;

    @FXML
    private Button block_Btn;


    @FXML
    void follow(ActionEvent event) {
        if (block_Btn.getText().equals("UnBlock")){
            resultLbl.setStyle( "-fx-text-fill: red");
            resultLbl.setText("First unblock user");
        }else {
            if (un_follow_Btn.getText().equals("Follow")){
                JavaFXImpl.follow(socket, jwt, writer, user);
            }else if (un_follow_Btn.getText().equals("Following")){
                JavaFXImpl.unfollow(socket, jwt, writer, user);
            }
        }


    }
    @FXML
    void block(ActionEvent event) {
        if (block_Btn.getText().equals("Block")){
            JavaFXImpl.block(socket, jwt, writer, user);
        }else if (block_Btn.getText().equals("UnBlock")){
            JavaFXImpl.unBlock(socket, jwt, writer, user);
        }
    }
    @FXML
    void goToLink(ActionEvent event) {

    }
    @FXML
    void showFollowers(MouseEvent event) {
        showingAnchor_pane.getChildren().clear();
        try {
            SQLConnection.getInstance().connect();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        resultLbl.setText("Followers");
        Iterator<String> followersIt = user.getFollowers().iterator();
        while (followersIt.hasNext()){
            SocketModel socketModel = PagesToBeShownToUser.goToTheUsersProfile(followersIt.next(), true);
//            makeCircleProf(followersIt.next());
            FXMLLoader fxmlLoader = new FXMLLoader(SearchController.class.getResource("userItemHboxSearch.fxml"));
//                fxmlLoader.setLocation(getClass().getResource());


            try {
                User thatUser = (User) socketModel.get();
                HBox hBox = fxmlLoader.load();
                UserItemHboxSearch uis = fxmlLoader.getController();
                uis.setJwt(jwt);
                uis.setSocket(socket);
                uis.setWriter(writer);
                if (thatUser.getFollowers().contains(user.getUsername())){
                    uis.setButtonsTxt("Following");
                }else {
                    uis.setButtonsTxt("Follow");
                }
//                uis.setBackPage("Other");
                uis.setData(thatUser);
                showingAnchor_pane.getChildren().add(hBox);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @FXML
    void showFollowings(MouseEvent event) {
        showingAnchor_pane.getChildren().clear();
        try {
            SQLConnection.getInstance().connect();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        resultLbl.setText("Followings");
        Iterator<String> followersIt = user.getFollowings().iterator();
        while (followersIt.hasNext()){
            SocketModel socketModel = PagesToBeShownToUser.goToTheUsersProfile(followersIt.next(), true);
//            makeCircleProf(followersIt.next());
            FXMLLoader fxmlLoader = new FXMLLoader(SearchController.class.getResource("userItemHboxSearch.fxml"));
//                fxmlLoader.setLocation(getClass().getResource());


            try {
                User thatUser = (User) socketModel.get();
                HBox hBox = fxmlLoader.load();
                UserItemHboxSearch uis = fxmlLoader.getController();
                uis.setJwt(jwt);
                uis.setSocket(socket);
                uis.setWriter(writer);
                if (thatUser.getFollowers().contains(user.getUsername())){
                    uis.setButtonsTxt("Following");
                }else {
                    uis.setButtonsTxt("Follow");
                }
//                uis.setBackPage("Other");
                uis.setData(thatUser);
                showingAnchor_pane.getChildren().add(hBox);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
//    private void makeCircleProf(String username) {
//        User otherUser = Util.getUserFromDB(username, true);
//        Label userLabel = new Label();
//        userLabel.setText("@" + username + "\n" + otherUser.getFirstName() + " " + otherUser.getLastName());
//        Circle circle = new Circle();
//        Image image = null;
//        if (otherUser.getAvatar() == null || otherUser.getAvatar().equals("")){
//            image = new Image("images\\download2.png");
//        }else {
//            image = new Image(otherUser.getAvatar());
//        }
//        circle.setFill(new ImagePattern(image));
//        HBox hBox = new HBox();
//        hBox.getChildren().addAll(circle, userLabel);
//        showingAnchor_pane.getChildren().addAll(hBox);
//    }

//    @FXML
//    void showFollowings(MouseEvent event) {
//
//    }
//
//    @FXML
//    void showProfilePhoto(MouseEvent event) {
//
//    }
//
//    @FXML
//    void showTweets(MouseEvent event) {
//
//    }
    @FXML
    void GoBack(ActionEvent event) {
        if (backPage == null){
            backPage = "Search";
        }
        TwitterApplication.goBack(backPage, (Stage) (((Node) event.getSource()).getScene().getWindow()), socket, writer, jwt, usernameOfThisUser);

    }

    @FXML
    void sendMsg(ActionEvent event) {
        msgLbl.setVisible(true);
        msgLbl.setText("Write Message");
        sendMsgField.setVisible(true);
        sendBtn.setVisible(true);
    }

    @FXML
    void send(ActionEvent event) {
        String msg = sendMsgField.getText();
        Message message = new Message(usernameOfThisUser, msg, user.getUsername(), user.getAvatar());
        JavaFXImpl.sendMsg(socket, jwt, writer, message);
    }

    public void prepareProf() {
        if (user.getFollowers().contains(usernameOfThisUser)){
            un_follow_Btn.setText("Following");
        }else {
            un_follow_Btn.setText("Follow");
        }
        if (user.getBlocker().contains(usernameOfThisUser)){
            block_Btn.setText("UnBlock");
        }else {
            block_Btn.setText("Block");
        }
        setUsername_Lbl("@" + user.getUsername());
        setName_Lbl(user.getFirstName() + " " + user.getLastName());
        setBio_labl(user.getBio());
        setLocation_Labl(user.getLocation());
//        setBirthdate_Labl(user.getBirthDate());
//        setRegisteration_date_labl(user.getSignUpDate());
        setNum_of_followers(user.getNumOfFollowers());
       setNum_of_followings(user.getNumOfFollowings());
        setCircle_prof(user.getAvatar());
        setHeader_img(user.getHeader());
        setLink_hyper(user.getWebsite());
    }


    public void setBio_labl(String bio_labl) {
        Bio_labl.setText(bio_labl);
    }

//    public void setBirthdate_Labl(Date birthdate) {
//        if (user.isToShowBirthInProf())
//            Birthdate_Labl.setText(birthdate.toString());
//    }


    public void setLink_hyper(String link_hyper) {
        Link_hyper.setText(link_hyper);
    }
    public void setHeader_img(String img) {
        if (Validate.NotBlank(img)){
            File imagefile = new File(img);
            Image image = new Image(imagefile.toURI().toString());
            header_imgView.setImage(image);
        }

    }
    public void setLocation_Labl(String location_Labl) {
        if (location_Labl != null)
            Location_Labl.setText(location_Labl);
    }

//    public void setRegisteration_date_labl(Date registeration_date_labl) {
//        if (user.isToShowRegInProf())
//            Registeration_date_labl.setText(registeration_date_labl.toString());
//    }


    public void setName_Lbl(String name_Lbl) {
        Name_Lbl.setText(name_Lbl);
    }

    public void setUsername_Lbl(String  username_Lbl) {
        Username_Lbl.setText(username_Lbl);
    }

    public void setCircle_prof(String profUrl) {
        Image prof = null;
        if (profUrl != null && !profUrl.isBlank()){
            File imagefile = new File(profUrl);
            prof = new Image(imagefile.getAbsolutePath());
            circle_prof.setFill(new ImagePattern(prof));
        }else if (profUrl == null || profUrl.isBlank()){
            File imagefile = new File("AP_Project1402//images//download2.png");
            prof = new Image(imagefile.getAbsolutePath());
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

    public void changeButton(String msg) {//follow button
        un_follow_Btn.setText(msg);
    }
    public void changeBlockButton(String msg) {
        block_Btn.setText(msg);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        msgLbl.setVisible(false);
        sendMsgField.setVisible(false);
        sendBtn.setVisible(false);
    }
}

