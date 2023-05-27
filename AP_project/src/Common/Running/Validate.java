package Common.Running;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public static boolean validateDateFormat(String dateToValidate) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        //To make strict date format validation
        formatter.setLenient(false);
        try {
            formatter.parse(dateToValidate);
//            System.out.println("++validated DATE TIME ++");
            return true;
        } catch (ParseException e) {
            System.out.println("Invalid Format");
            return false;
        }
    }
    public  static boolean validateEmail(String email){
        if (email == null || email.isEmpty()){
            return false;
        }
        String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern emailPat = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPat.matcher(email);
        return matcher.find();
    }
}
