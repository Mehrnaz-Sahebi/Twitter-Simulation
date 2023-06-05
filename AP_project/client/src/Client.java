import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.ParseException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 8080);
            ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream reader = new ObjectInputStream(socket.getInputStream());
            ExecutorService executorService = Executors.newCachedThreadPool();
            start(socket, writer);
            Listener listeningUser = new
                    Listener(socket, reader, writer);
            executorService.execute(listeningUser);





        } catch (IOException | ParseException e) {
            System.out.println("Server isn't available");
        }
    }
    public static void start(Socket socket, ObjectOutputStream writer) throws ParseException {
        ConsoleImpl.openAccountMenu(socket, writer,null);


    }
}

