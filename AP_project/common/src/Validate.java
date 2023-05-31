import com.mysql.cj.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {

    public static boolean NotBlank(String... args) {
        for (String arg : args) {
            if (arg == null || arg.isBlank()) {
                return false;
            }
        }
        return true;
    }
    public static boolean dateNotBlank(Date date){
        if(date == null ){
            return false;
        }
        return true;
    }
    public static ResponseOrErrorType validateDateFormat(String dateToValidate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        //To make strict date format validation
        formatter.setLenient(false);
        try {
            formatter.parse(dateToValidate);
            return ResponseOrErrorType.SUCCESSFUL;
        } catch (ParseException e) {
            return ResponseOrErrorType.INVALID_DATEFORMAT;
        }
    }
    public  static ResponseOrErrorType validateEmail(String email){
        if (email == null || email.isEmpty())
            return ResponseOrErrorType.INVALID_EMAIL;
        String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern emailPat = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPat.matcher(email);
        if (matcher.find()){
            return ResponseOrErrorType.SUCCESSFUL;
        }else {
            return ResponseOrErrorType.INVALID_EMAIL;
        }
    }
    public static ResponseOrErrorType validPass(String pass){//pass should contain both small and capital letters
        return isMixed(pass);
    }
    public static ResponseOrErrorType isMixed(String s) {
        if (s.toLowerCase().equals(s)) {
            return ResponseOrErrorType.LOWERCASE;
        } else if (s.toUpperCase().equals(s)) {
            return ResponseOrErrorType.UPPERCASE;
        } else {
            return ResponseOrErrorType.SUCCESSFUL;
        }
    }
}
