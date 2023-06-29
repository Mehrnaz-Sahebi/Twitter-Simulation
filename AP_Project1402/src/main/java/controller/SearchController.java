package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.common.User;
import model.javafx_action.JavaFXImpl;

import java.io.File;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    private Socket socket;
    private ObjectOutputStream writer;
    private String jwt;
    private String username;
    private SearchController searchController;

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setWriter(ObjectOutputStream writer) {
        this.writer = writer;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public void setUser(String user) {
        this.username = user;
    }

    public void setUserProfController(SearchController controller){
        this.searchController = controller;
    }
    @FXML
    private Label alert_lbl;

    @FXML
    private Circle circle_search;

    @FXML
    private TextField search_txt;

    @FXML
    private VBox showingAnchor_pane;

    @FXML
    void Search(ActionEvent event) {
        String wordToBeSearched = search_txt.getText();
        JavaFXImpl.searchWord(socket, wordToBeSearched, writer);
    }
    @FXML
    void GoBack(ActionEvent event) {
        TwitterApplication.goBackHomePage((Stage)((Node) event.getSource()).getScene().getWindow(), socket, writer, jwt, username);
    }
    public void addLabel(String msg) {
        search_txt.setText(msg);

    }
    public void prepareScene(HashSet<User> foundUsers) {
        if (foundUsers.size() == 0){
            Label label = new Label();
            label.setText("no users exists in this details");
            showingAnchor_pane.getChildren().addAll(label);
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
                            label.setText("");
                        }
                    });
                }
            });
            threadTask.start();
        }else{
            Iterator<User> followersIt = foundUsers.iterator();
            while (followersIt.hasNext()){
                makeCircleProf(followersIt.next());
            }
        }

    }

    private void makeCircleProf(User next) {
        Label userLabel = new Label();
        userLabel.setText("@" + next.getUsername() + "\n" + next.getFirstName() + " " + next.getLastName());
        Circle circle = new Circle();
        Image image = null;
        if (next.getAvatar() == null || next.getAvatar().equals("")){

            File imagefile = new File("images\\download2.png");
            image = new Image(imagefile.toURI().toString());
        }else {
            File imagefile = new File(next.getAvatar());
            image = new Image(imagefile.toURI().toString());
        }
        circle.setFill(new ImagePattern(image));
        HBox hBox = new HBox();
        hBox.getChildren().addAll(circle, userLabel);
        showingAnchor_pane.getChildren().addAll(hBox);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File imagefile = new File("images/download3.png");
        Image image = new Image(imagefile.toURI().toString());//search icon
        circle_search.setFill(new ImagePattern(image));
    }
}
