package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.common.Tweet;
import model.javafx_action.JavaFXImpl;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

public class AddTweetController {

    @FXML
    private AnchorPane return_button;

    @FXML
    private Button tweet_button;

    @FXML
    private TextArea tweet_text_area;

    @FXML
    private Button upload_image_button;
    @FXML
    private ImageView image;
    @FXML
    private Label alert_label;
    private Socket socket;
    private ObjectOutputStream writer;
    private String jwt;
    private String imagePath;

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setWriter(ObjectOutputStream writer) {
        this.writer = writer;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
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
    void goToHomePage(MouseEvent event) {
        TwitterApplication.homePage((Stage) tweet_button.getScene().getWindow(),socket,writer,jwt);
    }

    @FXML
    void tweet(ActionEvent event) throws URISyntaxException, IOException {
        String tweetText = tweet_text_area.getText();
        String newImageFileName = getUsername();
        String newImageFilePath = ".//.//.//.//images//tweet-images//"+newImageFileName+".png";
        if(image.getImage()!=null){
            File checkFile = new File(newImageFilePath);
            int i = 1;
            while(checkFile.exists()){
                newImageFileName =newImageFileName + i;
                newImageFilePath = ".//.//.//.//images//tweet-images//"+newImageFileName+".png";
                checkFile = new File(newImageFilePath);
                i++;
            }
            File newImageFile = new File(newImageFilePath);
            Files.copy(new File(imagePath).toPath(),newImageFile.toPath(),StandardCopyOption.REPLACE_EXISTING);
        }
        Tweet tweet = new Tweet(getUsername(),tweetText,newImageFilePath);
        JavaFXImpl.addTweet(tweet,socket,writer,jwt);
    }

    @FXML
    void uploadImage(ActionEvent event) {
        FileChooser fil_chooser = new FileChooser();

        File file = fil_chooser.showOpenDialog((Stage) upload_image_button.getScene().getWindow());

        if (file != null) {
            imagePath = file.getAbsolutePath();
        }
        image.setImage(new Image(imagePath));
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
    public void addAlert(String alert){
        alert_label.setText(alert);
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
                        alert_label.setText("");
                    }
                });
            }
        });
        threadTask.start();
    }
}
