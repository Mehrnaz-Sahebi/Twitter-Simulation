package model.server;

import model.common.*;
import model.database.UsersTable;

import java.io.*;
import java.sql.SQLException;
import java.util.HashSet;

public class MessagesFileConnection {
    public static synchronized SocketModel addTweet(Tweet tweet) {
        File file = new File("messages.bin");
        HashSet<Tweet> tweets = new HashSet<Tweet>();
        if (!file.exists()) {
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("messages.bin"))) {

                outputStream.writeObject(tweet);
                outputStream.flush();
            } catch (IOException e) {
                return new SocketModel(Api.TYPE_WRITING_TWEET, ResponseOrErrorType.UNSUCCESSFUL_FILE, false);
            }
        } else {
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("tweets.bin"))) {
                while (true) {
                    try {
                        tweets.add((Tweet) inputStream.readObject());
                    } catch (EOFException e) {
                        break;
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                return new SocketModel(Api.TYPE_WRITING_TWEET, ResponseOrErrorType.UNSUCCESSFUL_FILE, false);
            }
            tweets.add(tweet);
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("tweets.bin"))) {
                for (Tweet loopTweet : tweets) {
                    outputStream.writeObject(loopTweet);
                }
                outputStream.flush();
            } catch (IOException e) {
                return new SocketModel(Api.TYPE_WRITING_TWEET, ResponseOrErrorType.UNSUCCESSFUL_FILE, false);
            }
        }

        return new SocketModel(Api.TYPE_WRITING_TWEET, ResponseOrErrorType.SUCCESSFUL, true);
    }
}
