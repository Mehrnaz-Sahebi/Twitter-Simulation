package controller;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.client.ListenerForFX;
import model.common.Tweet;
import model.common.User;
import model.common.UserToBeSigned;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TwitterApplication extends Application {
    static Scene backScene = null;
    private static User thisuser = null;
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
        welcomePage(stage,socket,writer, null);
    }
    public static WelcomeController welcomePage(Stage stage, Socket socket, ObjectOutputStream writer, String jwt){
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
        return controller;
    }
    public static LogInController signInPage(Stage stage, Socket socket , ObjectOutputStream writer, String jwt){

//        FXMLLoader fxmlLoader = new FXMLLoader(TwitterApplication.class.getResource("login.fxml"));
//        try {
//            fxmlLoader.load();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        SignUpController controller = fxmlLoader.getController();
//        controller.setSocket(socket);
//        controller.setJwt(jwt);
//        controller.setWriter(writer);
//        controller.setSignUpController(controller);
        LogInController controller2 = Util.changeScene(stage, "login.fxml", "Log in to Twitter");
        controller2.setSocket(socket);
        controller2.setJwt(jwt);
        controller2.setWriter(writer);
        controller2.setLogInController(controller2);
        return controller2;
    }
    public static SignUpController signUpPage(Stage stage , Socket socket , ObjectOutputStream writer, String jwt){
        SignUpController controller = Util.changeScene(stage, "SignUp.fxml", "Sign Up to Twitter");
        controller.setSocket(socket);
        controller.setJwt(jwt);
        controller.setWriter(writer);
        controller.setSignUpController(controller);
        return controller;
    }
    public static HomePageController homePage(Stage stage , Socket socket , ObjectOutputStream writer, String jwt, ArrayList<Tweet> timeline){
        HomePageController controller = Util.changeScene(stage, "HomePage.fxml", "Home");
        controller.setSocket(socket);
        controller.setJwt(jwt);
        controller.setWriter(writer);
        controller.setHomePageController(controller);
        controller.setUser(thisuser);
        controller.start(timeline );
        return controller;
    }
    public static AddTweetController addTweet(Stage stage ,Socket socket , ObjectOutputStream writer, String jwt){
        AddTweetController controller = Util.changeScene(stage, "AddTweet.fxml", "Write a new tweet");
        controller.setSocket(socket);
        controller.setJwt(jwt);
        controller.setWriter(writer);
        return controller;
    }
    public static AddReplyController addReply(Stage stage ,Socket socket , ObjectOutputStream writer, String jwt , Tweet tweet){
        AddReplyController controller = Util.changeScene(stage, "AddReply.fxml", "Reply");
        controller.setSocket(socket);
        controller.setJwt(jwt);
        controller.setWriter(writer);
        controller.start(tweet);
        return controller;
    }
    public static AddQuoteController addQuote(Stage stage ,Socket socket , ObjectOutputStream writer, String jwt , Tweet tweet){
        AddQuoteController controller = Util.changeScene(stage, "AddQuote.fxml", "Quote");
        controller.setSocket(socket);
        controller.setJwt(jwt);
        controller.setWriter(writer);
        controller.start(tweet);
        return controller;
    }
    public static UsersProfileController profPage(Stage stage ,Socket socket , ObjectOutputStream writer, String jwt, User user){
        FXMLLoader fxmlLoader = new FXMLLoader(TwitterApplication.class.getResource("UsersProfile.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        UsersProfileController controller = fxmlLoader.getController();
        controller.setSocket(socket);
        controller.setJwt(jwt);
        controller.setWriter(writer);
        controller.setUserProfController(controller);
        controller.setUser(user);
        controller.prepareProf();
        thisuser = user;
        Scene scene = null;
        scene = new Scene(root);
        stage.setScene(scene);
        return controller;
    }
    public static ProfileOfOthersController profOthersPage(String back, Stage stage ,Socket socket , ObjectOutputStream writer, String jwt, User user, String usernameThis){
        FXMLLoader fxmlLoader = new FXMLLoader(TwitterApplication.class.getResource("OthersProfile.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ProfileOfOthersController controller = fxmlLoader.getController();
        controller.setSocket(socket);
        controller.setJwt(jwt);
        controller.setWriter(writer);
        controller.setUserProfController(controller);
        controller.setUser(user);
        controller.setUsernameOfThisUser(usernameThis);
        controller.setBackPage(back);
        controller.prepareProf();
        Scene scene = null;
        scene = new Scene(root);
        stage.setScene(scene);
        return controller;
    }
    public static EditProfController editProfPage(Stage stage ,Socket socket , ObjectOutputStream writer, String jwt, User user){
        FXMLLoader fxmlLoader = new FXMLLoader(TwitterApplication.class.getResource("EditProfPage.fxml"));
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        EditProfController controller = fxmlLoader.getController();
        controller.setSocket(socket);
        controller.setJwt(jwt);
        controller.setWriter(writer);
        controller.setUserProfController(controller);
        controller.setUser(user);
        controller.prepareProf();
        backScene = stage.getScene();
        Scene scene = null;
        scene = new Scene(root);
        stage.setScene(scene);
        return controller;
    }
    public static void goBackProfilePage(Stage stage ,Socket socket , ObjectOutputStream writer, String jwt, User user){
        profPage(stage, socket, writer,jwt, user);
    }
    public static SearchController goSearchPage(Stage stage , Socket socket , ObjectOutputStream writer, String jwt, String username){
        FXMLLoader fxmlLoader = new FXMLLoader(TwitterApplication.class.getResource("SearchPage.fxml"));
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SearchController controller = fxmlLoader.getController();
        controller.setSocket(socket);
        controller.setJwt(jwt);
        controller.setWriter(writer);
        controller.setSerchController(controller);
        controller.setUser(username);
        backScene = stage.getScene();
        Scene scene = null;
        scene = new Scene(root);
        stage.setScene(scene);
        return controller;
    }
    public static void goBack(String goingPage, Stage stage, Socket socket , ObjectOutputStream writer, String jwt, String username){
        if (goingPage.equals("Prof")){
            goBackProfilePage(stage, socket, writer, jwt, thisuser);
        } else if (goingPage.equals("Search")) {
            goSearchPage(stage, socket, writer, jwt, username);
        }
//        else if (goingPage.equals("Other")) {
//            profOthersPage(
//        }
    }
    public static void setThisUser(User newUser){
        thisuser = newUser;
    }
}
