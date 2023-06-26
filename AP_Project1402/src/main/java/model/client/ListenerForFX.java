package model.client;

import controller.LogInController;
import controller.TwitterApplication;
import controller.Util;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.common.*;
import model.console_action.ConsoleImpl;
import model.console_action.ConsoleUtil;
import model.javafx_action.JavaFXUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

public class ListenerForFX implements Runnable {

    private Socket socket;
    private final Hashtable<Integer, LinkedList<Client>> listeners = new Hashtable<>();
    private boolean connected;
    private static Client instance;
    private ObjectInputStream reader = null;
    private ObjectOutputStream writer = null;
    private String jwToken;
    private Stage stage ;

//    public static Listener getInstance(Socket socket, ObjectOutputStream writer) throws IOException {
//        if (instance == null) {
//            instance = new Listener(socket);
//            this.writer = writer;
//        }
//        return instance;
//    }

    public ListenerForFX(Socket socket,ObjectInputStream inputStream, ObjectOutputStream outputStream ,Stage stage) throws IOException {
        this.writer = outputStream;
        this.reader = inputStream;
        this.socket = socket;
        this.stage = stage;
        this.jwToken = null;
    }

    public String getJwToken() {
        return jwToken;
    }

//    @Override
    public void run() {
        try {
            while (socket.isConnected()){
                SocketModel model = (SocketModel) reader.readObject();
                switch (model.eventType){
                    case TYPE_SIGNIN :
                        if (model.message == ResponseOrErrorType.SUCCESSFUL){
                            this.jwToken = model.getJwToken();
                            TwitterApplication.firstPage(stage,socket,writer,jwToken);
                        } else {
                            String errorMessg = JavaFXUtil.getErrorMSg(model);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    TwitterApplication.signInPage(stage,socket,writer,jwToken).addLabel(errorMessg);
                                }
                            });
//                            TwitterApplication.signInPage(stage,socket,writer,jwToken);
//                            LogInController.getInstance().addLabel(errorMessg);
                        }
                        break;
                    case TYPE_SIGNUP :
                        if (model.message == ResponseOrErrorType.SUCCESSFUL){
                            this.jwToken = model.getJwToken();
                            TwitterApplication.firstPage(stage,socket,writer,jwToken);
                        } else {
                            String errorMessg = JavaFXUtil.getErrorMSg(model);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
//                                    TwitterApplication.signUpPage(stage,socket,writer,jwToken).addLabel(errorMessg);
                                }
                            });
                        }
                        break;
                    case TYPE_CHANGE_PROF:
                        if (model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleImpl.showProf(socket, model.get(), writer,jwToken);
                            ConsoleImpl.openChatPage(socket, writer,jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            JavaFXUtil.getErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        } else {
                            ConsoleImpl.openChatPage(socket, writer,jwToken);
                        }
                        break;
                    case TYPE_Update_PROF:
                        if (model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleImpl.openChatPage(socket, writer,jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            JavaFXUtil.getErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        } else {
                            JavaFXUtil.getErrorMSg(model);
                        }
                        break;
                    case TYPE_USER_SEARCH:
                        if (model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleImpl.showSearchWords(model.get());
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            JavaFXUtil.getErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        }
                        break;
                    case TYPE_WRITING_TWEET:
                        if(model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleUtil.printTweetAddedMessage();
                            ConsoleImpl.openChatPage(socket,writer,jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            JavaFXUtil.getErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        } else {
                            JavaFXUtil.getErrorMSg(model);
                            ConsoleImpl.openChatPage(socket,writer,jwToken);
                        }
                        break;
                    case TYPE_LOADING_TIMELINE:
                        if(model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleImpl.timeLinePage(socket,writer,(ArrayList<Tweet>) model.data,jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            JavaFXUtil.getErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        }else {
                            JavaFXUtil.getErrorMSg(model);
                            ConsoleImpl.openChatPage(socket,writer,jwToken);
                        }
                        break;
                    case TYPE_UNLIKE:
                        if(model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleUtil.printTweetUnlikedMessage();
                            ConsoleImpl.tweetPage(socket,writer,(Tweet) model.get(),jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            JavaFXUtil.getErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        }else {
                            JavaFXUtil.getErrorMSg(model);
                            ConsoleImpl.tweetPage(socket,writer,(Tweet) model.get(),jwToken);
                        }
                        break;
                    case TYPE_LIKE:
                        if(model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleUtil.printTweetLikedMessage();
                            ConsoleImpl.tweetPage(socket,writer,(Tweet) model.get(),jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            JavaFXUtil.getErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        }else {
                            JavaFXUtil.getErrorMSg(model);
                            ConsoleImpl.tweetPage(socket,writer,(Tweet) model.get(),jwToken);
                        }
                        break;
                    case TYPE_UNDO_RETWEET:
                        if(model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleUtil.printTweetUnRetweetedMessage();
                            ConsoleImpl.tweetPage(socket,writer,(Tweet) model.get(),jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            JavaFXUtil.getErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        }else {
                            JavaFXUtil.getErrorMSg(model);
                            ConsoleImpl.tweetPage(socket,writer,(Tweet) model.get(),jwToken);
                        }
                        break;
                    case TYPE_RETWEET:
                        if(model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleUtil.printTweetRetweetedMessage();
                            ConsoleImpl.tweetPage(socket,writer,(Tweet) model.get(),jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            JavaFXUtil.getErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        }else {
                            JavaFXUtil.getErrorMSg(model);
                            ConsoleImpl.tweetPage(socket,writer,(Tweet) model.get(),jwToken);
                        }
                        break;
                    case TYPE_QUOTE_TWEET:
                        if(model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleUtil.printTweetQuotedMessage();
                            ConsoleImpl.tweetPage(socket,writer,(Tweet) model.get(),jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            JavaFXUtil.getErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        }else {
                            JavaFXUtil.getErrorMSg(model);
                            ConsoleImpl.tweetPage(socket,writer,(Tweet) model.get(),jwToken);
                        }
                        break;
                    case TYPE_REPLY:
                        if(model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleUtil.printReplyAddedMessage();
                            ConsoleImpl.tweetPage(socket,writer,(Tweet) model.get(),jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            JavaFXUtil.getErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        }else {
                            JavaFXUtil.getErrorMSg(model);
                            ConsoleImpl.tweetPage(socket,writer,(Tweet) model.get(),jwToken);
                        }
                        break;
                    case TYPE_SHOW_OTHERS_PROFILE:
                        if(model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleImpl.showOthersProf(socket,(User) model.get(),writer,jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            JavaFXUtil.getErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        }else {
                            JavaFXUtil.getErrorMSg(model);
                            ConsoleImpl.requestTimeLine(socket,writer,jwToken);
                        }
                        break;
                    case TYPE_FOLLOW:
                        if(model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleUtil.printFollowMessage();
                            ConsoleImpl.showOthersProf(socket,(User) model.get(),writer,jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            JavaFXUtil.getErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        }else {
                            JavaFXUtil.getErrorMSg(model);
                            ConsoleImpl.showOthersProf(socket,(User) model.get(),writer,jwToken);
                        }
                        break;
                    case TYPE_UNFOLLOW:
                        if(model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleUtil.printUnFollowMessage();
                            ConsoleImpl.showOthersProf(socket,(User) model.get(),writer,jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            JavaFXUtil.getErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        }else {
                            JavaFXUtil.getErrorMSg(model);
                            ConsoleImpl.showOthersProf(socket,(User) model.get(),writer,jwToken);
                        }
                        break;
                    case TYPE_BLOCK:
                        if(model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleUtil.printBlockMessage();
                            ConsoleImpl.showOthersProf(socket,(User) model.get(),writer,jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            JavaFXUtil.getErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        }else {
                            JavaFXUtil.getErrorMSg(model);
                            ConsoleImpl.showOthersProf(socket,(User) model.get(),writer,jwToken);
                        }
                        break;
                    case TYPE_UNBLOCK:
                        if(model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleUtil.printUnBlockMessage();
                            ConsoleImpl.showOthersProf(socket,(User) model.get(),writer,jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            JavaFXUtil.getErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        }else {
                            JavaFXUtil.getErrorMSg(model);
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

