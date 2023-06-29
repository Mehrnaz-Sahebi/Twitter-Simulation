package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
import model.client.SendMessage;
import model.common.Api;
import model.common.SocketModel;
import model.common.Tweet;
import model.javafx_action.JavaFXImpl;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.ResourceBundle;

public class HomePageController {

    @FXML
    private Button add_tweet_button;

    @FXML
    private Circle profile_circle;

    @FXML
    private Button search_button;
    @FXML
    private Button exitBtn;

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
    private ArrayList<Tweet> timeline;
    private static HomePageController homePageController;

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
    void Exit(ActionEvent event) {
        TwitterApplication.signInPage((Stage)((Node) event.getSource()).getScene().getWindow(), socket, writer, jwt);
    }
    @FXML
    public void goToAddTweet(ActionEvent event) {
        TwitterApplication.addTweet((Stage) add_tweet_button.getScene().getWindow(),socket,writer,jwt);
    }

    @FXML
    public void goToProfile(ActionEvent event) {
        JavaFXImpl.seeThisUserProf(getUsername(), socket, writer, jwt);
    }

    @FXML
    public void goToSearch(ActionEvent event) {
        TwitterApplication.goSearchPage((Stage) ((Node) event.getSource()).getScene().getWindow(), socket, writer, jwt, getUsername());
    }
    @FXML
    public void reload(ActionEvent event){
        SendMessage.write(socket, new SocketModel(Api.TYPE_LOADING_TIMELINE,getUsername(),jwt), writer);

    }
    public void start(ArrayList timeline){
        setTimeline(timeline);
        setProfile();
    }
    public void setTimeline(ArrayList<Tweet> timeline){
        this.timeline=timeline;
        for (Tweet tweet: timeline) {
            timeline_vbox.getChildren().add(new TweetWithoutImageComponent(tweet,getUsername(),socket,writer,jwt));
        }
    }
    public void setProfile(){
        username_label.setText(getUsername());
    }
    public String getUsername(){
        if(jwt ==null){
            return null;
        }
        String[] parts = jwt.split("\\.");
        JSONObject payload = null;
        try {
            payload = new JSONObject(decode(parts[1]));
            return payload.getString("username");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static String decode(String encodedString) {
        return new String(Base64.getUrlDecoder().decode(encodedString));
    }

}
