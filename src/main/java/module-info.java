module uet.arkanoid {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.base;

    opens uet.arkanoid to javafx.fxml;
    opens uet.arkanoid.Menu.StartMenu to javafx.fxml;
    opens uet.arkanoid.Menu.PauseMenu to javafx.fxml;
    exports uet.arkanoid;
    exports uet.arkanoid.Menu.StartMenu;
    exports uet.arkanoid.Menu.PauseMenu;


}
