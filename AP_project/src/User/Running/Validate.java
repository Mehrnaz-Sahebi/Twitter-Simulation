package User.Running;

public class Validate {

    public static boolean NotBlank(String... args) {
        for (String arg : args) {
            if (arg == null || arg.isBlank()) {
                return false;
            }
        }
        return true;
    }
}
