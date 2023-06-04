import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
            ConsoleUtil.printCommandHint("correct pass = chars in both upper and lower case: ");
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

        SendMessage.write(socket, new SocketModel(Api.TYPE_SIGNUP, user), writer);
    }


//    public static void openChatPage(UserToBeSigned user) {
//        ConsoleUtil.printHello(user);
//
//        new Thread(() -> {
//            String msg;
//            while (!"exit".equalsIgnoreCase(msg = ConsoleUtil.waitForString())) {
////                SocketApi.getInstance().write(
////                        new SocketModel(Api.TYPE_MESSAGE, msg)
////                );
//            }
//            System.exit(0);
//        }).start();
//    }
}
