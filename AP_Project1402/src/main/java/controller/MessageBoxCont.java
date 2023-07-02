package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.client.SendMessage;
import model.common.Api;
import model.common.Message;
import model.common.SocketModel;
import model.common.User;
import model.javafx_action.JavaFXImpl;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.Iterator;

public class MessageBoxCont {
    private Socket socket;
    private ObjectOutputStream writer;
    private String jwt;
    private String username;
    private MessageBoxCont searchController;

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

    public void setSerchController(MessageBoxCont controller){
        this.searchController = controller;
    }

    @FXML
    private VBox showingAnchor_pane;

    @FXML
    void GoBack(ActionEvent event) {
        SendMessage.write(socket, new SocketModel(Api.TYPE_LOADING_TIMELINE,username,jwt), writer);

    }

//    public void prepare(){
//        JavaFXImpl.getMsg(socket,jwt,writer, username);
//    }

    public void prepareScene(HashSet<Message> messageInfos){
        if (messageInfos.isEmpty()){
            System.out.println("Nothing exists");
        }else {
            Iterator<Message> followersIt = messageInfos.iterator();
            while (followersIt.hasNext()){
//                data.add(followersIt.next());
//                makeCircleProf(followersIt.next());
                FXMLLoader fxmlLoader = new FXMLLoader(SearchController.class.getResource("MessageItem.fxml"));
//                fxmlLoader.setLocation(getClass().getResource());


                try {
                    Message message = followersIt.next();

                        HBox hBox = fxmlLoader.load();
                        MessageItem uis = fxmlLoader.getController();
//                        uis.setSearchProfController(searchController);
                        uis.setUsernameOfThisUser(message.getSenderUsername());
//                        if (thatUser.getFollowers().contains(username)){
//                            uis.setButtonsTxt("Following");
//                        }else {
//                            uis.setButtonsTxt("Follow");
//                        }
//                       TODO uis.setBackPage("");
                        uis.prepare(message);
                        showingAnchor_pane.getChildren().add(hBox);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
