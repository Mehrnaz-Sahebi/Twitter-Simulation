<<<<<<< HEAD

    package Common;

    public class QuoteTweet extends Tweet{
        private Tweet originalTweet;

        public QuoteTweet(String authorUsername, String text, String photo, Tweet originalTweet) {
            super(authorUsername, text, photo);
            this.originalTweet = originalTweet;
        }
    }
=======
package Common;

public class QuoteTweet extends Tweet{
    private Tweet originalTweet;

    public QuoteTweet(String authorUsername, String text, String photo, Tweet originalTweet) {
        super(authorUsername, text, photo);
        this.originalTweet = originalTweet;
    }
}
>>>>>>> fa5af0753254ec148910ebcb95c1d645961e36ac
