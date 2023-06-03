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
    private ObjectOutputStream writer;
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
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            Scanner scanner = new Scanner(System.in);
//            System.out.println("Enter your name:");
            while (true) {
                String sendingMessage = scanner.nextLine();
                objectOutputStream.writeObject(sendingMessage);
                objectOutputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
