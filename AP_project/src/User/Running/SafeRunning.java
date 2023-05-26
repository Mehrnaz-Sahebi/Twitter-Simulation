package User.Running;

import java.io.IOException;
import java.sql.SQLException;

public class SafeRunning {
    public static interface SafeRun{
        void run() throws SQLException, IOException;
    }
    public static boolean safe(SafeRun runnable){
        try {
            runnable.run();
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public interface TypeSafeRunnable<T> {
        T run() throws Exception;
    }
    public static <T> T safe(TypeSafeRunnable<T> runnable, T defaultValue) {
        try {
            return runnable.run();
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }
}
