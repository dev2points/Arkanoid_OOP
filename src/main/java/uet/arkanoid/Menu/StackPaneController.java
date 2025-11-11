package uet.arkanoid.Menu;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

import uet.arkanoid.Gameconfig;
import uet.arkanoid.MenuManager;

public class StackPaneController {
    private MenuManager menuManager;

    @FXML
    private StackPane Sroot;

    @FXML
    public void initialize() {
        Sroot.setPrefWidth(Gameconfig.screen_width);
        Sroot.setPrefHeight(Gameconfig.screen_height);
        menuManager = new MenuManager(Sroot);
        menuManager.displayStartMenu();
    }



}