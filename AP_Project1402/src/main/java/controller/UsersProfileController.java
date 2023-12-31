package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.client.SendMessage;
import model.common.Api;
import model.common.SocketModel;
import model.common.User;
import model.common.Validate;
import model.database.SQLConnection;
import model.javafx_action.JavaFXImpl;
import model.server.PagesToBeShownToUser;


import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;

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
    private ImageView header_imgView;

    @FXML
    private Label num_of_tweets;

    @FXML
    private VBox showingAnchor_pane;

    @FXML
    private Label resultLbl;

    @FXML
    void EditProfile(ActionEvent event) {
        JavaFXImpl.goToEditProfPage(user, socket, writer, jwt);
    }

    @FXML
    void goToLink(ActionEvent event) {

    }

    @FXML
    void showFollowers(MouseEvent event) throws SQLException, ClassNotFoundException {
        showingAnchor_pane.getChildren().clear();
        SQLConnection.getInstance().connect();
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
                uis.setBackPage("Prof");
                uis.setData(thatUser);
                showingAnchor_pane.getChildren().add(hBox);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @FXML
    void showFollowings(MouseEvent event) throws SQLException, ClassNotFoundException {
        showingAnchor_pane.getChildren().clear();
        SQLConnection.getInstance().connect();
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
                uis.setBackPage("Prof");
                uis.setData(thatUser);
                showingAnchor_pane.getChildren().add(hBox);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void makeCircleProf(String username) {
        try {
            User otherUser = Util.getUserFromDB(username, true);
            Label userLabel = new Label();
            userLabel.setText("@" + username + "\n" + otherUser.getFirstName() + " " + otherUser.getLastName());
            Circle circle = new Circle();
            Image image = null;
            if (otherUser.getAvatar() == null || otherUser.getAvatar().equals("") || !new File(otherUser.getAvatar()).exists()){
                File imageFile = new File("AP_Project1402//images//download2.png");
                image = new Image(imageFile.getAbsolutePath());
//            image = new Image("images\\download2.png");
            }else {
                File imageFile = new File(otherUser.getAvatar());
                image = new Image(imageFile.getAbsolutePath());
            }
            circle.setFill(new ImagePattern(image));
            HBox hBox = new HBox();
            hBox.getChildren().addAll(circle, userLabel);
            showingAnchor_pane.getChildren().addAll(hBox);
        } catch (Exception e){
            e.printStackTrace();
        }

    }



    @FXML
    void showTweets(MouseEvent event) {

    }
    @FXML
    void GoBack(ActionEvent event) {
        SendMessage.write(socket, new SocketModel(Api.TYPE_LOADING_TIMELINE,user.getUsername(),jwt), writer);

    }

    public void prepareProf() {
        setUsername_Lbl("@" + user.getUsername());
        setName_Lbl(user.getFirstName() + " " + user.getLastName());
        setBio_labl(user.getBio());
        setLocation_Labl(user.getLocation());
        setBirthdate_Labl(user.getBirthDate());
        setRegisteration_date_labl(user.getSignUpDate());
        setNum_of_followers(user.getNumOfFollowers());
        setNum_of_followings(user.getNumOfFollowings());
        setCircle_prof(user.getAvatar());
        setHeader_img(user.getHeader());
        setLink_hyper(user.getWebsite());
    }


    public void setBio_labl(String bio_labl) {
        if (Validate.NotBlank(bio_labl) && !bio_labl.equals("null")){
            Bio_labl.setText(bio_labl);
        }
    }

   public void setBirthdate_Labl(Date birthdate) {
        if (user.isToShowBirthInProf())
            Birthdate_Labl.setText(birthdate.toString());
    }


  public void setLink_hyper(String link_hyper) {
        Link_hyper.setText(link_hyper);
    }
    public void setHeader_img(String img) {
        if (img!=null && !img.equals("") && new File(img).exists()){
            File imagefile = new File(img);
            Image image = new Image(imagefile.getAbsolutePath());
            header_imgView.setImage(image);
        }

    }
    public void setLocation_Labl(String location_Labl) {
        if (location_Labl != null)
            Location_Labl.setText(location_Labl);
    }

    public void setRegisteration_date_labl(Date registeration_date_labl) {
        if (user.isToShowRegInProf())
            Registeration_date_labl.setText(registeration_date_labl.toString());
    }


    public void setName_Lbl(String name_Lbl) {
        Name_Lbl.setText(name_Lbl);
    }

    public void setUsername_Lbl(String  username_Lbl) {
        Username_Lbl.setText(username_Lbl);
    }

    public void setCircle_prof(String profUrl) {
        try {
            Image prof = null;
            System.out.println(profUrl);
            if (profUrl != null && !profUrl.isBlank() && new File(profUrl).exists()){
                File imagefile = new File(profUrl);
                prof = new Image(imagefile.getAbsolutePath());
                circle_prof.setFill(new ImagePattern(prof));
            }else{
                File imagefile = new File("AP_Project1402//images//download2.png");
//            File imagefile = new File("images\\download2.png");
                prof = new Image(imagefile.getAbsolutePath());
                circle_prof.setFill(new ImagePattern(prof));
            }
        } catch (Exception e){
            e.printStackTrace();
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

