package controller;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.client.ListenerForFX;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TwitterApplication extends Application {
    public TwitterApplication() {

    }

    @Override
    public void start(Stage stage) {
        ListenerForFX listeningUser = null;
        Socket socket = null;
        ObjectOutputStream writer = null;
        ObjectInputStream reader = null;
        try {
            socket = new Socket("127.0.0.1", 8080);
            writer = new ObjectOutputStream(socket.getOutputStream());
            reader = new ObjectInputStream(socket.getInputStream());
            listeningUser = new ListenerForFX(socket,reader,writer,stage);
            ExecutorService executorService = Executors.newCachedThreadPool();
            executorService.execute(listeningUser);

        } catch (IOException e) {
            System.out.println("Server isn't available");
        }
        FXMLLoader fxmlLoader = new FXMLLoader(TwitterApplication.class.getResource("welcome.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the fxml file");
        }
        WelcomeController controller = fxmlLoader.getController();
        controller.setSocket(socket);
        controller.setJwt(null);
        controller.setWriter(writer);
        stage.setTitle("Twitter");
        stage.setScene(scene);
        stage.show();
    }
    public static LogInController signInPage(Stage stage, Socket socket , ObjectOutputStream writer, String jwt){

        FXMLLoader fxmlLoader = new FXMLLoader(TwitterApplication.class.getResource("SignUp.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SignUpController controller = fxmlLoader.getController();
        controller.setSocket(socket);
        controller.setJwt(jwt);
        controller.setWriter(writer);
        controller.setSignUpController(controller);
        //        FXMLLoader fxmlLoader = new FXMLLoader(TwitterApplication.class.getResource("login.fxml"));
//        Scene scene = null;
//        try {
//            scene = new Scene(fxmlLoader.load());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        LogInController controller2 = Util.changeScene(stage, "login.fxml", "Sign Up to Twitter", null);
        controller2.setSocket(socket);
        controller2.setJwt(jwt);
        controller2.setWriter(writer);
        controller2.setLogInController(controller2);
//        stage.setScene(scene);
//        stage.show();
        return controller2;
    }
    public static SignUpController signUpPage(Stage stage , Socket socket , ObjectOutputStream writer, String jwt){

//        FXMLLoader fxmlLoader = new FXMLLoader(TwitterApplication.class.getResource("SignUp.fxml"));
//        Scene scene = null;
//        try {
//            scene = new Scene(fxmlLoader.load());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        SignUpController controller = Util.changeScene(stage, "SignUp.fxml", "Sign Up to Twitter", null);
        controller.setSocket(socket);
        controller.setJwt(jwt);
        controller.setWriter(writer);
        controller.setSignUpController(controller);
//        stage.setScene(scene);
//        stage.show();
        return controller;
    }
    public static void firstPage(Stage stage ,Socket socket , ObjectOutputStream writer, String jwt){

    }
}
