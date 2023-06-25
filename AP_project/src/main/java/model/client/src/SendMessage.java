package main.java.model.client.src;

import main.java.model.common.src.SocketModel;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SendMessage{

    public static void write(Socket socket, SocketModel model, ObjectOutputStream writer){
        try {
            writer.writeObject(model);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
