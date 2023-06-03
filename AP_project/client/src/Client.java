import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", 8080)){

            ExecutorService executorService = Executors.newCachedThreadPool();

            Listener listeningUser = Listener.getInstance(socket);
            start(socket);





        } catch (IOException e) {
            System.out.println("Server isn't available");
        }
    }
    public static void start(Socket socket){
        DesignFlow showTheDesign = new ConsoleImpl();
        showTheDesign.openAccountMenu(socket);


    }
}
