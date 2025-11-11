package uet.arkanoid;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Pair;
import uet.arkanoid.Menu.BaseController;
import uet.arkanoid.Menu.DefeatController;
import uet.arkanoid.Menu.PauseMenuController;
import uet.arkanoid.Menu.VictoryController;

import java.io.IOException;
import java.util.Stack;


public class MenuManager {

    private final StackPane rootPane;
    private final Stack<Parent> menuStack;
    GameController gamecontroller;
    private boolean save_flag = false;
    Pair<GameMap, User> process;

    public MenuManager(StackPane rootPane) {
        this.rootPane = rootPane;
        this.menuStack = new Stack<>();
        pushMenu("start", "/assets/menu/fxml/start_menu.fxml");
    }

    public MenuManager(StackPane rootPane, GameController g) {
        this.rootPane = rootPane;
        this.menuStack = new Stack<>();
        pushMenu("start", "/assets/menu/fxml/start_menu.fxml");
        gamecontroller = g;
    }

    public void pushMenu(String name, String fxmlPath) {
        rootPane.setMouseTransparent(false);
        rootPane.setFocusTraversable(true);
        try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                Parent menu = loader.load();
                Object controller = loader.getController();
                if (controller instanceof BaseController) {
                    ((BaseController) controller).setMenumanager(this);
                }
                if (controller instanceof GameController) {
                    gamecontroller = loader.getController();
                    gamecontroller.setScene(rootPane.getScene());
                    gamecontroller.setMenumanager(this);
                    
                }
                if (controller instanceof PauseMenuController) {
                    ((PauseMenuController) controller).setGameController(gamecontroller);
                }
                if (controller instanceof VictoryController) {
                    ((VictoryController) controller).setScore(gamecontroller.getScore());
                }
                if (controller instanceof DefeatController) {
                    ((DefeatController) controller).setScore(5);
                }

            

            if (!menuStack.isEmpty()) {
                rootPane.getChildren().remove(menuStack.peek());
            }
            
            menuStack.push(menu);
            rootPane.getChildren().add(menu);
            if (save_flag) {
                System.out.println("im being load");
                save_flag = false;
                gamecontroller.loadProcess(process);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void popMenu() {
        if (menuStack.isEmpty()) return;

        Parent current = menuStack.pop();
        rootPane.getChildren().remove(current);

        if (!menuStack.isEmpty()) {
            rootPane.getChildren().add(menuStack.peek());
        }
    }

    public void clearMenus() {
        rootPane.getChildren().clear();
        menuStack.clear();
    }


    public Parent getCurrentMenu() {
        if (!menuStack.isEmpty()) {
            return menuStack.peek();
        }
        return null;
    }

    public void displayStartMenu() {
        
        pushMenu("start", "/assets/menu/fxml/start_menu.fxml");
    }

    public void displayOptionMenu() {
        pushMenu("option", "/assets/menu/fxml/option_menu.fxml");
    }

    public void displayPauseMenu() {
        pushMenu("pause", "/assets/menu/fxml/pause_menu.fxml");
    }

    public void displayLoadGame() {
        pushMenu("load", "/assets/menu/fxml/load_game.fxml");
    }

    public void displayHighScore() {
        pushMenu("highscore", "/assets/menu/fxml/high_score.fxml");
    }

    public void playNewGame() {
        clearMenus();
        pushMenu("game", "/assets/maps/main.fxml");
    }

    public void loadSavedGame(Pair<GameMap, User> process) {
        clearMenus();
        this.process = process;
        save_flag = true;
        pushMenu("game", "/assets/maps/main.fxml");
    }

    public void displayVictoryMenu() {
        clearMenus();
        pushMenu("game", "/assets/menu/fxml/victory_menu.fxml");
    }

    public void displayDefeatMenu() {
        clearMenus();
        pushMenu("game", "/assets/menu/fxml/defeat_menu.fxml");
    }


}
