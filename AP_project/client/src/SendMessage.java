import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class SendMessage{


//    @Override
//    public synchronized void run() {
//        try {
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
//            Scanner scanner = new Scanner(System.in);
////            System.out.println("Enter your name:");
//            while (true) {
//                String sendingMessage = scanner.nextLine();
//                objectOutputStream.writeObject(sendingMessage);
//                objectOutputStream.flush();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

    public static void write(Socket socket, SocketModel model, ObjectOutputStream writer){
        try {
            writer.writeObject(model);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
