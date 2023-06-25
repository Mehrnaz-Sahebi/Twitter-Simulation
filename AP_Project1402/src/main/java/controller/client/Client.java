package controller.client;

import controller.LogInController;
import controller.Util;
import javafx.event.ActionEvent;
import model.common.*;
import model.console_action.ConsoleImpl;
import model.console_action.ConsoleUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

public class Client{

    private Socket socket;
    private final Hashtable<Integer, LinkedList<Client>> listeners = new Hashtable<>();
    private boolean connected;
    private static Client instance;
    private ObjectInputStream reader = null;
    private ObjectOutputStream writer = null;
    private String jwToken;
    UserToBeSigned thisUser;

//    public static Listener getInstance(Socket socket, ObjectOutputStream writer) throws IOException {
//        if (instance == null) {
//            instance = new Listener(socket);
//            this.writer = writer;
//        }
//        return instance;
//    }

    public Client(Socket socket) throws IOException {
        this.writer = new ObjectOutputStream(socket.getOutputStream());
        this.reader = new ObjectInputStream(socket.getInputStream());
        this.socket = socket;
        this.jwToken = null;
    }

    public String getJwToken() {
        return jwToken;
    }

//    @Override
    public  void recieveMessageFromServer(ActionEvent event) {
        try {
            while (socket.isConnected()){
                SocketModel model = (SocketModel) reader.readObject();
                switch (model.eventType){
                    case TYPE_SIGNIN :
                    case TYPE_SIGNUP :
                        if (model.message == ResponseOrErrorType.SUCCESSFUL){
                            this.jwToken = model.getJwToken();
//                            ConsoleUtil.printLoginMessage((UserToBeSigned) model.data);
//                            ConsoleImpl.openChatPage(socket, writer,jwToken);
                            Util.changeScene(event,"logged_in.fxml","logged in",null);
                        } else {
                            String errorMessg = ConsoleUtil.getErrorMSg(model);
//                            LogInController.addLabel(errorMessg);
//                            ConsoleImpl.openAccountMenu(socket, writer,jwToken);
                        }
                        break;
                    case TYPE_CHANGE_PROF:
                        if (model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleImpl.showProf(socket, model.get(), writer,jwToken);
                            ConsoleImpl.openChatPage(socket, writer,jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            ConsoleUtil.getErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        } else {
                            ConsoleImpl.openChatPage(socket, writer,jwToken);
                        }
                        break;
                    case TYPE_Update_PROF:
                        if (model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleImpl.openChatPage(socket, writer,jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            ConsoleUtil.getErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        } else {
                            ConsoleUtil.getErrorMSg(model);
                        }
                        break;
                    case TYPE_USER_SEARCH:
                        if (model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleImpl.showSearchWords(model.get());
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            ConsoleUtil.getErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        }
                        break;
                    case TYPE_WRITING_TWEET:
                        if(model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleUtil.printTweetAddedMessage();
                            ConsoleImpl.openChatPage(socket,writer,jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            ConsoleUtil.getErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        } else {
                            ConsoleUtil.getErrorMSg(model);
                            ConsoleImpl.openChatPage(socket,writer,jwToken);
                        }
                        break;
                    case TYPE_LOADING_TIMELINE:
                        if(model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleImpl.timeLinePage(socket,writer,(ArrayList<Tweet>) model.data,jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            ConsoleUtil.getErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        }else {
                            ConsoleUtil.getErrorMSg(model);
                            ConsoleImpl.openChatPage(socket,writer,jwToken);
                        }
                        break;
                    case TYPE_UNLIKE:
                        if(model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleUtil.printTweetUnlikedMessage();
                            ConsoleImpl.tweetPage(socket,writer,(Tweet) model.get(),jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            ConsoleUtil.getErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        }else {
                            ConsoleUtil.getErrorMSg(model);
                            ConsoleImpl.tweetPage(socket,writer,(Tweet) model.get(),jwToken);
                        }
                        break;
                    case TYPE_LIKE:
                        if(model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleUtil.printTweetLikedMessage();
                            ConsoleImpl.tweetPage(socket,writer,(Tweet) model.get(),jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            ConsoleUtil.getErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        }else {
                            ConsoleUtil.getErrorMSg(model);
                            ConsoleImpl.tweetPage(socket,writer,(Tweet) model.get(),jwToken);
                        }
                        break;
                    case TYPE_UNDO_RETWEET:
                        if(model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleUtil.printTweetUnRetweetedMessage();
                            ConsoleImpl.tweetPage(socket,writer,(Tweet) model.get(),jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            ConsoleUtil.getErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        }else {
                            ConsoleUtil.getErrorMSg(model);
                            ConsoleImpl.tweetPage(socket,writer,(Tweet) model.get(),jwToken);
                        }
                        break;
                    case TYPE_RETWEET:
                        if(model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleUtil.printTweetRetweetedMessage();
                            ConsoleImpl.tweetPage(socket,writer,(Tweet) model.get(),jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            ConsoleUtil.getErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        }else {
                            ConsoleUtil.getErrorMSg(model);
                            ConsoleImpl.tweetPage(socket,writer,(Tweet) model.get(),jwToken);
                        }
                        break;
                    case TYPE_QUOTE_TWEET:
                        if(model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleUtil.printTweetQuotedMessage();
                            ConsoleImpl.tweetPage(socket,writer,(Tweet) model.get(),jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            ConsoleUtil.getErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        }else {
                            ConsoleUtil.getErrorMSg(model);
                            ConsoleImpl.tweetPage(socket,writer,(Tweet) model.get(),jwToken);
                        }
                        break;
                    case TYPE_REPLY:
                        if(model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleUtil.printReplyAddedMessage();
                            ConsoleImpl.tweetPage(socket,writer,(Tweet) model.get(),jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            ConsoleUtil.getErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        }else {
                            ConsoleUtil.getErrorMSg(model);
                            ConsoleImpl.tweetPage(socket,writer,(Tweet) model.get(),jwToken);
                        }
                        break;
                    case TYPE_SHOW_OTHERS_PROFILE:
                        if(model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleImpl.showOthersProf(socket,(User) model.get(),writer,jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            ConsoleUtil.getErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        }else {
                            ConsoleUtil.getErrorMSg(model);
                            ConsoleImpl.requestTimeLine(socket,writer,jwToken);
                        }
                        break;
                    case TYPE_FOLLOW:
                        if(model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleUtil.printFollowMessage();
                            ConsoleImpl.showOthersProf(socket,(User) model.get(),writer,jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            ConsoleUtil.getErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        }else {
                            ConsoleUtil.getErrorMSg(model);
                            ConsoleImpl.showOthersProf(socket,(User) model.get(),writer,jwToken);
                        }
                        break;
                    case TYPE_UNFOLLOW:
                        if(model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleUtil.printUnFollowMessage();
                            ConsoleImpl.showOthersProf(socket,(User) model.get(),writer,jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            ConsoleUtil.getErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        }else {
                            ConsoleUtil.getErrorMSg(model);
                            ConsoleImpl.showOthersProf(socket,(User) model.get(),writer,jwToken);
                        }
                        break;
                    case TYPE_BLOCK:
                        if(model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleUtil.printBlockMessage();
                            ConsoleImpl.showOthersProf(socket,(User) model.get(),writer,jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            ConsoleUtil.getErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        }else {
                            ConsoleUtil.getErrorMSg(model);
                            ConsoleImpl.showOthersProf(socket,(User) model.get(),writer,jwToken);
                        }
                        break;
                    case TYPE_UNBLOCK:
                        if(model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleUtil.printUnBlockMessage();
                            ConsoleImpl.showOthersProf(socket,(User) model.get(),writer,jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            ConsoleUtil.getErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        }else {
                            ConsoleUtil.getErrorMSg(model);
                            ConsoleImpl.showOthersProf(socket,(User) model.get(),writer,jwToken);
                        }
                        break;
                }
            }
        } catch (ClassNotFoundException | ParseException | IOException e) {
            throw new RuntimeException(e);
        }

    }
    public synchronized void write(SocketModel model){
        try {
            writer.writeObject(model);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

