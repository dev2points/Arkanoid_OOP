module uet.arkanoid {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.base;

    opens uet.arkanoid to javafx.fxml;
<<<<<<< Updated upstream
=======
    opens uet.arkanoid.Menu.StartMenu to javafx.fxml;
>>>>>>> Stashed changes

    exports uet.arkanoid;
    exports uet.arkanoid.Menu.StartMenu;
}
