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
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.client.SendMessage;
import model.common.Api;
import model.common.Reply;
import model.common.SocketModel;
import model.common.Tweet;
import model.console_action.ConsoleUtil;
import model.javafx_action.JavaFXImpl;
import org.json.JSONException;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class AddReplyController {

    @FXML
    private Label alert_label;

    @FXML
    private ImageView image;

    @FXML
    private Label name;

    @FXML
    private Circle profile;

    @FXML
    private Button reply_button;

    @FXML
    private AnchorPane return_button;

    @FXML
    private TextArea tweet_text_area;
    @FXML
    private TextArea original_tweet_text_area;
    @FXML
    private Button upload_image_button;

    @FXML
    private Label username;
    private Socket socket;
    private ObjectOutputStream writer;
    private String jwt;
    private String imagePath;
    private Tweet originalTweet;

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
        SendMessage.write(socket, new SocketModel(Api.TYPE_LOADING_TIMELINE, getUsername(), jwt), writer);
    }

    @FXML
    void reply(ActionEvent event) throws IOException {
        if (tweet_text_area.getText().length() > 280) {
            addAlert("The text shouldn't be contained of more than 280 characters.");
        } else {
            Reply reply = null;
            String tweetText = tweet_text_area.getText();
            String newImageFileName = getUsername();
            String newImageFilePath = "AP_Project1402//images//tweet-images//" + newImageFileName + ".png";
            if (image.getImage() != null) {
                File checkFile = new File(newImageFilePath);
                int i = 1;
                while (checkFile.exists()) {
                    newImageFileName = getUsername() + i;
                    newImageFilePath = "AP_Project1402//images//tweet-images//" + newImageFileName + ".png";
                    checkFile = new File(newImageFilePath);
                    i++;
                }
                File newImageFile = new File(newImageFilePath);
                Files.copy(new File(imagePath).toPath(), newImageFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                reply = new Reply(getUsername(), tweet_text_area.getText(), newImageFilePath, originalTweet);
            } else {
                reply = new Reply(getUsername(), tweet_text_area.getText(), null, originalTweet);
            }

            SendMessage.write(socket, new SocketModel(Api.TYPE_REPLY, reply), writer);
        }
    }


    @FXML
    void uploadImage(ActionEvent event) {
        FileChooser fil_chooser = new FileChooser();

        File file = fil_chooser.showOpenDialog((Stage) upload_image_button.getScene().getWindow());

        if (file != null) {
            if (checkSize(file)) {
                imagePath = file.getAbsolutePath();
                image.setImage(new Image(imagePath));
            } else {
                addAlert("The image can't be larger than 1600*900");
            }
        }
    }

    public void start(Tweet tweet) {
        originalTweet = tweet;
        if (originalTweet.getProfile() != null && new File(originalTweet.getProfile()).exists()) {
            File imageFile = new File(originalTweet.getProfile());
            Image image = new Image(imageFile.getAbsolutePath());
            profile.setFill(new ImagePattern(image));
        } else {
            File imageFile = new File("AP_Project1402//images//download2.png");
            Image image = new Image(imageFile.getAbsolutePath());
            profile.setFill(new ImagePattern(image));
        }
        name.setText(tweet.getAuthorName());
        String dateToShow = null;
        Date now = new Date();
        long differenceOFDays = now.getTime() - tweet.getDate().getTime();
        differenceOFDays = TimeUnit.MINUTES.convert(differenceOFDays, TimeUnit.MILLISECONDS);
        if (differenceOFDays < 60) {
            dateToShow = differenceOFDays + "m";
        } else if (differenceOFDays >= 60 && differenceOFDays < 24 * 60) {
            dateToShow = differenceOFDays / 60 + "h";
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(tweet.getDate());
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
            dateToShow = day + " " + monthNames[month];
        }
        username.setText("@" + tweet.getAuthorUsername() + " . " + dateToShow);
        original_tweet_text_area.setText(tweet.getText());
    }

    public String getUsername() {
        if (jwt == null) {
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

    public void addAlert(String alert) {
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

    public boolean checkSize(File imgFile) {
        int pos = imgFile.getName().lastIndexOf(".");
        if (pos == -1)
            addAlert("No extension for file: " + imgFile.getAbsolutePath());
        String suffix = imgFile.getName().substring(pos + 1);
        Iterator<ImageReader> iter = ImageIO.getImageReadersBySuffix(suffix);
        while (iter.hasNext()) {
            ImageReader reader = iter.next();
            try {
                ImageInputStream stream = new FileImageInputStream(imgFile);
                reader.setInput(stream);
                int width = reader.getWidth(reader.getMinIndex());
                int height = reader.getHeight(reader.getMinIndex());
                if (width > 1600 || height > 900) {
                    return false;
                }
            } catch (IOException e) {
                addAlert("Error reading: " + imgFile.getAbsolutePath());
            } finally {
                reader.dispose();
            }
        }
        return true;
    }
}
