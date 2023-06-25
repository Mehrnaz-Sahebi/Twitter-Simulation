module com.example.ap_project1402 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.json;


    opens controller to javafx.fxml;
    exports controller;
}