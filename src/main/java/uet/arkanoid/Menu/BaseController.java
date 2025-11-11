package uet.arkanoid.Menu;

import uet.arkanoid.MenuManager;

public abstract class BaseController {
    protected MenuManager menumanager;

    public MenuManager getMenumanager() {
        return this.menumanager;
    }

    public void setMenumanager(MenuManager menumanager) {
        this.menumanager = menumanager;
    }
    
}
