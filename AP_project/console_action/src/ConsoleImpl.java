import org.json.JSONException;
import org.json.JSONObject;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ConsoleImpl {

    public synchronized static void failedToConnect() {
        System.err.println("Failed to connect!");
    }

    public synchronized static void openAccountMenu(Socket socket, ObjectOutputStream writer ,String jwt) throws ParseException {
        ConsoleUtil.printCommandHint("1. Login");
        ConsoleUtil.printCommandHint("2. Signup");

        int cmd = ConsoleUtil.waitForCommand(1, 2);
        if (cmd == 1) {
            openLoginForm(socket, writer,jwt);
        } else {
            openSignupForm(socket, writer,jwt);
        }
    }

    public synchronized static void openLoginForm(Socket socket, ObjectOutputStream writer ,String jwt) {
        UserToBeSigned user = new UserToBeSigned();

        ConsoleUtil.printCommandHint("Enter username: ");
        user.setUsername(ConsoleUtil.waitForString());

        ConsoleUtil.printCommandHint("Enter password: ");
        user.setPassword(ConsoleUtil.waitForString());

//        return new SocketModel(Api.TYPE_SIGNIN, user);
        SendMessage.write(socket, new SocketModel(Api.TYPE_SIGNIN, user,jwt), writer);

//        SocketApi.getInstance().writeAndListen(
//                new SocketModel(Api.TYPE_SIGNIN, user),
//                model -> {
//                    UserToBeSigned result = model.get();
//                    if (result == null) {
//                        System.out.println(model.message);
//                        openAccountMenu();
//                    } else {
//                        openChatPage(result);
//                    }
//                }
//        );
    }

    public synchronized static void openSignupForm(Socket socket, ObjectOutputStream writer , String jwt) throws ParseException {
        ConsoleUtil.printCommandHint("Creating new account...");

        UserToBeSigned user = new UserToBeSigned();

        ConsoleUtil.printCommandHint("Enter username: ");
        user.setUsername(ConsoleUtil.waitForString());

        ConsoleUtil.printCommandHint("Enter password: ");
        String pass = ConsoleUtil.waitForString();
        while (Validate.validPass(pass) != ResponseOrErrorType.SUCCESSFUL) {
            ConsoleUtil.printCommandHint("correct pass = 8chars in both upper and lower case: ");
            pass = ConsoleUtil.waitForString();
        }


        ConsoleUtil.printCommandHint("Repeat your password: ");
        String repeatPass = ConsoleUtil.waitForString();
        while (!pass.equals(repeatPass)) {
            ConsoleUtil.printCommandHint("Not the same: ");
            repeatPass = ConsoleUtil.waitForString();
        }
        user.setPassword(pass);

        ConsoleUtil.printCommandHint("Enter your FIRST name: ");
        user.setFirstName(ConsoleUtil.waitForString());

        ConsoleUtil.printCommandHint("Enter your LAST name: ");
        user.setLastName(ConsoleUtil.waitForString());

        ConsoleUtil.printCommandHint("Enter your email: ");
        String email = ConsoleUtil.waitForString();
        while (Validate.validateEmail(email) != ResponseOrErrorType.SUCCESSFUL) {
            ConsoleUtil.printCommandHint("Invalid email: ");
            email = ConsoleUtil.waitForString();
        }
        user.setEmail(email);

        ConsoleUtil.printCommandHint("Enter your phone number : ");
        String phNumber = ConsoleUtil.waitForString();
        user.setPhoneNumber(phNumber);

        ConsoleUtil.printCommandHint("Enter your birthDate: ");
        String birthDate = ConsoleUtil.waitForString();
        while (Validate.validateDateFormat(birthDate) != ResponseOrErrorType.SUCCESSFUL) {
            ConsoleUtil.printCommandHint("Invalid date format: ");
            birthDate = ConsoleUtil.waitForString();
        }
        Date date = new SimpleDateFormat("yyyy/MM/dd").parse(birthDate);//casting the str to Date class
        user.setBirthDate(date);
        //TODO show the countries list


        //checkTheFormatAndAllowedConditions
//        if (isValidDateFormat != ResponseOrErrorType.INVALID_DATEFORMAT){
//            //TODO send response for client and come back
//        }
//        if (isValidEmail != ResponseOrErrorType.INVALID_EMAIL){
//            //TODO send response for client and come back
//        }
//        if (isValidPass != ResponseOrErrorType.INVALID_PASS){
//            //TODO send response for client and come back
//        }
//        if (!isEqualRepeatedPass){
//            //TODO send response for client and come back
//        }
        if (email.isBlank() //&& phoneNumber.isBlank()
        ) {
            //TODO send response that enter at least one of these items
        }
//        return new SocketModel(Api.TYPE_SIGNUP, user);
        SendMessage.write(socket, new SocketModel(Api.TYPE_SIGNUP, user,jwt), writer);
    }


    public synchronized static void openChatPage(Socket socket, ObjectOutputStream writer ,String jwt) {
        String username = getUsername(jwt);
        ConsoleUtil.printHello(username);

        int cmd = 0;
//        if (cmd != 5) {
            ConsoleUtil.printCommandHint("1. Profile");
            ConsoleUtil.printCommandHint("2. Search");
            ConsoleUtil.printCommandHint("3. TimeLine");
            ConsoleUtil.printCommandHint("4. Add tweet");
            ConsoleUtil.printCommandHint("5. enter 5 in order to exit for chat");
            cmd = ConsoleUtil.waitForCommand(1, 5);
            if (cmd == 1) {
                SendMessage.write(socket, new SocketModel(Api.TYPE_CHANGE_PROF, username,jwt), writer);
            } else if (cmd == 2) {
                ConsoleUtil.printCommandHint("2. Enter the word to search");
                String str = ConsoleUtil.waitForString();
                SendMessage.write(socket, new SocketModel(Api.TYPE_USER_SEARCH, str,jwt), writer);
            } else if (cmd == 3) {
                requestTimeLine(socket,writer,jwt);
            } else if (cmd == 4) {
                addTweetForm(socket,writer,jwt);
            } else if (cmd == 5) {
                
            }
//        }


//        new Thread(() -> {
//            String msg;
//            while (!"exit".equalsIgnoreCase(msg = ConsoleUtil.waitForString())) {
//                SocketApi.getInstance().write(
//                        new SocketModel(Api.TYPE_MESSAGE, msg)
//                );
//            }
//            System.exit(0);
//        }).start();
    }

    public synchronized static void showProf(Socket socket, User user, ObjectOutputStream writer , String jwt) {
        System.out.println("\u001B[" + 37 + "m" + user.getFirstName() + " " + user.getLastName() + " birth date: " + user.getBirthDate() + " username: " + user.getUsername() + "\u001B[0m");
        System.out.println("\u001B[" + 40 + "m" + " avatar: " + user.getAvatar() + " header: " + user.getHeader() + " bio: " + user.getBio()
                + " location: " + user.getLocation() + " website: " + user.getWebsite() + "\u001B[0m");
        System.out.println("\u001B[" + 41 + "m" + " email: " + user.getEmail()
                + user.getPhoneNumber() + user.getRegionOrCountry() + "\u001B[0m");
        System.out.println("\u001B[" + 33 + "m" + " followers : " + user.getFollowers().size() + " followings : " + user.getFollowings().size() + "\u001B[0m");
        ConsoleUtil.printCommandHint("1. change first name");
        ConsoleUtil.printCommandHint("2. change last name");
        ConsoleUtil.printCommandHint("3. change birthdate");
        ConsoleUtil.printCommandHint("4. change username");
        ConsoleUtil.printCommandHint("5. change password");
        ConsoleUtil.printCommandHint("6. change avatar");
        ConsoleUtil.printCommandHint("7. change header");
        ConsoleUtil.printCommandHint("8. change bio");
        ConsoleUtil.printCommandHint("9. change location");
        ConsoleUtil.printCommandHint("10. change website");
        ConsoleUtil.printCommandHint("11. change email");
        ConsoleUtil.printCommandHint("12. change phone number");
        ConsoleUtil.printCommandHint("13. change country");
        ConsoleUtil.printCommandHint("14. show followers");
        ConsoleUtil.printCommandHint("15. show followings");
        ConsoleUtil.printCommandHint("16. exit");

        boolean isChanged = false;
        int cmd = ConsoleUtil.waitForCommand(1, 16);
        while (cmd != 16) {
            if (cmd == 1) {
                ConsoleUtil.printCommandHint("enter first name:");
                String str = ConsoleUtil.waitForString();
                user.setFirstName(str);
                isChanged = true;
            } else if (cmd == 2) {
                ConsoleUtil.printCommandHint("enter last name:");
                String str = ConsoleUtil.waitForString();
                user.setLastName(str);
                isChanged = true;
            } else if (cmd == 3) {
                ConsoleUtil.printCommandHint("enter birthdate:");
                String str = ConsoleUtil.waitForString();
                while (Validate.validateDateFormat(str) != ResponseOrErrorType.SUCCESSFUL) {
                    ConsoleUtil.printCommandHint("Invalid date format: ");
                    str = ConsoleUtil.waitForString();
                }
                user.setBirthDate(new Date(str));
                isChanged = true;
            } else if (cmd == 4) {
                ConsoleUtil.printCommandHint("enter user name:");
                String str = ConsoleUtil.waitForString();
                user.setUsername(str);
                isChanged = true;
            } else if (cmd == 5) {
                ConsoleUtil.printCommandHint("enter your prev pass:");
                String str1 = ConsoleUtil.waitForString();
                while (!str1.equals(user.getPassword())) {
                    str1 = ConsoleUtil.waitForString();
                }
                ConsoleUtil.printCommandHint("enter your new pass:");
                String str = ConsoleUtil.waitForString();
                while (Validate.validPass(str) != ResponseOrErrorType.SUCCESSFUL) {
                    ConsoleUtil.printCommandHint("correct pass = 8chars in both upper and lower case: ");
                    str = ConsoleUtil.waitForString();
                }
                ConsoleUtil.printCommandHint("Repeat your password: ");
                String repeatPass = ConsoleUtil.waitForString();
                while (!str.equals(repeatPass)) {
                    ConsoleUtil.printCommandHint("Not the same: ");
                    repeatPass = ConsoleUtil.waitForString();
                }
                user.setPassword(str);
                isChanged = true;
            } else if (cmd == 6) {
                ConsoleUtil.printCommandHint("enter avatar path:");
                String str = ConsoleUtil.waitForString();
                user.setAvatar(str);
                isChanged = true;
            } else if (cmd == 7) {
                ConsoleUtil.printCommandHint("enter header path:");
                String str = ConsoleUtil.waitForString();
                user.setHeader(str);
                isChanged = true;
            } else if (cmd == 8) {
                ConsoleUtil.printCommandHint("enter bio:");
                String str = ConsoleUtil.waitForString();
                user.setBio(str);
                isChanged = true;
            } else if (cmd == 9) {
                ConsoleUtil.printCommandHint("enter location:");
                String str = ConsoleUtil.waitForString();
                user.setLocation(str);
                isChanged = true;
            } else if (cmd == 10) {
                ConsoleUtil.printCommandHint("enter website:");
                String str = ConsoleUtil.waitForString();
                ResponseOrErrorType isValidLink = Validate.validateWebsite(str);

                while (isValidLink == ResponseOrErrorType.INVALID_LINK) {
                    ConsoleUtil.printCommandHint("enter valid website:");
                    //TODO handle it
                    str = ConsoleUtil.waitForString();
                }
                user.setWebsite(str);
                isChanged = true;
            } else if (cmd == 11) {
                ConsoleUtil.printCommandHint("enter email:");
                String str = ConsoleUtil.waitForString();
                while (Validate.validateEmail(str) != ResponseOrErrorType.SUCCESSFUL) {
                    ConsoleUtil.printCommandHint("Invalid email: ");
                    str = ConsoleUtil.waitForString();
                }
                user.setEmail(str);
                isChanged = true;
            } else if (cmd == 12) {
                ConsoleUtil.printCommandHint("enter phone number:");
                String str = ConsoleUtil.waitForString();
                user.setPhoneNumber(str);
                isChanged = true;
            } else if (cmd == 13) {
                ConsoleUtil.printCommandHint("enter country choice:");
//            showCountryList();
//            int choice = ConsoleUtil.waitForCommand(1, 2);
//            user.setRegionOrCountry(str);
                isChanged = true;
            } else if (cmd == 14) {
                Iterator<String> iterator = user.getFollowers().iterator();
                /////////////////////////////////////////
                while (iterator.hasNext()) {

                }
                isChanged = true;
            } else if (cmd == 15) {
                //////////////////////////////////////
                isChanged = true;
            }
            cmd = ConsoleUtil.waitForCommand(1, 16);
        }
        if (isChanged){
            SendMessage.write(socket, new SocketModel(Api.TYPE_Update_PROF, user,jwt), writer);
        }

    }
    public synchronized static void showOthersProf(Socket socket, User user, ObjectOutputStream writer , String jwt) {
        if(user.doesBlock(getUsername(jwt))){
            ConsoleUtil.printCommandHint("You are blocked by this account so you can't view their profile.");
            ConsoleUtil.printCommandHint("Press Enter to go back to your timeLine");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            requestTimeLine(socket,writer,jwt);
            return;
        }
        System.out.println("\u001B[" + 37 + "m" + user.getFirstName() + " " + user.getLastName() + " username: " + user.getUsername() + "\u001B[0m");
        System.out.println("\u001B[" + 40 + "m" + " avatar: " + user.getAvatar() + " header: " + user.getHeader() + " bio: " + user.getBio()
                + " location: " + user.getLocation() + " website: " + user.getWebsite() + "\u001B[0m");
        System.out.println("\u001B[" + 33 + "m" + " followers : " + user.getFollowers().size() + " followings : " + user.getFollowings().size() + "\u001B[0m");
        System.out.println();
        ConsoleUtil.printCommandHint("What do you want to do?");
        if(!user.doesFollow(getUsername(jwt))) {
            ConsoleUtil.printCommandHint("1. Follow This Account");
        } else {
            ConsoleUtil.printCommandHint("1. Unfollow This Account");
        } if(!user.doesBlock(getUsername(jwt))){
            ConsoleUtil.printCommandHint("2. Block This Account");
        }else {
            ConsoleUtil.printCommandHint("2. Unblock This Account");
        }
        ConsoleUtil.printCommandHint("3. Return to your timeline");
        int command = ConsoleUtil.waitForInteger();
        if(command==1 && !user.doesFollow(jwt)){
            SendMessage.write(socket, new SocketModel(Api.TYPE_FOLLOW,user),writer);
        } else if(command==1 && user.doesFollow(jwt)){
            SendMessage.write(socket, new SocketModel(Api.TYPE_UNFOLLOW,user),writer);
        } else if(command==2 && !user.doesBlock(jwt)){
            SendMessage.write(socket, new SocketModel(Api.TYPE_BLOCK,user),writer);
        }else if(command==2 && user.doesBlock(jwt)){
            SendMessage.write(socket, new SocketModel(Api.TYPE_UNBLOCK,user),writer);
        } else if (command==3) {
            requestTimeLine(socket,writer,jwt);
            return;
        } else {
            ConsoleUtil.printCommandHint("Invalid Request!");
            System.out.println();
            showOthersProf(socket,user,writer,jwt);
            return;
        }
    }

    public synchronized static void showSearchWords(HashSet<String> words) {
        Iterator<String> iterator = words.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
    public synchronized static void addTweetForm(Socket socket, ObjectOutputStream writer, String jwt)  {
        String username = getUsername(jwt);
        ///TODO handle having enter in the text and tweet size limitations
        ConsoleUtil.printCommandHint("Type your tweet, Press ENTER when you're done.");
        Tweet tweet = new Tweet(username,ConsoleUtil.waitForString(),null);
        SendMessage.write(socket, new SocketModel(Api.TYPE_WRITING_TWEET, tweet,jwt), writer);
    }
    public synchronized static void requestTimeLine(Socket socket, ObjectOutputStream writer, String jwt){
        SendMessage.write(socket, new SocketModel(Api.TYPE_LOADING_TIMELINE,getUsername(jwt),jwt), writer);
    }
    public synchronized static void timeLinePage(Socket socket, ObjectOutputStream writer, ArrayList<Tweet> tweets , String jwt){
        String username = getUsername(jwt);
        ConsoleUtil.printTimeLine(tweets);
        ConsoleUtil.printCommandHint("Choose a tweet. (Type * to go back to the last page.)");
        String command = ConsoleUtil.waitForString();
        if(command.equals("*")){
            openChatPage(socket,writer,jwt);
        }else {
            tweetPage(socket, writer, tweets.get(Integer.parseInt(command)), username);
        }
    }
    public synchronized static void tweetPage(Socket socket, ObjectOutputStream writer, Tweet tweet ,String jwt){
        String username = getUsername(jwt);
        ConsoleUtil.printATweet(tweet);
        System.out.println();
        ConsoleUtil.printCommandHint("What do you want to do?");
        if(tweet.isLikedBy(username)){
            ConsoleUtil.printCommandHint("1. Unlike");
        } else if (!tweet.isLikedBy(username)) {
            ConsoleUtil.printCommandHint("1. Like");
        }
        if(tweet.isRetweetedBy(username)){
            ConsoleUtil.printCommandHint("2. Undo Retweet");
        } else if (!tweet.isRetweetedBy(username)) {
            ConsoleUtil.printCommandHint("2. Retweet");
        }
        ConsoleUtil.printCommandHint("3. Quote Tweet");
        ConsoleUtil.printCommandHint("4. Reply");
        ConsoleUtil.printCommandHint("5. View The Likes");
        ConsoleUtil.printCommandHint("6. View The Retweets");
        ConsoleUtil.printCommandHint("7. View The Quotes");
        ConsoleUtil.printCommandHint("8. View The Replies");
        ConsoleUtil.printCommandHint("9. View The Writer's account");
        ConsoleUtil.printCommandHint("10. Go back to TimeLine");
        int command = ConsoleUtil.waitForInteger();
        if(tweet.isLikedBy(username)&&command==1){
            SendMessage.write(socket, new SocketModel(Api.TYPE_UNLIKE,tweet),writer);
        } else if (!tweet.isLikedBy(username)&&command==1) {
            SendMessage.write(socket, new SocketModel(Api.TYPE_LIKE,tweet),writer);
        } else if (tweet.isRetweetedBy(username)&&command==2) {
            SendMessage.write(socket, new SocketModel(Api.TYPE_UNDO_RETWEET,tweet),writer);
        } else if (!tweet.isRetweetedBy(username)&&command==2) {
            SendMessage.write(socket,new SocketModel(Api.TYPE_RETWEET,tweet),writer);
        } else if (command==3) {
            ConsoleUtil.printCommandHint("Write your quote.");
            QuoteTweet quoteTweet = new QuoteTweet(username,ConsoleUtil.waitForString(),null,tweet);
            SendMessage.write(socket,new SocketModel(Api.TYPE_QUOTE_TWEET,quoteTweet),writer);
        } else if (command==4) {
            ConsoleUtil.printCommandHint("Write your reply.");
            Reply reply = new Reply(username,ConsoleUtil.waitForString(),null,tweet);
            SendMessage.write(socket,new SocketModel(Api.TYPE_REPLY,reply),writer);
        } else if (command==5) {
            System.out.println();
            int i = 1;
            for (String likerUsername:tweet.getLikes()) {
                System.out.println(i+"- "+likerUsername);
                i++;
            }
            System.out.println();
            tweetPage(socket,writer,tweet,jwt);
        } else if (command==6) {
            System.out.println();
            int i = 1;
            for (String retweeterUsername:tweet.getLikes()) {
                System.out.println(i+"- "+retweeterUsername);
                i++;
            }
            System.out.println();
            tweetPage(socket,writer,tweet,jwt);
        } else if (command==7) {
            System.out.println();
            int i = 1;
            for (QuoteTweet quoteTweet:tweet.getQuoteTweets()) {
                System.out.print(i+"- ");
                ConsoleUtil.printAQuote(quoteTweet);
                System.out.println();
                i++;
            }
            tweetPage(socket,writer,tweet,jwt);
        } else if (command==8) {
            System.out.println();
            int i = 1;
            for (Reply reply:tweet.getReplies()) {
                System.out.print(i+"- ");
                ConsoleUtil.printAReply(reply);
                System.out.println();
                i++;
            }
            tweetPage(socket,writer,tweet,jwt);
        } else if (command==9) {
            SendMessage.write(socket, new SocketModel(Api.TYPE_SHOW_OTHERS_PROFILE,username),writer);
            return;
        } else if (command==10) {
            requestTimeLine(socket,writer,jwt);
            return;
        } else {
            ConsoleUtil.printCommandHint("Invalid Request!");
            System.out.println();
            tweetPage(socket,writer,tweet,jwt);
            return;
        }
    }
    public synchronized static String getUsername(String jwToken){
        String[] parts = jwToken.split("\\.");
        JSONObject payload = null;
        try {
            payload = new JSONObject(decode(parts[1]));
            return payload.getString("username");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    private synchronized static String decode(String encodedString) {
        return new String(Base64.getUrlDecoder().decode(encodedString));
    }
//    public synchronized static void becomeOffline(Socket socket, ObjectOutputStream writer){
//        SendMessage.write(socket, new SocketModel(Api.TYPE_OFFLINE_REQUEST,username),writer);
//    }
}
