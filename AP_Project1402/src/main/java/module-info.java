module com.example.ap_project1402 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.json;
    requires java.desktop;


    opens controller to javafx.fxml;
    opens model.common to javafx.fxml;
//table view error

    exports controller;
    exports model.common;

}