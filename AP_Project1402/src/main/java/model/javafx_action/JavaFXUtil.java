package model.javafx_action;

import model.common.SocketModel;

public class JavaFXUtil {
    public static String getErrorMSg(SocketModel socketuser) {
        switch (socketuser.message){
            case ALREADY_ONLINE -> {
                return " This account is already online!";
            }
            case INVALID_PASS -> {
                return "Invalid pass";
            }
            case DUPLICATE_USERNAME -> {
                return " duplicate username";
            }
            case DUPLICATE_EMAIL -> {
                return " duplicate email";
            }
            case DUPLICATE_PHONENUMBER -> {
                return " duplicate phone number";
            }
            case UNSUCCESSFUL -> {
                return " Unsuccessful because of different DB probs";
            }
            case INVALID_JWT -> {
                return "Not You? Try signing in again.";
            }
            case USER_NOTFOUND -> {
                return "User not found";
            }
//            case USER_NOTFOUND -> {
//                printColored(36, " User not found"); //cyan color
//            }
//            case USER_NOTFOUND -> {
//                printColored(36, " User not found"); //cyan color
//            }
        }
        return null;
    }
}
