import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Hashtable;
import java.util.LinkedList;

public class Listener implements Runnable{

    private Socket socket;
    private final Hashtable<Integer, LinkedList<Listener>> listeners = new Hashtable<>();
    private boolean connected;
    private static Listener instance;
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

    public Listener(Socket socket, ObjectInputStream reader, ObjectOutputStream writer) throws IOException {
        this.socket = socket;
        this.reader = reader;
        this.writer = writer;
        this.jwToken = null;
    }

    @Override
    public synchronized void run() {
        try {
            while (socket.isConnected()){
                SocketModel model = (SocketModel) reader.readObject();
                switch (model.eventType){
                    case TYPE_SIGNIN :
                    case TYPE_SIGNUP :
                        if (model.message == ResponseOrErrorType.SUCCESSFUL){
                            this.jwToken = model.getJwToken();
                            ConsoleUtil.printLoginMessage((UserToBeSigned) model.data);
                            ConsoleImpl.openChatPage(socket, writer,jwToken);
                        } else {
                            ConsoleUtil.printErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket, writer,jwToken);
                        }
                        break;
                    case TYPE_CHANGE_PROF:
                        if (model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleImpl.showProf(socket, model.get(), writer,jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            ConsoleUtil.printErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        } else {
                            ConsoleImpl.openChatPage(socket, writer,jwToken);
                        }
                        break;
                    case TYPE_Update_PROF:
                        if (model.message == ResponseOrErrorType.SUCCESSFUL){

                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            ConsoleUtil.printErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        } else {
                            ConsoleUtil.printErrorMSg(model);
                        }
                        break;
                    case TYPE_USER_SEARCH:
                        if (model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleImpl.showSearchWords(model.get());
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            ConsoleUtil.printErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        }
                        break;
                    case TYPE_WRITING_TWEET:
                        if(model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleUtil.printTweetAddedMessage();
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            ConsoleUtil.printErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        } else {
                            ConsoleUtil.printErrorMSg(model);
                            ConsoleImpl.openChatPage(socket,writer,jwToken);
                        }
                        break;
                    case TYPE_LOADING_TIMELINE:
                        if(model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleImpl.timeLinePage(socket,writer,(ArrayList<Tweet>) model.data,jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            ConsoleUtil.printErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        }else {
                            ConsoleUtil.printErrorMSg(model);
                            ConsoleImpl.openChatPage(socket,writer,jwToken);
                        }
                        break;
                    case TYPE_UNLIKE:
                        if(model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleUtil.printTweetUnlikedMessage();
                            ConsoleImpl.tweetPage(socket,writer,(Tweet) model.get(),jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            ConsoleUtil.printErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        }else {
                            ConsoleUtil.printErrorMSg(model);
                            ConsoleImpl.tweetPage(socket,writer,(Tweet) model.get(),jwToken);
                        }
                        break;
                    case TYPE_LIKE:
                        if(model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleUtil.printTweetLikedMessage();
                            ConsoleImpl.tweetPage(socket,writer,(Tweet) model.get(),jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            ConsoleUtil.printErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        }else {
                            ConsoleUtil.printErrorMSg(model);
                            ConsoleImpl.tweetPage(socket,writer,(Tweet) model.get(),jwToken);
                        }
                        break;
                    case TYPE_UNDO_RETWEET:
                        if(model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleUtil.printTweetUnRetweetedMessage();
                            ConsoleImpl.tweetPage(socket,writer,(Tweet) model.get(),jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            ConsoleUtil.printErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        }else {
                            ConsoleUtil.printErrorMSg(model);
                            ConsoleImpl.tweetPage(socket,writer,(Tweet) model.get(),jwToken);
                        }
                        break;
                    case TYPE_RETWEET:
                        if(model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleUtil.printTweetRetweetedMessage();
                            ConsoleImpl.tweetPage(socket,writer,(Tweet) model.get(),jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            ConsoleUtil.printErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        }else {
                            ConsoleUtil.printErrorMSg(model);
                            ConsoleImpl.tweetPage(socket,writer,(Tweet) model.get(),jwToken);
                        }
                        break;
                    case TYPE_QUOTE_TWEET:
                        if(model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleUtil.printTweetQuotedMessage();
                            ConsoleImpl.tweetPage(socket,writer,(Tweet) model.get(),jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            ConsoleUtil.printErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        }else {
                            ConsoleUtil.printErrorMSg(model);
                            ConsoleImpl.tweetPage(socket,writer,(Tweet) model.get(),jwToken);
                        }
                        break;
                    case TYPE_REPLY:
                        if(model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleUtil.printReplyAddedMessage();
                            ConsoleImpl.tweetPage(socket,writer,(Tweet) model.get(),jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            ConsoleUtil.printErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        }else {
                            ConsoleUtil.printErrorMSg(model);
                            ConsoleImpl.tweetPage(socket,writer,(Tweet) model.get(),jwToken);
                        }
                        break;
                    case TYPE_SHOW_OTHERS_PROFILE:
                        if(model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleImpl.showOthersProf(socket,(User) model.get(),writer,jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            ConsoleUtil.printErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        }else {
                            ConsoleUtil.printErrorMSg(model);
                            ConsoleImpl.requestTimeLine(socket,writer,jwToken);
                        }
                        break;
                    case TYPE_FOLLOW:
                        if(model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleUtil.printFollowMessage();
                            ConsoleImpl.showOthersProf(socket,(User) model.get(),writer,jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            ConsoleUtil.printErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        }else {
                            ConsoleUtil.printErrorMSg(model);
                            ConsoleImpl.showOthersProf(socket,(User) model.get(),writer,jwToken);
                        }
                        break;
                    case TYPE_UNFOLLOW:
                        if(model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleUtil.printUnFollowMessage();
                            ConsoleImpl.showOthersProf(socket,(User) model.get(),writer,jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            ConsoleUtil.printErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        }else {
                            ConsoleUtil.printErrorMSg(model);
                            ConsoleImpl.showOthersProf(socket,(User) model.get(),writer,jwToken);
                        }
                        break;
                    case TYPE_BLOCK:
                        if(model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleUtil.printBlockMessage();
                            ConsoleImpl.showOthersProf(socket,(User) model.get(),writer,jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            ConsoleUtil.printErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        }else {
                            ConsoleUtil.printErrorMSg(model);
                            ConsoleImpl.showOthersProf(socket,(User) model.get(),writer,jwToken);
                        }
                        break;
                    case TYPE_UNBLOCK:
                        if(model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleUtil.printUnBlockMessage();
                            ConsoleImpl.showOthersProf(socket,(User) model.get(),writer,jwToken);
                        } else if (model.message == ResponseOrErrorType.INVALID_JWT) {
                            ConsoleUtil.printErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket,writer,jwToken);
                        }else {
                            ConsoleUtil.printErrorMSg(model);
                            ConsoleImpl.showOthersProf(socket,(User) model.get(),writer,jwToken);
                        }
                        break;
                }
            }
        } catch (ClassNotFoundException | ParseException | IOException e) {
            throw new RuntimeException(e);
        }

    }
    public synchronized void write(Socket socket, SocketModel model, ObjectOutputStream writer){
        try {
            writer.writeObject(model);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

