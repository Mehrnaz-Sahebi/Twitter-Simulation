package main.java.model.console_action.src;

import main.java.model.common.src.*;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleUtil {

    private static final Scanner scanner = new Scanner(System.in);
    /*
     scans the user's choice in string type
     */
    public static String waitForString() {
        while (true) {
            String val = scanner.nextLine();

            if (!val.isBlank()) {
                return val.trim();
            }
        }
    }
    /*
    if user type a string in non-integer type it throw an exception that it is invalid
     */
    public static int waitForInteger() {
        while (true) {
            String val = waitForString();

            try {
                return Integer.parseInt(val);
            } catch (Exception ignore) {
                System.err.println("Invalid!");
            }
        }
    }
    /*
    * waits for the user to enter the choice , if choice is out of range, Invalid command
     */
    public static int waitForCommand(int rangeFrom, int rangeTo) {
        while (true) {
            int cmd = waitForInteger();
            if (cmd < rangeFrom || cmd > rangeTo) {
                System.err.println("Invalid Command!");
            } else {
                return cmd;
            }
        }
    }

    public static void printCommandHint(String command) {
        printColored(36, command);
    }

    public static void printColored(int color, String text) {
        System.out.println("\u001B[" + color + "m" + text + "\u001B[0m");
    }

    public static void printHello(String username) {
        printColored(33, "Hello " + username );
//        printColored(33, "Your Email: " + user.getEmail());
        printColored(33, "WELCOME TO TWITTER!");
    }

    public static void printJoinMessage(UserToBeSigned user) {
        printColored(32, user.getUsername() + " Joined! (" + user.getFirstName() + user.getLastName()  + ")");
    }

    public static void printLeftMessage(UserToBeSigned user) {
        printColored(31, user.getUsername() + " Left! (" + user.getFirstName() + user.getLastName() + ")");
    }


    public static void printLoginMessage(UserToBeSigned user) {
        printColored(35, " Welcome! (" + user.getFirstName() + user.getLastName() + ")"); //pink color
    }
//    public static void printMessage(MessageModel messageModel) {
//        printColored(34, messageModel.getSender().getUsername() +
//                ":\u001B[0m " + messageModel.getText());
//    }

    public static void printErrorMSg(SocketModel socketuser) {
        switch (socketuser.message){
            case ALREADY_ONLINE -> {
                printColored(32, " This account is already online!"); //cyan color
            }
            case INVALID_PASS -> {
                printColored(31, " Invalid password"); //cyan color
            }
            case USER_NOTFOUND -> {
                printColored(31, " User not found"); //cyan color
            }
            case DUPLICATE_USERNAME -> {
                printColored(36, " duplicate username"); //cyan color
            }
            case DUPLICATE_EMAIL -> {
                printColored(36, " duplicate email"); //cyan color
            }
            case DUPLICATE_PHONENUMBER -> {
                printColored(36, " duplicate phone number"); //cyan color
            }
            case UNSUCCESSFUL -> {
                printColored(33, " Unsuccessful because of different DB probs"); //cyan color
            }
            case INVALID_JWT -> {
                printColored(31,"Not You? Try signing in again.");
            }
//            case USER_NOTFOUND -> {
//                printColored(36, " User not found"); //cyan color
//            }
//            case USER_NOTFOUND -> {
//                printColored(36, " User not found"); //cyan color
//            }
//            case USER_NOTFOUND -> {
//                printColored(36, " User not found"); //cyan color
//            }
        }
    }
    public static void printTweetAddedMessage() {
        printColored(35, "Your Tweet has been added successfully."); //pink color
    }
    public static void printTweetUnlikedMessage() {
        printColored(35, "Your like has been undone successfully."); //pink color
    }
    public static void printTweetLikedMessage() {
        printColored(35, "The Tweet has been liked successfully."); //pink color
    }public static void printTweetRetweetedMessage() {
        printColored(35, "The Tweet has been retweeted successfully."); //pink color
    }public static void printTweetUnRetweetedMessage() {
        printColored(35, "Your retweet has been undone successfully."); //pink color
    }public static void printTweetQuotedMessage() {
        printColored(35, "Your quote has been added successfully."); //pink color
    }public static void printReplyAddedMessage() {
        printColored(35, "Your reply has been added successfully."); //pink color
    }
    public static void printFollowMessage(){
        printColored(35,"You followed this account successfully.");
    }
    public static void printUnFollowMessage(){
        printColored(35,"You unfollowed this account successfully.");
    }
    public static void printBlockMessage(){
        printColored(35,"You blocked this account successfully.");
    }
    public static void printUnBlockMessage(){
        printColored(35,"You unblocked this account successfully.");
    }
    public static void printTimeLine(ArrayList<Tweet> tweets){
        for (int i = 0; i < tweets.size(); i++) {
            System.out.print(i+1 + "- ");
            printATweet(tweets.get(i));
        }
    }
    public static void printANormalTweet(Tweet tweet){
        printColored(70,tweet.getAuthorUsername()+" :");
        printColored(84, tweet.getText());
    }
    public static void printARetweet(Retweet retweet){
        printColored(70,"("+retweet.getRetweeterUsername()+" retweeted) "+retweet.getAuthorUsername()+" :");
        printColored(84,retweet.getText());
    }
    public static void printAReply(Reply reply){
        printColored(70,reply.getAuthorUsername()+" replied to "+reply.getOriginalTweet().getAuthorUsername()+" :");
        printColored(84,reply.getText());
    }
    public static void printAQuote(QuoteTweet quoteTweet){
        printColored(70,quoteTweet.getAuthorUsername()+" quoted \""+quoteTweet.getText() +"\" to");
        printANormalTweet(quoteTweet.getOriginalTweet());
    }
    public static void printATweet(Tweet tweet){
        if(tweet instanceof Retweet){
            printARetweet((Retweet) tweet);
        } else if (tweet instanceof Reply) {
            printAReply((Reply) tweet);
        } else if (tweet instanceof QuoteTweet) {
            printAQuote((QuoteTweet) tweet);
        } else {
            printANormalTweet(tweet);
        }
    }
}
