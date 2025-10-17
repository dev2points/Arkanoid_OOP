module uet.arkanoid {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens uet.arkanoid to javafx.fxml;
    exports uet.arkanoid;
}
