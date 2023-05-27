public class Retweet extends Tweet{
    private String retweeterUsername;

    public Retweet(Tweet originalTweet, String retweeterUsername) {
        this.setAuthorUsername(originalTweet.getAuthorUsername());
        this.setDate(originalTweet.getDate());
        this.setRetweets(originalTweet.getRetweets());
        this.setHashtags(originalTweet.getHashtags());
        this.setFavStar(originalTweet.isFavStar());
        this.setLikes(originalTweet.getLikes());
        this.setPhoto(originalTweet.getPhoto());
        this.setReplies(originalTweet.getReplies());
        this.setQuoteTweets(originalTweet.getQuoteTweets());
        this.setText(originalTweet.getText());
        this.retweeterUsername = retweeterUsername;
    }
}
