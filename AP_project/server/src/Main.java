import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws IOException {

        try  {
            ServerSocket socketServer = new ServerSocket(8080);
            SQLConnection.getInstance().connect();
            ExecutorService executorService = Executors.newCachedThreadPool();
            while (true) {
                Socket socket = socketServer.accept();
                ClientHandler clientHandler = new ClientHandler(socket);
                executorService.execute(clientHandler);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        SQLConnection.closeConnection();
        System.out.println("Server closed");
    }
}