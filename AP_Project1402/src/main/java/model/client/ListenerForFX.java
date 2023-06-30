package model.client;

import controller.HomePageController;
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
import model.javafx_action.JavaFXImpl;
import model.javafx_action.JavaFXUtil;
import model.common.Api;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.ParseException;
import java.util.*;

import static model.console_action.ConsoleImpl.getUsername;

public class ListenerForFX implements Runnable {

    private Socket socket;
    private final Hashtable<Integer, LinkedList<Client>> listeners = new Hashtable<>();
    private static Client instance;
    private ObjectInputStream reader = null;
    private ObjectOutputStream writer = null;
    private String jwToken;
    private Stage stage;
    private String username;
    private HomePageController controller;

//    public static Listener getInstance(Socket socket, ObjectOutputStream writer) throws IOException {
//        if (instance == null) {
//            instance = new Listener(socket);
//            this.writer = writer;
//        }
//        return instance;
//    }

    public ListenerForFX(Socket socket, ObjectInputStream inputStream, ObjectOutputStream outputStream, Stage stage) throws IOException {
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
            while (socket.isConnected()) {
                SocketModel model = (SocketModel) reader.readObject();
                switch (model.eventType) {
                    case TYPE_SIGNIN:
                        if (model.message == ResponseOrErrorType.SUCCESSFUL) {
                            this.jwToken = model.getJwToken();
                            SendMessage.write(socket, new SocketModel(Api.TYPE_LOADING_TIMELINE,getUsername(),jwToken), writer);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            String errorMessg = JavaFXUtil.getErrorMSg(model);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    TwitterApplication.welcomePage(stage, socket, writer, jwToken).addAlert(errorMessg);
                                }
                            });

                        } else {
                            String errorMessg = JavaFXUtil.getErrorMSg(model);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    TwitterApplication.signInPage(stage, socket, writer, jwToken).addLabel(errorMessg);
                                }
                            });
                        }
                        break;
                    case TYPE_SIGNUP:
                        if (model.message == ResponseOrErrorType.SUCCESSFUL) {
                            this.jwToken = model.getJwToken();
                            SendMessage.write(socket, new SocketModel(Api.TYPE_LOADING_TIMELINE,getUsername(),jwToken), writer);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            String errorMessg = JavaFXUtil.getErrorMSg(model);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    TwitterApplication.welcomePage(stage, socket, writer, jwToken).addAlert(errorMessg);
                                }
                            });
                        } else {
                            String errorMessg = JavaFXUtil.getErrorMSg(model);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    TwitterApplication.signUpPage(stage, socket, writer, jwToken).addLabel(errorMessg);
                                }
                            });
                        }
                        break;
                    case TYPE_SEE_PROF:
                        if (model.message == ResponseOrErrorType.SUCCESSFUL) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    TwitterApplication.profPage(stage, socket, writer, jwToken, (User) model.data);
                                }
                            });
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            String errorMessg = JavaFXUtil.getErrorMSg(model);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    TwitterApplication.welcomePage(stage, socket, writer, jwToken).addAlert(errorMessg);
                                }
                            });
                        } else {
                            //TODO add alert
                            SendMessage.write(socket, new SocketModel(Api.TYPE_LOADING_TIMELINE,getUsername(),jwToken), writer);
                        }
                        break;
                    case TYPE_GO_TO_EDIT_PROF:
                        if (model.message == ResponseOrErrorType.SUCCESSFUL) {
                            username = ((User)model.data).getUsername();
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    TwitterApplication.editProfPage(stage, socket, writer, jwToken, (User) model.data);
                                }
                            });

                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            String errorMessg = JavaFXUtil.getErrorMSg(model);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    TwitterApplication.welcomePage(stage, socket, writer, jwToken).addAlert(errorMessg);
                                }
                            });
                        } else {
                            String msg = JavaFXUtil.getErrorMSg(model);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    TwitterApplication.profPage(stage, socket, writer, jwToken, (User) model.data).addLabel(msg);
                                }
                            });

                        }
                        break;
                    case TYPE_Update_PROF:
                        if (model.message == ResponseOrErrorType.SUCCESSFUL) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    TwitterApplication.profPage(stage, socket, writer, jwToken, (User) model.data);
                                }
                            });
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            String errorMessg = JavaFXUtil.getErrorMSg(model);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    TwitterApplication.welcomePage(stage, socket, writer, jwToken).addAlert(errorMessg);
                                }
                            });
                        } else {
                            String msg = JavaFXUtil.getErrorMSg(model);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    TwitterApplication.editProfPage(stage, socket, writer, jwToken, (User) model.data).addLabel(msg);
                                }
                            });

                        }
                        break;
                            case TYPE_USER_SEARCH:
                                if (model.message == ResponseOrErrorType.SUCCESSFUL) {

                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            TwitterApplication.goSearchPage(stage, socket, writer, jwToken, getUsername()).prepareScene(model.get());
                                        }
                                    });
                                } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                                    String errorMessg = JavaFXUtil.getErrorMSg(model);
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            TwitterApplication.welcomePage(stage, socket, writer, jwToken).addAlert(errorMessg);
                                        }
                                    });
                                }
                                break;
                            case TYPE_WRITING_TWEET:
                                if (model.message == ResponseOrErrorType.SUCCESSFUL) {
                                    SendMessage.write(socket, new SocketModel(Api.TYPE_LOADING_TIMELINE,getUsername(),jwToken), writer);

                                } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                                    String errorMessg = JavaFXUtil.getErrorMSg(model);
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            TwitterApplication.welcomePage(stage, socket, writer, jwToken).addAlert(errorMessg);
                                        }
                                    });
                                } else {

                                    String errorMessg = JavaFXUtil.getErrorMSg(model);
                                    System.out.println(errorMessg);
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            TwitterApplication.addTweet(stage, socket, writer, jwToken).addAlert(errorMessg);
                                        }
                                    });
                                }
                                break;
                            case TYPE_LOADING_TIMELINE:
                                if (model.message == ResponseOrErrorType.SUCCESSFUL) {
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            controller = TwitterApplication.homePage(stage, socket, writer, jwToken,(ArrayList<Tweet>) model.data);
                                        }
                                    });
                                } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                                    String errorMessg = JavaFXUtil.getErrorMSg(model);
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            TwitterApplication.welcomePage(stage, socket, writer, jwToken).addAlert(errorMessg);
                                        }
                                    });
                                } else {
                                    //TODO add alert
                                    String errorMessg = JavaFXUtil.getErrorMSg(model);
                                    System.out.println(errorMessg);
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            controller = TwitterApplication.homePage(stage, socket, writer, jwToken,(ArrayList<Tweet>) model.data);
                                        }
                                    });
                                }
                                break;
                            case TYPE_UNLIKE:
                                if (model.message == ResponseOrErrorType.SUCCESSFUL) {
                                    System.out.println("unlike");
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            controller.replaceTweet((Tweet) model.data);
                                        }
                                    });
                                } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                                    String errorMessg = JavaFXUtil.getErrorMSg(model);
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            TwitterApplication.welcomePage(stage, socket, writer, jwToken).addAlert(errorMessg);
                                        }
                                    });
                                } else {
                                    //TODO add alert
                                }
                                break;
                            case TYPE_LIKE:
                                if (model.message == ResponseOrErrorType.SUCCESSFUL) {
                                    System.out.println("like");
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            controller.replaceTweet((Tweet) model.data);
                                        }
                                    });
                                } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                                    String errorMessg = JavaFXUtil.getErrorMSg(model);
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            TwitterApplication.welcomePage(stage, socket, writer, jwToken).addAlert(errorMessg);
                                        }
                                    });
                                } else {
                                    //TODO add alert
                                }
                                break;
                            case TYPE_UNDO_RETWEET:
                                if (model.message == ResponseOrErrorType.SUCCESSFUL) {
                                    System.out.println("undo");
                                    SendMessage.write(socket, new SocketModel(Api.TYPE_LOADING_TIMELINE,getUsername(),jwToken), writer);

                                } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                                    String errorMessg = JavaFXUtil.getErrorMSg(model);
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            TwitterApplication.welcomePage(stage, socket, writer, jwToken).addAlert(errorMessg);
                                        }
                                    });
                                } else {
                                    //TODO add alert
                                }
                                break;
                            case TYPE_RETWEET:
                                if (model.message == ResponseOrErrorType.SUCCESSFUL) {
                                    System.out.println("retweet");
                                    SendMessage.write(socket, new SocketModel(Api.TYPE_LOADING_TIMELINE,getUsername(),jwToken), writer);

                                } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                                    String errorMessg = JavaFXUtil.getErrorMSg(model);
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            TwitterApplication.welcomePage(stage, socket, writer, jwToken).addAlert(errorMessg);
                                        }
                                    });
                                } else {
                                    //TODO add alert
                                }
                                break;
                            case TYPE_QUOTE_TWEET:
                                if (model.message == ResponseOrErrorType.SUCCESSFUL) {
                                    ConsoleUtil.printTweetQuotedMessage();
                                    ConsoleImpl.tweetPage(socket, writer, (Tweet) model.get(), jwToken);
                                } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                                    JavaFXUtil.getErrorMSg(model);
                                    ConsoleImpl.openAccountMenu(socket, writer, jwToken);
                                } else {
                                    JavaFXUtil.getErrorMSg(model);
                                    ConsoleImpl.tweetPage(socket, writer, (Tweet) model.get(), jwToken);
                                }
                                break;
                            case TYPE_REPLY:
                                if (model.message == ResponseOrErrorType.SUCCESSFUL) {
                                    ConsoleUtil.printReplyAddedMessage();
                                    ConsoleImpl.tweetPage(socket, writer, (Tweet) model.get(), jwToken);
                                } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                                    JavaFXUtil.getErrorMSg(model);
                                    ConsoleImpl.openAccountMenu(socket, writer, jwToken);
                                } else {
                                    JavaFXUtil.getErrorMSg(model);
                                    ConsoleImpl.tweetPage(socket, writer, (Tweet) model.get(), jwToken);
                                }
                                break;
                            case TYPE_SHOW_OTHERS_PROFILE:
                                if (model.message == ResponseOrErrorType.SUCCESSFUL) {
                                    ConsoleImpl.showOthersProf(socket, (User) model.get(), writer, jwToken);
                                } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                                    JavaFXUtil.getErrorMSg(model);
                                    ConsoleImpl.openAccountMenu(socket, writer, jwToken);
                                } else {
                                    JavaFXUtil.getErrorMSg(model);
                                    ConsoleImpl.requestTimeLine(socket, writer, jwToken);
                                }
                                break;
                            case TYPE_FOLLOW:
                                if (model.message == ResponseOrErrorType.SUCCESSFUL) {
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            TwitterApplication.profOthersPage(stage, socket, writer, jwToken, (User) model.data, getUsername()).changeButton("Following");
                                        }
                                    });

                                } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                                    String errorMessg = JavaFXUtil.getErrorMSg(model);
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            TwitterApplication.welcomePage(stage, socket, writer, jwToken).addAlert(errorMessg);
                                        }
                                    });
                                } else {
                                    String errorMessg = JavaFXUtil.getErrorMSg(model);
                                    TwitterApplication.welcomePage(stage, socket, writer, jwToken).addAlert(errorMessg);
                                }
                                break;
                            case TYPE_UNFOLLOW:
                                if (model.message == ResponseOrErrorType.SUCCESSFUL) {
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            TwitterApplication.profOthersPage(stage, socket, writer, jwToken, (User) model.data, getUsername()).changeButton("Follow");
                                        }
                                    });
                                } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                                    String errorMessg = JavaFXUtil.getErrorMSg(model);
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            TwitterApplication.welcomePage(stage, socket, writer, jwToken).addAlert(errorMessg);
                                        }
                                    });
                                } else {
                                    String errorMessg = JavaFXUtil.getErrorMSg(model);
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            TwitterApplication.welcomePage(stage, socket, writer, jwToken).addAlert(errorMessg);
                                        }
                                    });
                                }
                                break;
                            case TYPE_BLOCK:
                                if (model.message == ResponseOrErrorType.SUCCESSFUL) {
                                    ConsoleUtil.printBlockMessage();
                                    ConsoleImpl.showOthersProf(socket, (User) model.get(), writer, jwToken);
                                } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                                    JavaFXUtil.getErrorMSg(model);
                                    ConsoleImpl.openAccountMenu(socket, writer, jwToken);
                                } else {
                                    JavaFXUtil.getErrorMSg(model);
                                    ConsoleImpl.showOthersProf(socket, (User) model.get(), writer, jwToken);
                                }
                                break;
                            case TYPE_UNBLOCK:
                                if (model.message == ResponseOrErrorType.SUCCESSFUL) {
                                    ConsoleUtil.printUnBlockMessage();
                                    ConsoleImpl.showOthersProf(socket, (User) model.get(), writer, jwToken);
                                } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                                    JavaFXUtil.getErrorMSg(model);
                                    ConsoleImpl.openAccountMenu(socket, writer, jwToken);
                                } else {
                                    JavaFXUtil.getErrorMSg(model);
                                    ConsoleImpl.showOthersProf(socket, (User) model.get(), writer, jwToken);
                                }
                                break;
                        }
                }
            }catch(ClassNotFoundException | ParseException | IOException e){
            throw new RuntimeException(e);
        }

    }
    public String getUsername(){
        if(jwToken ==null){
            return null;
        }
        String[] parts = jwToken.split("\\.");
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

        public synchronized void write (SocketModel model){
            try {
                writer.writeObject(model);
                writer.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
}


