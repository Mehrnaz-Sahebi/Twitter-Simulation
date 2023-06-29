package model.common;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;

public class Tweet implements Serializable {
    private String authorUsername;
    private String authorName;
    private String profile;
    private String text;
    private String photo;//the url
    private HashSet<String> likes;
    private HashSet<Reply> replies;
    private HashSet<String> retweets;
    private HashSet<QuoteTweet> quoteTweets;
    private Date date;
    //retweet ?!?! which field
    private boolean favStar;
    private HashSet<String> hashtags;

    public Tweet(String authorUsername, String text, String photo) {
        this.authorUsername = authorUsername;
        this.text = text;
        this.photo = photo;
        likes = new HashSet<String>();
        replies = new HashSet<Reply>();
        retweets = new HashSet<String>();
        quoteTweets = new HashSet<QuoteTweet>();
        date = new Date();
        favStar = false;
        hashtags = new HashSet<String>();
        String[] words = text.split(" ");
        for (String word : words) {
            if (Character.toString(word.charAt(0)).equals("#")) {
                hashtags.add(word.substring(1, word.length()));
            }
        }
    }

    public Tweet() {
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setLikes(HashSet<String> likes) {
        this.likes = likes;
    }

    public void setReplies(HashSet<Reply> replies) {
        this.replies = replies;
    }

    public void setRetweets(HashSet<String> retweets) {
        this.retweets = retweets;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public void setAuthorName(String firstName , String lastName) {
        this.authorName = firstName + " " + lastName;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isFavStar() {
        return favStar;
    }

    public void setFavStar(boolean favStar) {
        this.favStar = favStar;
    }

    public HashSet<String> getHashtags() {
        return hashtags;
    }

    public String getText() {
        return text;
    }

    public String getPhoto() {
        return photo;
    }

    public HashSet<String> getLikes() {
        return likes;
    }

    public HashSet<Reply> getReplies() {
        return replies;
    }

    public HashSet<String> getRetweets() {
        return retweets;
    }

    public HashSet<QuoteTweet> getQuoteTweets() {
        return quoteTweets;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getProfile() {
        return profile;
    }

    public void setQuoteTweets(HashSet<QuoteTweet> quoteTweets) {
        this.quoteTweets = quoteTweets;
    }

    public void setHashtags(HashSet<String> hashtags) {
        this.hashtags = hashtags;
    }

    public void getLiked(String username) {
        likes.add(username);
        if(likes.size()>10){
            favStar = true;
        }
    }
    public void getUnLiked(String username){
        likes.remove(username);
        if(likes.size()<=10){
            favStar = false;
        }
    }

    public void recievesAReply(Reply reply) {
        replies.add(reply);
    }

    public void getRetweeted(String retweeter) {
        retweets.add(retweeter);
    }
    public void getUnRetweeted(String retweeter) {
        retweets.remove(retweeter);
    }
    public void getsAQuote(QuoteTweet quoteTweet){
        quoteTweets.add(quoteTweet);
    }
    ///TODO changed
    public boolean isLikedBy(String username){
        for (String liker:likes) {
            if(liker.equals(username)){
                return true;
            }
        }
        return false;
    }
    public boolean isRetweetedBy(String username){
        for (String retweeter:retweets) {
            if(retweeter.equals(username)){
                return true;
            }
        }
        return false;
    }
}

