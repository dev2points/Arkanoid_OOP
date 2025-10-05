package uet.arkanoid;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BaseObject {

    protected double x, y, width, height;
    protected Node view;

    public BaseObject(double x, double y, double width, double height, Pane pane) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        Rectangle rect = new Rectangle(width, height, Color.BLUE);
        rect.setLayoutX(x);
        rect.setLayoutY(y);

        this.view = rect;
        pane.getChildren().add(view);
    }

    public void removeFromPane(Pane pane) {
        pane.getChildren().remove(view);
    }

    public void destroy(Pane pane) {
        removeFromPane(pane);
        view = null;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
        if (view != null) {
            view.setLayoutX(x);
            view.setLayoutY(y);
        }
    }

    public Node getView() {
        return view;
    }

    public void setView(Node view) {
        this.view = view;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
        if (view != null)
            view.setLayoutX(x);
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        if (view != null)
            view.setLayoutY(y);
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
        if (view instanceof Rectangle)
            ((Rectangle) view).setWidth(width);
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
        if (view instanceof Rectangle)
            ((Rectangle) view).setHeight(height);
    }

    public void update(Pane pane) {
    }

    public void loadImage(String imagePath, Pane pane) {
        Image img = new Image("file:" + imagePath);
        ImageView imageView = new ImageView(img);

        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);

        if (view != null)
            pane.getChildren().remove(view);

        view = imageView;
        pane.getChildren().add(view);
    }
}
