package model.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import model.database.*;

public class Main {

    public static void main(String[] args) throws IOException {

        try  {
            ServerSocket socketServer = new ServerSocket(8080);
            SQLConnection.getInstance().connect();
            UsersTable usersTable = new UsersTable();
            //Pay Attention
//            usersTable.deleteTable();

            ExecutorService executorService = Executors.newCachedThreadPool();
            while (true) {
                Socket socket = socketServer.accept();
                System.out.println("ggj");
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