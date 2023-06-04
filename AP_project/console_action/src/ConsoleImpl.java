import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

public class ConsoleImpl{

    public static void failedToConnect() {
        System.err.println("Failed to connect!");
    }

    public static void openAccountMenu(Socket socket, ObjectOutputStream writer) throws ParseException {
        ConsoleUtil.printCommandHint("1. Login");
        ConsoleUtil.printCommandHint("2. Signup");

        int cmd = ConsoleUtil.waitForCommand(1, 2);
        if (cmd == 1) {
            openLoginForm(socket, writer);
        } else {
            openSignupForm(socket, writer);
        }
    }

    public static void openLoginForm(Socket socket, ObjectOutputStream writer) {
        UserToBeSigned user = new UserToBeSigned();

        ConsoleUtil.printCommandHint("Enter username: ");
        user.setUsername(ConsoleUtil.waitForString());

        ConsoleUtil.printCommandHint("Enter password: ");
        user.setPassword(ConsoleUtil.waitForString());

//        return new SocketModel(Api.TYPE_SIGNIN, user);
        SendMessage.write(socket, new SocketModel(Api.TYPE_SIGNIN, user), writer);

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

    public static void openSignupForm(Socket socket, ObjectOutputStream writer) throws ParseException {
        ConsoleUtil.printCommandHint("Creating new account...");

        UserToBeSigned user = new UserToBeSigned();

        ConsoleUtil.printCommandHint("Enter username: ");
        user.setUsername(ConsoleUtil.waitForString());

        ConsoleUtil.printCommandHint("Enter password: ");
        String pass = ConsoleUtil.waitForString();
        while (Validate.validPass(pass) != ResponseOrErrorType.SUCCESSFUL){
            ConsoleUtil.printCommandHint("correct pass = 8chars in both upper and lower case: ");
            pass = ConsoleUtil.waitForString();
        }


        ConsoleUtil.printCommandHint("Repeat your password: ");
        String repeatPass = ConsoleUtil.waitForString();
        while (!pass.equals(repeatPass)){
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
        while (Validate.validateEmail(email) != ResponseOrErrorType.SUCCESSFUL){
            ConsoleUtil.printCommandHint("Invalid email: ");
            email = ConsoleUtil.waitForString();
        }
        user.setEmail(email);

        ConsoleUtil.printCommandHint("Enter your phone number : ");
        String phNumber = ConsoleUtil.waitForString();
        user.setPhoneNumber(phNumber);

        ConsoleUtil.printCommandHint("Enter your birthDate: ");
        String birthDate = ConsoleUtil.waitForString();
        while (Validate.validateDateFormat(birthDate) != ResponseOrErrorType.SUCCESSFUL){
            ConsoleUtil.printCommandHint("Invalid date format: ");
            birthDate = ConsoleUtil.waitForString();
        }
        Date date = new SimpleDateFormat("yyyy/MM/dd").parse(birthDate);//casting the str to Date class
        user.setBirthDate( date);
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
         ){
            //TODO send response that enter at least one of these items
        }
//        return new SocketModel(Api.TYPE_SIGNUP, user);
        SendMessage.write(socket, new SocketModel(Api.TYPE_SIGNUP, user), writer);
    }


    public static void openChatPage(Socket socket, UserToBeSigned user, ObjectOutputStream writer) {
        ConsoleUtil.printHello(user);

        int cmd = 0;
        while (cmd != 5){
            ConsoleUtil.printCommandHint("1. Profile");
            ConsoleUtil.printCommandHint("2. Search");
            ConsoleUtil.printCommandHint("3. TimeLine");
            ConsoleUtil.printCommandHint("4. Add tweet");
            ConsoleUtil.printCommandHint("5. enter 5 in order to exit for chat");
            cmd = ConsoleUtil.waitForCommand(1, 5);
            if (cmd == 1) {
                SendMessage.write(socket, new SocketModel(Api.TYPE_CHANGE_PROF, user), writer);
            } else if (cmd == 2) {
                ConsoleUtil.printCommandHint("2. Enter the word to search");
                String str = ConsoleUtil.waitForString();
                user.setSearchingWord(str);
                SendMessage.write(socket, new SocketModel(Api.TYPE_USER_SEARCH, user), writer);
            } else if (cmd == 3) {
                SendMessage.write(socket, new SocketModel(Api.TYPE_LOADING_TIMELINE, user), writer);/////////////
            } else if(cmd == 4){
                SendMessage.write(socket, new SocketModel(Api.TYPE_ADD_TWEET, user), writer);/////////////////
            }
        }


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
    public static void showProf(Socket socket, User user,ObjectOutputStream writer){
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

        int cmd = ConsoleUtil.waitForCommand(1, 16);
        while (cmd != 16){
            if (cmd == 1) {
                ConsoleUtil.printCommandHint("enter first name:");
                String str = ConsoleUtil.waitForString();
                user.setFirstName(str);
            } else if (cmd == 2) {
                ConsoleUtil.printCommandHint("enter last name:");
                String str = ConsoleUtil.waitForString();
                user.setLastName(str);
            } else if (cmd == 3) {
                ConsoleUtil.printCommandHint("enter birthdate:");
                String str = ConsoleUtil.waitForString();
                while (Validate.validateDateFormat(str) != ResponseOrErrorType.SUCCESSFUL){
                    ConsoleUtil.printCommandHint("Invalid date format: ");
                    str = ConsoleUtil.waitForString();
                }
                user.setBirthDate(new Date(str));
            } else if (cmd == 4) {
                ConsoleUtil.printCommandHint("enter user name:");
                String str = ConsoleUtil.waitForString();
                user.setUsername(str);
            } else if (cmd == 5) {
                ConsoleUtil.printCommandHint("enter your prev pass:");
                String str1 = ConsoleUtil.waitForString();
                while (!str1.equals(user.getPassword())){
                    str1 = ConsoleUtil.waitForString();
                }
                ConsoleUtil.printCommandHint("enter your new pass:");
                String str = ConsoleUtil.waitForString();
                while (Validate.validPass(str) != ResponseOrErrorType.SUCCESSFUL){
                    ConsoleUtil.printCommandHint("correct pass = 8chars in both upper and lower case: ");
                    str = ConsoleUtil.waitForString();
                }
                ConsoleUtil.printCommandHint("Repeat your password: ");
                String repeatPass = ConsoleUtil.waitForString();
                while (!str.equals(repeatPass)){
                    ConsoleUtil.printCommandHint("Not the same: ");
                    repeatPass = ConsoleUtil.waitForString();
                }
                user.setPassword(str);
            } else if (cmd == 6) {
                ConsoleUtil.printCommandHint("enter avatar path:");
                String str = ConsoleUtil.waitForString();
                user.setAvatar(str);
            } else if (cmd == 7) {
                ConsoleUtil.printCommandHint("enter header path:");
                String str = ConsoleUtil.waitForString();
                user.setHeader(str);
            } else if (cmd == 8) {
                ConsoleUtil.printCommandHint("enter bio:");
                String str = ConsoleUtil.waitForString();
                user.setBio(str);
            } else if (cmd == 9) {
                ConsoleUtil.printCommandHint("enter location:");
                String str = ConsoleUtil.waitForString();
                user.setLocation(str);
            } else if (cmd == 10) {
                ConsoleUtil.printCommandHint("enter website:");
                String str = ConsoleUtil.waitForString();
                ResponseOrErrorType isValidLink = Validate.validateWebsite(str);

        while (isValidLink == ResponseOrErrorType.INVALID_LINK){
            ConsoleUtil.printCommandHint("enter valid website:");
            //TODO handle it
            str = ConsoleUtil.waitForString();
        }
                user.setWebsite(str);
            } else if (cmd == 11) {
                ConsoleUtil.printCommandHint("enter email:");
                String str = ConsoleUtil.waitForString();
                while (Validate.validateEmail(str) != ResponseOrErrorType.SUCCESSFUL){
                    ConsoleUtil.printCommandHint("Invalid email: ");
                    str = ConsoleUtil.waitForString();
                }
                user.setEmail(str);
            } else if (cmd == 12) {
                ConsoleUtil.printCommandHint("enter phone number:");
                String str = ConsoleUtil.waitForString();
                user.setPhoneNumber(str);
            } else if (cmd == 13) {
                ConsoleUtil.printCommandHint("enter country choice:");
//            showCountryList();
//            int choice = ConsoleUtil.waitForCommand(1, 2);
//            user.setRegionOrCountry(str);
            } else if (cmd == 14) {
                Iterator<String> iterator = user.getFollowers().iterator();
                /////////////////////////////////////////
                while (iterator.hasNext()){

                }
            } else if (cmd == 15) {
                //////////////////////////////////////
            }
            cmd = ConsoleUtil.waitForCommand(1, 16);
        }
        SendMessage.write(socket, new SocketModel(Api.TYPE_Update_PROF, user), writer);

    }
public static void showSearchWords(HashSet<String> words){
        Iterator<String> iterator = words.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
}
}
