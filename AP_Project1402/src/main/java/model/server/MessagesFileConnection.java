package model.server;

import model.common.*;
import model.database.UsersTable;

import java.io.*;
import java.sql.SQLException;
import java.util.HashSet;

public class MessagesFileConnection {
    public final static String ADDRESS = "messages.bin";
    public static synchronized SocketModel addMessage(Message message) {
        File file = new File(ADDRESS);
        HashSet<Message> messages = new HashSet<Message>();
        if (!file.exists()) {
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(ADDRESS))) {
                outputStream.writeObject(message);
                outputStream.flush();
            } catch (IOException e) {
                return new SocketModel(Api.TYPE_MESSAGE, ResponseOrErrorType.UNSUCCESSFUL_FILE, false);
            }
        } else {
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(ADDRESS))) {
                while (true) {
                    try {
                        messages.add((Message) inputStream.readObject());
                    } catch (EOFException e) {
                        break;
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                return new SocketModel(Api.TYPE_MESSAGE, ResponseOrErrorType.UNSUCCESSFUL_FILE, false);
            }
            messages.add(message);
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(ADDRESS))) {
                for (Message loopMessage : messages) {
                    outputStream.writeObject(loopMessage);
                }
                outputStream.flush();
            } catch (IOException e) {
                return new SocketModel(Api.TYPE_MESSAGE, ResponseOrErrorType.UNSUCCESSFUL_FILE, false);
            }
        }

        return new SocketModel(Api.TYPE_MESSAGE, ResponseOrErrorType.SUCCESSFUL, true);
    }
    public static synchronized SocketModel findMessagesFor(String username) throws Exception {
        HashSet<Message> messagesForUsername = new HashSet<Message>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(ADDRESS))) {
            while (true) {
                try {
                    Message currentMessage = (Message) inputStream.readObject();
                    if (currentMessage.getToWhom().equals(username)) {
                        messagesForUsername.add(currentMessage);
                    }
                } catch (EOFException e) {
                    break;
                }
            }
        };
        return new SocketModel(Api.TYPE_GET_MESSAGE, ResponseOrErrorType.SUCCESSFUL, messagesForUsername);
    }

    public static boolean updateProfile(String username, String newUsername,String newProfilePhoto){
        HashSet<Message> messages = new HashSet<Message>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(ADDRESS))) {
            while (true) {
                try {
                    messages.add((Message) inputStream.readObject());
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            return false;
        }
        for (Message loopMessage : messages) {
            if (loopMessage.getToWhom().equals(username)) {
                loopMessage.setToWhom(newUsername);
            }
            if(loopMessage.getSenderUsername().equals(username)){
                loopMessage.setSenderUsername(newUsername);
                loopMessage.setAvtar(newProfilePhoto);
            }
        }
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("messages.bin"))) {
            for (Message loopMessage : messages) {
                outputStream.writeObject(loopMessage);
            }
            outputStream.flush();
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
