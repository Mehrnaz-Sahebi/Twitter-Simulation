package model.server;

import model.common.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

public class ClientHandler implements Runnable {
    static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    Socket socketBetweenClientServer;
    ObjectInputStream objectInputStream = null;
    ObjectOutputStream objectOutputStream = null;
    private final String secret = "AP_1402";

    public ClientHandler(Socket client) {
        this.socketBetweenClientServer = client;

        try {
            objectInputStream = new ObjectInputStream(client.getInputStream());
            objectOutputStream = new ObjectOutputStream(client.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        clientHandlers.add(this);
    }
    @Override
    public void run() {
        try {
            while (socketBetweenClientServer.isConnected()) {
                SocketModel model = (SocketModel) objectInputStream.readObject();
                switch (model.eventType) {
                    case TYPE_SIGNIN -> {
                        UserToBeSigned user = (UserToBeSigned) model.get();
                            SocketModel socketModel = PagesToBeShownToUser.signInPage(user);
                            socketModel.makeJwToken(user.getUsername(), secret);
                            if (socketModel.message != null) {
                                write(socketModel);
                            } else {
                                socketModel.setMessage(ResponseOrErrorType.SUCCESSFUL);
                                write(socketModel);
                            }
                    }
                    case TYPE_SIGNUP -> {
                        UserToBeSigned user = (UserToBeSigned) model.get();
                        SocketModel res = PagesToBeShownToUser.signUpPage(user);
                        res.data = user;
                        res.makeJwToken(user.getUsername(), secret);
                        write(res);
                    }
                    case TYPE_SEE_PROF -> {
                        String username = ((UserToBeSigned) model.get()).getUsername();
                        SocketModel res = PagesToBeShownToUser.goToTheUsersProfile(username, false);
                        res.setEventType(Api.TYPE_SEE_PROF);
                        if (!model.checkJwToken(secret)) {
                            res.setMessage(ResponseOrErrorType.INVALID_JWT);
                        }
                        write(res);
                    }
                    case TYPE_SHOW_OTHERS_PROFILE ->{
                        String username = (String) model.get();
                        SocketModel res = PagesToBeShownToUser.goToTheUsersProfile(username, true);
                        res.setEventType(Api.TYPE_SHOW_OTHERS_PROFILE);
                        if (!model.checkJwToken(secret)) {
                            res.setMessage(ResponseOrErrorType.INVALID_JWT);
                        }
                        write(res);
                    }
                    case TYPE_GO_TO_EDIT_PROF -> {
                        User user = (User) model.get();
                        ResponseOrErrorType res = ResponseOrErrorType.SUCCESSFUL;
                        if (!model.checkJwToken(secret)) {
                            res = ResponseOrErrorType.INVALID_JWT;
                        }
                        write(new SocketModel(Api.TYPE_GO_TO_EDIT_PROF, res, user));
                    }
                    case TYPE_Update_PROF -> {
                        User user = (User) model.get();
                        ResponseOrErrorType res = PagesToBeShownToUser.updateProfile(user);
                        if (!model.checkJwToken(secret)) {
                            res = ResponseOrErrorType.INVALID_JWT;
                        }
                        write(new SocketModel(Api.TYPE_Update_PROF, res, user));
                    }
                    case TYPE_USER_SEARCH -> {
                        String searchWord = (String) model.get();
                        HashSet<String> res = PagesToBeShownToUser.searchInUsers(searchWord);
                        if (!model.checkJwToken(secret)) {
                            write(new SocketModel(Api.TYPE_USER_SEARCH, ResponseOrErrorType.INVALID_JWT, res));
                        } else {
                            write(new SocketModel(Api.TYPE_USER_SEARCH, ResponseOrErrorType.SUCCESSFUL, res));
                        }
                    }
                    case TYPE_WRITING_TWEET -> {
                        System.out.println("3");
                        Tweet tweetToAdd = (Tweet) model.get();
                        SocketModel res = PagesToBeShownToUser.addTweet(tweetToAdd);
                        if (!model.checkJwToken(secret)) {
                            res.setMessage(ResponseOrErrorType.INVALID_JWT);
                        }
                        write(res);
                    }
                    case TYPE_LOADING_TIMELINE -> {
                        String username = (String) model.get();
                        SocketModel res = PagesToBeShownToUser.makeATimeLine(username);
                        if (!model.checkJwToken(secret)) {
                            res.setMessage(ResponseOrErrorType.INVALID_JWT);
                        }
                        write(res);
                    }
                    case TYPE_UNLIKE -> {
                        Tweet tweetToUnlike = (Tweet) model.get();
                        SocketModel res = PagesToBeShownToUser.unLikeTweet(tweetToUnlike, model.getUsername());
                        if (!model.checkJwToken(secret)) {
                            res.setMessage(ResponseOrErrorType.INVALID_JWT);
                        }
                        tweetToUnlike.getUnLiked(model.getUsername());
                        res.data = tweetToUnlike;
                        write(res);
                    }
                    case TYPE_LIKE -> {
                        Tweet tweetToLike = (Tweet) model.get();
                        SocketModel res = PagesToBeShownToUser.likeTweet(tweetToLike, model.getUsername());
                        if (!model.checkJwToken(secret)) {
                            res.setMessage(ResponseOrErrorType.INVALID_JWT);
                        }
                        tweetToLike.getLiked(model.getUsername());
                        res.data = tweetToLike;
                        write(res);
                    }
                    case TYPE_UNDO_RETWEET -> {
                        Tweet tweetToUndoRetweet = (Tweet) model.get();
                        SocketModel res = PagesToBeShownToUser.undoReTweet(tweetToUndoRetweet, model.getUsername());
                        if (!model.checkJwToken(secret)) {
                            res.setMessage(ResponseOrErrorType.INVALID_JWT);
                        }
                        tweetToUndoRetweet.getUnRetweeted(model.getUsername());
                        res.data = tweetToUndoRetweet;
                        write(res);
                    }
                    case TYPE_RETWEET -> {
                        Tweet tweetToReTweet = (Tweet) model.get();
                        SocketModel res = PagesToBeShownToUser.reTweet(tweetToReTweet, model.getUsername());
                        if (!model.checkJwToken(secret)) {
                            res.setMessage(ResponseOrErrorType.INVALID_JWT);
                        }
                        tweetToReTweet.getRetweeted(model.getUsername());
                        res.data = tweetToReTweet;
                        write(res);
                    }
                    case TYPE_QUOTE_TWEET -> {
                        QuoteTweet quoteTweet = (QuoteTweet) model.get();
                        SocketModel res = PagesToBeShownToUser.quoteTweet(quoteTweet);
                        if (!model.checkJwToken(secret)) {
                            res.setMessage(ResponseOrErrorType.INVALID_JWT);
                        }
                        quoteTweet.getOriginalTweet().getsAQuote(quoteTweet);
                        res.data = quoteTweet.getOriginalTweet();
                        write(res);
                    }
                    case TYPE_REPLY -> {
                        Reply reply = (Reply) model.get();
                        SocketModel res = PagesToBeShownToUser.reply(reply);
                        if (!model.checkJwToken(secret)) {
                            res.setMessage(ResponseOrErrorType.INVALID_JWT);
                        }
                        reply.getOriginalTweet().recievesAReply(reply);
                        res.data = reply.getOriginalTweet();
                        write(res);
                    }
                    case TYPE_FOLLOW -> {
                        User user = (User) model.get();
                        SocketModel res = PagesToBeShownToUser.firstFollowsSecond(model.getUsername(), user.getUsername());
                        if (!model.checkJwToken(secret)) {
                            res.setMessage(ResponseOrErrorType.INVALID_JWT);
                        }
                        write(res);
                    }
                    case TYPE_UNFOLLOW -> {
                        User user = (User) model.get();
                        SocketModel res = PagesToBeShownToUser.firstUnFollowsSecond(model.getUsername(), user.getUsername());
                        if (!model.checkJwToken(secret)) {
                            res.setMessage(ResponseOrErrorType.INVALID_JWT);
                        }
                        write(res);
                    }
                    case TYPE_BLOCK -> {
                        User user = (User) model.get();
                        SocketModel res = PagesToBeShownToUser.firstBlocksSecond(model.getUsername(), user.getUsername());
                        if (!model.checkJwToken(secret)) {
                            res.setMessage(ResponseOrErrorType.INVALID_JWT);
                        }
                        write(res);
                    }
                    case TYPE_UNBLOCK -> {
                        User user = (User) model.get();
                        SocketModel res = PagesToBeShownToUser.firstUnBlocksSecond(model.getUsername(), user.getUsername());
                        if (!model.checkJwToken(secret)) {
                            res.setMessage(ResponseOrErrorType.INVALID_JWT);
                        }
                        write(res);
                    }
                    case TYPE_OFFLINE_REQUEST -> {

                    }
                }
            }

        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean verifyLogIn(UserToBeSigned user) {
//        return !OnlineUsers.isOnline(user);
        return true;
    }

    public void write(SocketModel model) {
        if (model == null) return;
        try {
            objectOutputStream.writeObject(model);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
