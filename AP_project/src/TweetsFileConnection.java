import User.Api;
import User.SocketModel;

import java.io.*;
import java.util.ArrayList;

public class TweetsFileConnection {
    public static SocketModel addTweet(Tweet tweet){
        File file = new File("./lib//tweets.bin");
        ArrayList<Tweet> tweets = new ArrayList<Tweet>();
        if(!file.exists()){
            try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("./lib//tweets.bin"))){
                outputStream.writeObject(tweet);
                outputStream.flush();
            }catch (IOException e){
                return new SocketModel(Api.TYPE_WRITING_TWEET,"You can't tweet right now!",false);
            }
        }
        else{
            while (true){
                try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("./lib//tweets.bin"))){
                    tweets.add((Tweet) inputStream.readObject());
                }catch (EOFException e){
                    break;
                }catch (IOException | ClassNotFoundException e){
                    return new SocketModel(Api.TYPE_WRITING_TWEET,"You can't write tweet right now!",false);
                }
            }
            tweets.add(tweet);
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("./lib//tweets.bin"))){
                for (Tweet loopTweet:tweets) {
                    outputStream.writeObject(loopTweet);
                }
                outputStream.flush();
            }catch (IOException e){
                return new SocketModel(Api.TYPE_WRITING_TWEET,"You can't write tweet right now!",false);
            }
        }
        return new SocketModel(Api.TYPE_WRITING_TWEET,true);
    }
    public static SocketModel findTweetWithUsername
}
