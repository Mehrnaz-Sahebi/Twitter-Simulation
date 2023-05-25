import User.User;

import java.util.ArrayList;
import java.util.HashSet;

public class Tweet {
    private String text;
    private String photo;//doubtful about the type
    private HashSet<User> likes;
    private ArrayList<Tweet> comments;
    private String date;
    //retweet ?!?! which field
    private boolean favStar;
    private ArrayList<String> hashtags;
}
