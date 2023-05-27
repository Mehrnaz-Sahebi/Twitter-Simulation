public class Reply extends Tweet{
    private Tweet originalTweet;

    public Reply(String authorUsername, String text, String photo, Tweet originalTweet) {
        super(authorUsername, text, photo);
        this.originalTweet = originalTweet;
    }
}
