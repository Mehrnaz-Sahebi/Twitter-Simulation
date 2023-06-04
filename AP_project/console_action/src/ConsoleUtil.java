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

    public static void printHello(UserToBeSigned user) {
//        printColored(33, "Hello " + user.getFirstName() + user.getLastName() );
        printColored(33, "Your Email: " + user.getEmail());
        printColored(33, "WELCOME TO CHAT!");
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
}
