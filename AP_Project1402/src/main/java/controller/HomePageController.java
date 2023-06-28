package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.javafx_action.JavaFXImpl;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    @FXML
    private Button add_tweet_button;

    @FXML
    private Circle profile_circle;

    @FXML
    private Button search_button;

    @FXML
    private ScrollPane timeline_scroll_pane;

    @FXML
    private VBox timeline_vbox;

    @FXML
    private Label username_label;
    @FXML
    private Button reload_button;
    private Socket socket;
    private ObjectOutputStream writer;
    private String jwt;
    private String username;
    private static HomePageController homePageController;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setWriter(ObjectOutputStream writer) {
        this.writer = writer;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public void setHomePageController(HomePageController homePageController) {
        this.homePageController = homePageController;
    }

    public static HomePageController getInstance() {
        return homePageController;
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
    public void goToAddTweet(ActionEvent event) {
        TwitterApplication.addTweet((Stage) add_tweet_button.getScene().getWindow(),socket,writer,jwt, username);
    }

    @FXML
    public void goToProfile(ActionEvent event) {
        JavaFXImpl.seeThisUserProf(username, socket, writer, jwt);
    }

    @FXML
    public void goToSearch(ActionEvent event) {

    }
    @FXML
    public void reload(ActionEvent event){
        TwitterApplication.homePage((Stage) username_label.getScene().getWindow(),socket,writer,jwt, username);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        Image image = new Image();
//        profile_circle.setFill(new ImagePattern(image));
    }
}
