import java.util.Hashtable;

public class OnlineUsers {

    private static final Hashtable<String, ClientHandler> onlineUsers = new Hashtable<>();

    public static synchronized boolean isOnline(UserToBeSigned userModel) {
        return onlineUsers.containsKey(userModel.getUsername());
    }

    public static synchronized void addOnlineUser(ClientHandler user) {
        onlineUsers.put(user.getUserModel().getUsername(), user);
        notify(user, new SocketModel(Api.TYPE_USER_JOINED, user.getUserModel()));
    }

    public static synchronized void removeOnlineUser(ClientHandler user) {
        onlineUsers.remove(user.getUserModel().getUsername());
        notify(user, new SocketModel(Api.TYPE_USER_LEFT, user.getUserModel()));
    }

    public static synchronized void notify(ClientHandler sender, SocketModel model) {
        for (ClientHandler user : onlineUsers.values()) {
            if (user != sender) {
                user.write(model);
            }
        }
    }
}
