package controller.client;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.console_action.ConsoleImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientController implements Initializable {
    private Client client;
    @FXML
    private Label la;
//    public static void main(String[] args) {
//        try {
//            Socket socket = new Socket("127.0.0.1", 8080);
//            ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream());
//            ObjectInputStream reader = new ObjectInputStream(socket.getInputStream());
//            ExecutorService executorService = Executors.newCachedThreadPool();
//            start(socket, writer);
//            Client listeningUser = new
//                    Client(socket, reader, writer);
//            executorService.execute(listeningUser);
//
//
//
//
//
//        } catch (IOException | ParseException e) {
//            System.out.println("Server isn't available");
//        }
//    }
//    public static void start(Socket socket, ObjectOutputStream writer) throws ParseException {
//        ConsoleImpl.openAccountMenu(socket, writer,null);
//
//
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            client = new Client(new Socket("127.0.0.1", 8080));
        } catch (IOException e) {
            System.out.println("Server isn't available");
        }


    }
}

