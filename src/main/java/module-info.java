module uet.arkanoid {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.base;

    opens uet.arkanoid to javafx.fxml;
    opens uet.arkanoid.Menu.StartMenu to javafx.fxml;
    opens uet.arkanoid.Menu.PauseMenu to javafx.fxml;
    opens uet.arkanoid.Menu.LoadGame to javafx.fxml;
    opens uet.arkanoid.Menu.HighScore to javafx.fxml;
    exports uet.arkanoid;
    exports uet.arkanoid.Menu.StartMenu;
    exports uet.arkanoid.Menu.PauseMenu;
    exports uet.arkanoid.Menu.LoadGame;
    exports uet.arkanoid.Menu.HighScore;



}
