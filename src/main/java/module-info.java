module uet.arkanoid {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens uet.arkanoid to javafx.fxml;
    exports uet.arkanoid;
}
