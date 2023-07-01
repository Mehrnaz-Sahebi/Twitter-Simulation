package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.common.User;
import model.common.Validate;
import model.javafx_action.JavaFXImpl;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class UserItemHboxSearch {


//    private Socket socket;
//    private ObjectOutputStream writer;
//    private String jwt;
//    private User user;
    private SearchController searchController;

    private User user;
    private String usernameOfThisUser;
    private String buttonsTxt;

    @FXML
    private Button followBtn;

//    public void setSocket(Socket socket) {
//        this.socket = socket;
//    }
//
//    public void setWriter(ObjectOutputStream writer) {
//        this.writer = writer;
//    }
private Socket socket;
    private ObjectOutputStream writer;
    private String jwt;

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setWriter(ObjectOutputStream writer) {
        this.writer = writer;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }


//
//    public void setJwt(String jwt) {
//        this.jwt = jwt;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }

    public void setSearchProfController(SearchController controller){
        this.searchController = controller;
    }
    public void setUsernameOfThisUser(String usernameOfThisUser) {
        this.usernameOfThisUser = usernameOfThisUser;
    }

    public void setButtonsTxt(String buttonsTxt) {
        this.buttonsTxt = buttonsTxt;
    }

    @FXML
    private Label name_Lbl;

    @FXML
    private Label username_Lbl;
    @FXML
    private Circle profCircle;

    @FXML
    void FollowUser(ActionEvent event) {
        fillTheContr();
        if (followBtn.getText().equals("Follow")){
            JavaFXImpl.follow(searchController.getSocket(), searchController.getJwt(), searchController.getWriter(), user);
        }else if (followBtn.getText().equals("Following")){
            JavaFXImpl.unfollow(searchController.getSocket(), searchController.getJwt(), searchController.getWriter(), user);
        }
    }

    private void fillTheContr() {
        if (searchController == null){
            FXMLLoader fxmlLoader = new FXMLLoader(TwitterApplication.class.getResource("SearchPage.fxml"));
            Parent root;
            try {
                root = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            searchController = fxmlLoader.getController();
            searchController.setSocket(socket);
            searchController.setJwt(jwt);
            searchController.setWriter(writer);
            searchController.setSerchController(searchController);
            searchController.setUser(usernameOfThisUser);
        }
    }

    @FXML
    void GoToOtherUsersProf(ActionEvent event) {
        fillTheContr();
        TwitterApplication.profOthersPage((Stage) (((Node) event.getSource()).getScene().getWindow()), searchController.getSocket(), searchController.getWriter(), searchController.getJwt(), user, usernameOfThisUser);
    }
    public  void setData(User user){
        this.user = user;
        if (Validate.NotBlank(user.getAvatar())){
            File img = new File(user.getAvatar());
            Image newImg = new Image(img.toURI().toString());
            profCircle.setFill(new ImagePattern(newImg));
        }else {
            File img = new File("images\\download2.png");
            Image newImg = new Image(img.toURI().toString());
            profCircle.setFill(new ImagePattern(newImg));
        }


        name_Lbl.setText(user.getFirstName() + " " + user.getLastName());
        username_Lbl.setText(user.getUsername());
        followBtn.setText(buttonsTxt);
    }
}