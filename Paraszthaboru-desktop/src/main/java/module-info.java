module hu.desktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.example;

    opens hu.desktop to javafx.fxml;
    exports hu.desktop;

    opens hu.desktop.controller to javafx.fxml;
    exports hu.desktop.controller;
}