import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);
        //Create an HTTP Context and set it's path
        HttpContext context = server.createContext("/");

        //Create a Request Handler for the Server
        context.setHandler(new RequestHandler());
        server.start();



//        try (ServerSocket socketServer = new ServerSocket(8080)) {
//            SQLConnection.getInstance().connect();
//
//            while (true) {
//                SafeRunning.safe(() -> {
//                    Socket socket = socketServer.accept();
////                    new UserThread(socket).start();
//                });
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        SQLConnection.closeConnection();
//        System.out.println("Server closed");
    }
}