import java.net.Socket;

public interface DesignFlow {

    void failedToConnect();

    void openAccountMenu(Socket socket);

    void openLoginForm(Socket socket);

    void openSignupForm(Socket socket);

    void openChatPage(UserToBeSigned user);
}
