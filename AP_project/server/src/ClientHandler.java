import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

public class ClientHandler implements Runnable{
    static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    Socket socketBetweenClientServer;
    ObjectInputStream objectInputStream = null;
    ObjectOutputStream objectOutputStream = null;
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
        try{
            while (socketBetweenClientServer.isConnected()){
                SocketModel model = (SocketModel) objectInputStream.readObject();
                switch (model.eventType){
                    case TYPE_SIGNIN ->{
                        UserToBeSigned user = (UserToBeSigned) model.get();
                        if (verifyLogIn(user)){
                            SocketModel socketModel = PagesToBeShownToUser.signInPage(user);
                            if (socketModel.message != null){
                                write(socketModel);
                            }else {
                                socketModel.setMessage(ResponseOrErrorType.SUCCESSFUL);
                                write(socketModel);
                                OnlineUsers.addOnlineUser(this, user);
                            }
                        }else{
                            model.setMessage(ResponseOrErrorType.ALREADY_ONLINE);
                            write(model);
                        }
                    }
                    case TYPE_SIGNUP ->{
                        UserToBeSigned user = (UserToBeSigned) model.get();
                        SocketModel res = PagesToBeShownToUser.signUpPage(user);
                        if (res.get()) {
                            OnlineUsers.addOnlineUser(this, user);
                        }
                        res.data = null;
                        res.data = user;
                        write(res);
                    }
                    case TYPE_CHANGE_PROF ->{
                        UserToBeSigned user = (UserToBeSigned) model.get();
                        SocketModel res = PagesToBeShownToUser.goToTheUsersProfile(user.getUsername());
                        res.setEventType(Api.TYPE_CHANGE_PROF);
                        write(res);
                    }
                    case TYPE_Update_PROF ->{
                        User user = (User) model.get();
                        ResponseOrErrorType res = PagesToBeShownToUser.updateProfile(user);
                        write(new SocketModel(Api.TYPE_Update_PROF, res));
                    }
                    case TYPE_USER_SEARCH ->{
                        UserToBeSigned user = (UserToBeSigned) model.get();
                        HashSet<String> res = PagesToBeShownToUser.searchInUsers(user.getSearchingWord());
                        write(new SocketModel(Api.TYPE_Update_PROF, ResponseOrErrorType.SUCCESSFUL, res));
                    }
                }
            }

        }catch (IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean verifyLogIn(UserToBeSigned user){
        return !OnlineUsers.isOnline(user);
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
