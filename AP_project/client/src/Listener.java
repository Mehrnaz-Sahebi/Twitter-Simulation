import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Scanner;

public class Listener implements Runnable{

    private Socket socket;
    private final Hashtable<Integer, LinkedList<Listener>> listeners = new Hashtable<>();
//    private ObjectOutputStream writer;
    private ObjectInputStream reader;
    private boolean connected;
    private static Listener instance;

    public static Listener getInstance(Socket socket) {
        if (instance == null) {
            instance = new Listener(socket);
        }
        return instance;
    }

    private Listener(Socket socket) {
        this.socket = socket;
    }

    @Override
    public synchronized void run() {
        try {
            SocketModel model = (SocketModel) reader.readObject();
            if (model.message == ResponseOrErrorType.SUCCESSFUL){
                ConsoleUtil.printLoginMessage((UserToBeSigned) model.data);
                ConsoleImpl.openChatPage((UserToBeSigned) model.data);
            }else {
                ConsoleUtil.printErrorMSg(model);
                ConsoleImpl.openAccountMenu(socket);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
