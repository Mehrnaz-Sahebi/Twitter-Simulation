package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.common.User;
import model.common.Validate;

import java.io.File;
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

//    public void setSocket(Socket socket) {
//        this.socket = socket;
//    }
//
//    public void setWriter(ObjectOutputStream writer) {
//        this.writer = writer;
//    }
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

    @FXML
    private Label name_Lbl;

    @FXML
    private Label username_Lbl;
    @FXML
    private Circle profCircle;

    @FXML
    void FollowUser(ActionEvent event) {

    }

    @FXML
    void GoToOtherUsersProf(ActionEvent event) {
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
    }
}