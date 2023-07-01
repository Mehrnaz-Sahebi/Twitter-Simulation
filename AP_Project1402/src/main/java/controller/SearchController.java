package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.client.SendMessage;
import model.common.*;
import model.common.Api;
import model.common.SocketModel;
import model.common.User;
import model.javafx_action.JavaFXImpl;

import java.io.File;
import java.io.IOException;
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

    public Socket getSocket() {
        return socket;
    }

    public ObjectOutputStream getWriter() {
        return writer;
    }

    public String getJwt() {
        return jwt;
    }

    public void setSerchController(SearchController controller){
        this.searchController = controller;
    }
    @FXML
    private Label alert_lbl;

    @FXML
    private Circle circle_search;

//    @FXML
//    private TableView<User> searchTableview;
    ObservableList<User> data = FXCollections.observableArrayList();

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
        SendMessage.write(socket, new SocketModel(Api.TYPE_LOADING_TIMELINE,username,jwt), writer);
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
//            TableColumn<User, String> username = new TableColumn<>("Username");
//            TableColumn<User, String> fname = new TableColumn<>("First Name");
//            TableColumn<User, String> lname = new TableColumn<>("Last Name");
//            TableColumn<User, String> follow = new TableColumn<>("follow");
//            username.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
//            fname.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
//            lname.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));
//
//            Callback<TableColumn<User, String>, TableCell<User, String>> cellFactory = (param) -> {
//                 final TableCell<User, String> cell = new TableCell<User, String>(){
//                    @Override
//                    public void updateItem(String item, boolean empty) {
//                        super.updateItem(item, empty);
//
////                        if (empty){
////                            setGraphic(null);
////                            setText(null);
////                        } else {
//                        final Button follow = new Button("Follow");
//
//                        follow.setOnAction(event -> {
//                            User user = getTableView().getItems().get(getIndex());
//                            //TODO go users page
//
//                        });
//
//                        setGraphic(follow);
//                        setText(null);
////                    }
//                    }
//                };
//
//
//
//                return cell;
//
//            };
//
//            follow.setCellFactory(cellFactory);
//
////        TableColumn username = new TableColumn<>("Username");
////        TableColumn username = new TableColumn<>("Username");
//            searchTableview.getColumns().addAll(username, fname, lname);
            Iterator<User> followersIt = foundUsers.iterator();
            while (followersIt.hasNext()){
//                data.add(followersIt.next());
//                makeCircleProf(followersIt.next());
                FXMLLoader fxmlLoader = new FXMLLoader(SearchController.class.getResource("userItemHboxSearch.fxml"));
//                fxmlLoader.setLocation(getClass().getResource());


                try {
                    User thatUser = followersIt.next();
                    if (!thatUser.getUsername().equals(username)){

                        HBox hBox = fxmlLoader.load();
                        UserItemHboxSearch uis = fxmlLoader.getController();
                        uis.setSearchProfController(searchController);
                        uis.setUsernameOfThisUser(username);
                        if (thatUser.getFollowers().contains(username)){
                            uis.setButtonsTxt("Following");
                        }else {
                            uis.setButtonsTxt("Follow");
                        }
                        uis.setBackPage("Search");
                        uis.setData(thatUser);
                        showingAnchor_pane.getChildren().add(hBox);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


//            searchTableview.setItems(data);
        }

    }

    private void makeCircleProf(User next) {
//        Label userLabel = new Label();
//        userLabel.setText("@" + next.getUsername() + "\n" + next.getFirstName() + " " + next.getLastName());
//        Circle circle = new Circle();
//        Image image = null;
//        if (next.getAvatar() == null || next.getAvatar().equals("")){
//
//            File imagefile = new File("images\\download2.png");
//            image = new Image(imagefile.toURI().toString());
//        }else {
//            File imagefile = new File(next.getAvatar());
//            image = new Image(imagefile.toURI().toString());
//        }
//        circle.setFill(new ImagePattern(image));
//        HBox hBox = new HBox();
//        hBox.getChildren().addAll(circle, userLabel);
//        showingAnchor_pane.getChildren().addAll(hBox);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File imagefile = new File("AP_Project1402//images//download3.png");
        Image image = new Image(imagefile.getAbsolutePath());
        circle_search.setFill(new ImagePattern(image));

    }
}
