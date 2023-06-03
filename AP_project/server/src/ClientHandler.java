import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientHandler implements Runnable{
    static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    Socket socketBetweenClientServer;
    ObjectInputStream objectInputStream;
    ObjectOutputStream objectOutputStream;
    public ClientHandler(Socket client) {
        try {
            this.socketBetweenClientServer = client;
            objectOutputStream = new ObjectOutputStream(client.getOutputStream());
            objectInputStream = new ObjectInputStream(client.getInputStream());
            clientHandlers.add(this);
        }catch (IOException exception){
//            closeEveryThing();
        }
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
                                OnlineUsers.addOnlineUser(this);
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
                            logIn(user);
                        }
                        write(res);
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

    public synchronized void write(SocketModel model) {
        if (model == null) return;
        try {
            objectOutputStream.writeObject(model);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
