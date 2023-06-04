import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.ParseException;
import java.util.Hashtable;
import java.util.LinkedList;

public class Listener implements Runnable{

    private Socket socket;
    private final Hashtable<Integer, LinkedList<Listener>> listeners = new Hashtable<>();
    private boolean connected;
    private static Listener instance;
    private ObjectInputStream reader = null;
    private ObjectOutputStream writer = null;
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
                            ConsoleUtil.printLoginMessage((UserToBeSigned) model.data);
                            ConsoleImpl.openChatPage(socket, (UserToBeSigned) model.data, writer);
                        }else {
                            ConsoleUtil.printErrorMSg(model);
                            ConsoleImpl.openAccountMenu(socket, writer);
                        }
                        break;
                    case TYPE_CHANGE_PROF:
                        if (model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleImpl.showProf(socket, model.get(), writer);
                        }else {
                            ConsoleImpl.openChatPage(socket, (UserToBeSigned) model.data, writer);
                        }
                        break;
                    case TYPE_Update_PROF:
                        if (model.message == ResponseOrErrorType.SUCCESSFUL){

                        }else {
                            ConsoleUtil.printErrorMSg(model);
                        }
                        break;
                    case TYPE_USER_SEARCH:
                        if (model.message == ResponseOrErrorType.SUCCESSFUL){
                            ConsoleImpl.showSearchWords(model.get());
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

