package model.server;

import model.common.*;
import model.database.*;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

public class TweetsFileConnection {
    public static synchronized SocketModel addTweet(Tweet tweet) {
        User user = null;
        try {
            user = (new UsersTable()).getUserFromDatabase(tweet.getAuthorUsername());
        } catch (SQLException e) {
            return new SocketModel(Api.TYPE_WRITING_TWEET, ResponseOrErrorType.UNSUCCESSFUL, false);
        }
        tweet.setAuthorName(user.getFirstName(), user.getLastName());
        tweet.setProfile(user.getAvatar());
        File file = new File("tweets.bin");
        HashSet<Tweet> tweets = new HashSet<Tweet>();
        if (!file.exists()) {
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("tweets.bin"))) {

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

    public static synchronized HashSet<Tweet> findTweetWithUsername(String username) throws Exception {
        HashSet<Tweet> tweetsWithUsername = new HashSet<Tweet>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("tweets.bin"))) {
            while (true) {
                try {
                    Tweet currentTweet = (Tweet) inputStream.readObject();
                    if (currentTweet.getAuthorUsername().equals(username)) {
                        System.out.println(username);
                        tweetsWithUsername.add(currentTweet);
                    }
                    if (currentTweet.isRetweetedBy(username)) {
                        System.out.println(username);
                        currentTweet.addSpecial(username);
                        tweetsWithUsername.add(currentTweet);
                    }
                } catch (EOFException e) {
                    break;
                }
            }
        };
        return tweetsWithUsername;
    }

    public static synchronized HashSet<Tweet> findFaveStarredTweets() throws Exception {
        HashSet<Tweet> tweetsWithUsername = new HashSet<Tweet>();

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("tweets.bin"))) {
            while (true) {
                try {
                    Tweet currentTweet = (Tweet) inputStream.readObject();
                    if (currentTweet.isFavStar()) {
                        tweetsWithUsername.add(currentTweet);
                    }
                } catch (EOFException e) {
                    break;
                }
            }
        }
        return tweetsWithUsername;
    }

    public static synchronized boolean tweetGetLiked(Tweet tweet, String likerUsername) {
        HashSet<Tweet> tweets = new HashSet<Tweet>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("tweets.bin"))) {
            while (true) {
                try {
                    tweets.add((Tweet) inputStream.readObject());
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            return false;
        }
        for (Tweet loopTweet : tweets) {
            if (loopTweet.getAuthorUsername().equals(tweet.getAuthorUsername())&&loopTweet.getDate().equals(tweet.getDate())
            ) {
                loopTweet.getLiked(likerUsername);
                break;
            }
        }
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("tweets.bin"))) {
            for (Tweet loopTweet : tweets) {
                outputStream.writeObject(loopTweet);
            }
            outputStream.flush();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static synchronized boolean tweetGetUnLiked(Tweet tweet, String likerUsername) {
        HashSet<Tweet> tweets = new HashSet<Tweet>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("tweets.bin"))) {
            while (true) {
                try {
                    tweets.add((Tweet) inputStream.readObject());
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            return false;
        }
        for (Tweet loopTweet : tweets) {
            if (loopTweet.getAuthorUsername().equals(tweet.getAuthorUsername())&&loopTweet.getDate().equals(tweet.getDate())) {
                loopTweet.getUnLiked(likerUsername);
                break;
            }
        }
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("tweets.bin"))) {
            for (Tweet loopTweet : tweets) {
                outputStream.writeObject(loopTweet);
            }
            outputStream.flush();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static synchronized boolean tweetRecievesAReply(Reply reply) {
        HashSet<Tweet> tweets = new HashSet<Tweet>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("tweets.bin"))) {
            while (true) {
                try {
                    tweets.add((Tweet) inputStream.readObject());
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            return false;
        }
        Tweet originalTweet = reply.getOriginalTweet();
        for (Tweet loopTweet : tweets) {
            if (loopTweet.getAuthorUsername().equals(originalTweet.getAuthorUsername())&&loopTweet.getDate().equals(originalTweet.getDate())) {
                loopTweet.recievesAReply(reply);
                break;
            }
        }
        tweets.add(reply);
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("tweets.bin"))) {
            for (Tweet loopTweet : tweets) {
                outputStream.writeObject(loopTweet);
            }
            outputStream.flush();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static synchronized boolean tweetGetRetweeted(Tweet tweet, String retweeterUsername) {
        HashSet<Tweet> tweets = new HashSet<Tweet>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("tweets.bin"))) {
            while (true) {
                try {
                    tweets.add((Tweet) inputStream.readObject());
                } catch (EOFException e) {
                    break;
                }

            }
        } catch (IOException | ClassNotFoundException e) {
            return false;
        }
        for (Tweet loopTweet : tweets) {
            if (loopTweet.getAuthorUsername().equals(tweet.getAuthorUsername())&&loopTweet.getDate().equals(tweet.getDate())) {
                loopTweet.getRetweeted(retweeterUsername);
                break;
            }
        }
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("tweets.bin"))) {
            for (Tweet loopTweet : tweets) {
                outputStream.writeObject(loopTweet);
            }
            outputStream.flush();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static synchronized boolean tweetGetUnRetweeted(Tweet tweet, String retweeterUsername) {
        HashSet<Tweet> tweets = new HashSet<Tweet>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("tweets.bin"))) {
            while (true) {
                try {
                    tweets.add((Tweet) inputStream.readObject());
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            return false;
        }
        for (Tweet loopTweet : tweets) {
            if (loopTweet.getAuthorUsername().equals(tweet.getAuthorUsername())&&loopTweet.getDate().equals(tweet.getDate())) {
                loopTweet.getUnRetweeted(retweeterUsername);
                break;
            }
        }
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("tweets.bin"))) {
            for (Tweet loopTweet : tweets) {
                outputStream.writeObject(loopTweet);
            }
            outputStream.flush();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static synchronized boolean tweetGetsQuoted(QuoteTweet quoteTweet) {
        HashSet<Tweet> tweets = new HashSet<Tweet>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("tweets.bin"))) {
            while (true) {
                try {
                    tweets.add((Tweet) inputStream.readObject());
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            return false;
        }
        Tweet originalTweet = quoteTweet.getOriginalTweet();
        for (Tweet loopTweet : tweets) {
            if (loopTweet.getAuthorUsername().equals(originalTweet.getAuthorUsername())&&loopTweet.getDate().equals(originalTweet.getDate())) {
                loopTweet.getsAQuote(quoteTweet);
                break;
            }
        }
        tweets.add(quoteTweet);
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("tweets.bin"))) {
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
        FollowTable followTable = SQLConnection.getFollowTable();
        HashSet<Tweet> tweets = new HashSet<Tweet>();

        for (String followingUsername : followTable.getFollowings(username)) {
            HashSet<Tweet> followingsTweets = TweetsFileConnection.findTweetWithUsername(followingUsername);
            for (Tweet loopTweet : followingsTweets) {
                tweets.add(loopTweet);
            }
        }
        return tweets;
    }

    public static synchronized HashSet<Tweet> findBlockingsTweets(String username) throws Exception {
        BlockTable blockTable = SQLConnection.getBlockTable();
        HashSet<Tweet> tweets = new HashSet<Tweet>();

        for (String blockingUsername : blockTable.getBlockings(username)) {
            HashSet<Tweet> blockingsTweets = TweetsFileConnection.findTweetWithUsername(blockingUsername);
            for (Tweet loopTweet : blockingsTweets) {
                tweets.add(loopTweet);
            }
        }
        return tweets;
    }

    public static synchronized ArrayList<Tweet> makeATimeLine(String username) throws Exception {
        HashSet<Tweet> tweets = new HashSet<Tweet>();
        HashSet<Tweet> favestarTweets = TweetsFileConnection.findFaveStarredTweets();
        System.out.println("fave"+favestarTweets.size());
        HashSet<Tweet> followingsTweets = TweetsFileConnection.findFollowingsTweets(username);
        System.out.println("following"+followingsTweets.size());
        HashSet<Tweet> blockingsTweets = TweetsFileConnection.findBlockingsTweets(username);
        System.out.println("block"+blockingsTweets.size());
        HashSet<Tweet> myTweets = TweetsFileConnection.findTweetWithUsername(username);
        for (Tweet loopTweet : favestarTweets) {
            tweets.add(loopTweet);
        }
        for (Tweet loopTweet : followingsTweets) {
            tweets.add(loopTweet);
        }
        ArrayList<Date> dates = new ArrayList<Date>();
        for (Tweet loopTweet : tweets) {
            for (Tweet blockTweet : blockingsTweets) {
                if (loopTweet.getAuthorUsername().equals(blockTweet.getAuthorUsername()) && loopTweet.getDate().equals(blockTweet.getDate())) {
                    tweets.remove(loopTweet);
                }
            }
            dates.add(loopTweet.getDate());
        }
        System.out.println("withoutmy"+tweets.size());
        System.out.println("my"+myTweets.size());
        for (Tweet loopTweet : myTweets) {
            tweets.add(loopTweet);
            dates.add(loopTweet.getDate());
        }
        System.out.println("withmy"+tweets.size());
        System.out.println("dates"+dates.size());
        Collections.sort(dates);
        ArrayList<Tweet> sortedTweets = new ArrayList<Tweet>();
        for (Date loopDate : dates) {
            for (Tweet loopTweet : tweets) {
                if (loopDate.equals(loopTweet.getDate())) {
                    boolean doesExist = false;
                    for (Tweet sorted:sortedTweets) {
                        if(loopTweet.equals(sorted)){
                            doesExist = true;
                        }
                    }
                    if(!doesExist) {
                        sortedTweets.add(loopTweet);
                    }
                }
            }
        }
        System.out.println("size"+sortedTweets.size());
        return sortedTweets;
    }
}
