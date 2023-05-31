import java.io.*;
import java.util.*;

public class TweetsFileConnection {
    public static synchronized SocketModel addTweet(Tweet tweet) {
        File file = new File("./lib//tweets.bin");
        HashSet<Tweet> tweets = new HashSet<Tweet>();
        if (!file.exists()) {
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("././lib//tweets.bin"))) {
                outputStream.writeObject(tweet);
                outputStream.flush();
            } catch (IOException e) {
                return new SocketModel(Api.TYPE_WRITING_TWEET, ResponseOrErrorType.UNSUCCESSFUL, false);
            }
        } else {
            while (true) {
                try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("././lib//tweets.bin"))) {
                    tweets.add((Tweet) inputStream.readObject());
                } catch (EOFException e) {
                    break;
                } catch (IOException | ClassNotFoundException e) {
                    return new SocketModel(Api.TYPE_WRITING_TWEET, ResponseOrErrorType.UNSUCCESSFUL, false);
                }
            }
            tweets.add(tweet);
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("././lib//tweets.bin"))) {
                for (Tweet loopTweet : tweets) {
                    outputStream.writeObject(loopTweet);
                }
                outputStream.flush();
            } catch (IOException e) {
                return new SocketModel(Api.TYPE_WRITING_TWEET, ResponseOrErrorType.UNSUCCESSFUL, false);
            }
        }
        return new SocketModel(Api.TYPE_WRITING_TWEET, ResponseOrErrorType.SUCCESSFUL, true);
    }

    public static synchronized HashSet<Tweet> findTweetWithUsername(String username) throws Exception {
        HashSet<Tweet> tweetsWithUsername = new HashSet<Tweet>();
        while (true) {
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("././lib//tweets.bin"))) {
                Tweet currentTweet = (Tweet) inputStream.readObject();
                if (currentTweet.getAuthorUsername().equals(username)) {
                    tweetsWithUsername.add(currentTweet);
                }
            } catch (EOFException e) {
                break;
            }
        }
        return tweetsWithUsername;
    }

    public static synchronized HashSet<Tweet> findFaveStarredTweets() throws Exception {
        HashSet<Tweet> tweetsWithUsername = new HashSet<Tweet>();
        while (true) {
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("./lib//tweets.bin"))) {
                Tweet currentTweet = (Tweet) inputStream.readObject();
                if (currentTweet.isFavStar()) {
                    tweetsWithUsername.add(currentTweet);
                }
            } catch (EOFException e) {
                break;
            }
        }
        return tweetsWithUsername;
    }

    public static synchronized boolean tweetGetLiked(Tweet tweet, String likerUsername) {
        HashSet<Tweet> tweets = new HashSet<Tweet>();
        while (true) {
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("././lib//tweets.bin"))) {
                tweets.add((Tweet) inputStream.readObject());
            } catch (EOFException e) {
                break;
            } catch (IOException | ClassNotFoundException e) {
                return false;
            }
        }
        for (Tweet loopTweet:tweets) {
            if(loopTweet.equals(tweet)){
                loopTweet.getLiked(likerUsername);
                break;
            }
        }
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("././lib//tweets.bin"))) {
            for (Tweet loopTweet : tweets) {
                outputStream.writeObject(loopTweet);
            }
            outputStream.flush();
        } catch (IOException e) {
            return false;
        }
        return true;
    }
    public static synchronized boolean tweetRecievesAReply(Tweet tweet, Tweet reply) {
        HashSet<Tweet> tweets = new HashSet<Tweet>();
        while (true) {
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("././lib//tweets.bin"))) {
                tweets.add((Tweet) inputStream.readObject());
            } catch (EOFException e) {
                break;
            } catch (IOException | ClassNotFoundException e) {
                return false;
            }
        }
        Reply newReply;
        newReply = new Reply(reply.getAuthorUsername(),reply.getText(),reply.getPhoto(),tweet);
        for (Tweet loopTweet:tweets) {
            if(loopTweet.equals(tweet)){
                loopTweet.recievesAReply(newReply);
                break;
            }
        }
        tweets.add(newReply);
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("././lib//tweets.bin"))) {
            for (Tweet loopTweet : tweets) {
                outputStream.writeObject(loopTweet);
            }
            outputStream.flush();
        } catch (IOException e) {
            return false;
        }
        return true;
    }
    public static synchronized boolean tweetGetRetweeted(Tweet tweet , String retweeterUsername) {
        HashSet<Tweet> tweets = new HashSet<Tweet>();
        while (true) {
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("././lib//tweets.bin"))) {
                tweets.add((Tweet) inputStream.readObject());
            } catch (EOFException e) {
                break;
            } catch (IOException | ClassNotFoundException e) {
                return false;
            }
        }
        for (Tweet loopTweet:tweets) {
            if(loopTweet.equals(tweet)){
                loopTweet.getRetweeted(retweeterUsername);
                break;
            }
        }
        Retweet newRetweet = new Retweet(tweet,retweeterUsername);
        tweets.add(newRetweet);
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("././lib//tweets.bin"))) {
            for (Tweet loopTweet : tweets) {
                outputStream.writeObject(loopTweet);
            }
            outputStream.flush();
        } catch (IOException e) {
            return false;
        }
        return true;
    }
    public static synchronized boolean tweetGetsQuoted(Tweet originalTweet, String quote, String username, String photo ) {
        HashSet<Tweet> tweets = new HashSet<Tweet>();
        while (true) {
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("././lib//tweets.bin"))) {
                tweets.add((Tweet) inputStream.readObject());
            } catch (EOFException e) {
                break;
            } catch (IOException | ClassNotFoundException e) {
                return false;
            }
        }
        QuoteTweet quoteTweet = new QuoteTweet(username,quote,photo,originalTweet);
        for (Tweet loopTweet:tweets) {
            if(loopTweet.equals(originalTweet)){
                loopTweet.getsAQuote(quoteTweet);
                break;
            }
        }
        tweets.add(quoteTweet);
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("././lib//tweets.bin"))) {
            for (Tweet loopTweet : tweets) {
                outputStream.writeObject(loopTweet);
            }
            outputStream.flush();
        } catch (IOException e) {
            return false;
        }
        return true;
    }
    public static synchronized HashSet<Tweet> findFollowingsTweets(String username) throws Exception {
        FollowTable followTable = new FollowTable();
        HashSet<Tweet> tweets = new HashSet<Tweet>();

            for (String followingUsername: followTable.getFollowings(username)) {
                HashSet<Tweet> followingsTweets = TweetsFileConnection.findTweetWithUsername(followingUsername);
                for (Tweet loopTweet:followingsTweets) {
                    tweets.add(loopTweet);
                }
            }
        return tweets;
    }
    public static synchronized HashSet<Tweet> findBlockingsTweets(String username) throws Exception {
        BlockTable blockTable = new BlockTable();
        HashSet<Tweet> tweets = new HashSet<Tweet>();

            for (String blockingUsername: blockTable.getBlockings(username)) {
                HashSet<Tweet> blockingsTweets = TweetsFileConnection.findTweetWithUsername(blockingUsername);
                for (Tweet loopTweet:blockingsTweets) {
                    tweets.add(loopTweet);
                }
            }
        return tweets;
    }
    public static synchronized ArrayList<Tweet> makeATimeLine(String username) throws Exception{
        HashSet<Tweet> tweets = new HashSet<Tweet>();
        HashSet<Tweet> favestarTweets = TweetsFileConnection.findFaveStarredTweets();
        HashSet<Tweet> followingsTweets = TweetsFileConnection.findFollowingsTweets(username);
        HashSet<Tweet> blockingsTweets = TweetsFileConnection.findBlockingsTweets(username);
        for (Tweet loopTweet:favestarTweets) {
            tweets.add(loopTweet);
        }
        for (Tweet loopTweet:followingsTweets) {
            tweets.add(loopTweet);
        }
        ArrayList<Date> dates = new ArrayList<Date>();
        for (Tweet loopTweet:tweets) {
            for (Tweet blockTweet:blockingsTweets) {
                if(loopTweet.equals(blockTweet)){
                    tweets.remove(loopTweet);
                }
            }
            dates.add(loopTweet.getDate());
        }
        Collections.sort(dates);
        ArrayList<Tweet> sortedTweets = new ArrayList<Tweet>();
        for (Date loopDate : dates) {
            for (Tweet loopTweet:tweets) {
                if (dates.equals(loopTweet.getDate())){
                    sortedTweets.add(loopTweet);
                }
            }
        }
        return sortedTweets;
    }
}


