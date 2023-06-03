import java.net.Socket;

public class ConsoleImpl implements DesignFlow {

    @Override
    public void failedToConnect() {
        System.err.println("Failed to connect!");
    }

    @Override
    public void openAccountMenu(Socket socket) {
        ConsoleUtil.printCommandHint("1. Login");
        ConsoleUtil.printCommandHint("2. Signup");

        int cmd = ConsoleUtil.waitForCommand(1, 2);
        if (cmd == 1) {
            openLoginForm(socket);
        } else {
            openSignupForm(socket);
        }
    }

    @Override
    public void openLoginForm(Socket socket) {
        UserToBeSigned user = new UserToBeSigned();

        ConsoleUtil.printCommandHint("Enter username: ");
        user.setUsername(ConsoleUtil.waitForString());

        ConsoleUtil.printCommandHint("Enter password: ");
        user.setPassword(ConsoleUtil.waitForString());

        SendMessage.write(socket, new SocketModel(Api.TYPE_SIGNIN, user));

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

    @Override
    public void openSignupForm(Socket socket) {
        ConsoleUtil.printCommandHint("Creating new account...");

        UserToBeSigned user = new UserToBeSigned();

        ConsoleUtil.printCommandHint("Enter username: ");
        user.setUsername(ConsoleUtil.waitForString());

        ConsoleUtil.printCommandHint("Enter password: ");
        user.setPassword(ConsoleUtil.waitForString());

        ConsoleUtil.printCommandHint("Enter your FIRST name: ");
        user.setFirstName(ConsoleUtil.waitForString());

        ConsoleUtil.printCommandHint("Enter your LAST name: ");
        user.setLastName(ConsoleUtil.waitForString());

        ConsoleUtil.printCommandHint("Enter your email: ");
        user.setEmail(ConsoleUtil.waitForString());

        SocketApi.getInstance().writeAndListen(
                new SocketModel(Api.TYPE_SIGNUP, user),
                model -> {
                    if (model.get()) {
                        openChatPage(user);
                    } else {
                        System.out.println(model.message);
                        openAccountMenu();
                    }
                }
        );
    }

    @Override
    public void openChatPage(UserToBeSigned user) {
        ConsoleUtil.printHello(user);

        new Thread(() -> {
            String msg;
            while (!"exit".equalsIgnoreCase(msg = ConsoleUtil.waitForString())) {
                SocketApi.getInstance().write(
                        new SocketModel(Api.TYPE_MESSAGE, msg)
                );
            }
            System.exit(0);
        }).start();
    }
}
