package Server;

import User.Running.SafeRunning;

import java.net.ServerSocket;
import java.net.Socket;
import DataBase.SQLConnection;

public class Main {

    public static void main(String[] args) {
        try (ServerSocket socketServer = new ServerSocket()) {
            SQLConnection.getInstance().connect();

            while (true) {
                SafeRunning.safe(() -> {
                    Socket socket = socketServer.accept();
//                    new UserThread(socket).start();
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        SQLConnection.closeConnection();
        System.out.println("Server closed");
    }
}