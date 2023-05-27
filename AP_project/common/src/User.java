import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class User implements Serializable {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String avatar;
    private String header;
    private String regionOrCountry;
    private Date birthDate;
    private Date SignUpDate;
    private Date lastModifiedDate;
    private String bio;
    private String location;
    private String website;
    private HashSet<String> followings;
    private HashSet<String> followers;
    private HashSet<String> blackList;

    //methods
    public void tweet(Tweet tweet){
        TweetsFileConnection.addTweet(tweet);
    }
    public void reTweet(Tweet tweet){
        TweetsFileConnection.tweetGetRetweeted(tweet,username);
    }
    public void reply(Tweet tweet,Tweet reply){
        TweetsFileConnection.tweetRecievesAReply(tweet,reply);
    }
    public void follow(String followingUsername){
        SafeRunning.safe(()-> {
            FollowTable table = SQLConnection.getInstance().followTable;
            table.firstFollowsSecend(username, followingUsername);
        });
    }

    public void unfollow(String followingUsername){
        SafeRunning.safe(()-> {
            FollowTable table = SQLConnection.getInstance().followTable;
            table.firstUnfollowsSecend(username, followingUsername);
        });
    }
    public void block(String blockingUsername){
        SafeRunning.safe(()-> {
            BlockTable table = SQLConnection.getInstance().blockTable;
            table.firstBlocksSecend(username, blockingUsername);
        });
    }

    public void unblock(String blockingUsername){
        SafeRunning.safe(()-> {
            BlockTable table = SQLConnection.getInstance().followTable;
            table.firstUnblockSecend(username, blockingUsername);
        });
    }
}
