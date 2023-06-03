import java.util.Hashtable;

public class OnlineUsers {

    private static final Hashtable<String, ClientHandler> onlineUsers = new Hashtable<>();

    public static synchronized boolean isOnline(UserToBeSigned userModel) {
        return onlineUsers.containsKey(userModel.getUsername());
    }

    public static synchronized void addOnlineUser(ClientHandler clientuser, UserToBeSigned user) {
        onlineUsers.put(user.getUsername(), clientuser);
        notify(clientuser, new SocketModel(Api.TYPE_USER_JOINED, user));
    }

    public static synchronized void removeOnlineUser(ClientHandler clientuser, UserToBeSigned user) {
        onlineUsers.remove(user.getUsername());
        notify(clientuser, new SocketModel(Api.TYPE_USER_LEFT, user));
    }

    public static synchronized void notify(ClientHandler sender, SocketModel model) {
        for (ClientHandler user : onlineUsers.values()) {
            if (user != sender) {
                user.write(model);
            }
        }
    }
}
