package uet.arkanoid;

import java.io.Serializable;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class BaseObject implements Serializable {

    protected double x, y, width, height;
    protected transient Node view;
    protected transient Pane pane; // Đánh dấu transient
    protected static double deltatime;

    // Constructor có Pane (dùng khi tạo mới)
    public BaseObject(double x, double y, double width, double height, Pane pane) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.pane = pane;
    }

    // Constructor không có Pane (dùng khi deserialized, rồi sau đó setPane)
    public BaseObject(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        // Pane sẽ được set sau khi đối tượng được load từ file
    }

    public void setRootPane(Pane _pane) {
        pane = _pane;
    }

    public Pane getRootPane() {
        return pane;
    }

    public static void setDeltatime(double _deltatime) {
        deltatime = _deltatime;
    }

    public void removeFromPane() { // Sửa tên hàm để dễ gọi hơn
        if (view != null && pane != null && pane.getChildren().contains(view)) {
            pane.getChildren().remove(view);
        }
    }

    public void destroy() {
        removeFromPane();
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
        else if (view instanceof ImageView)
            ((ImageView) view).setFitWidth(width);
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
        if (view instanceof Rectangle)
            ((Rectangle) view).setHeight(height);
        else if (view instanceof ImageView)
            ((ImageView) view).setFitHeight(height);
    }

    public void update() {
    }

    public void loadImage(String imagePath) { // Bỏ pane khỏi tham số
        java.net.URL resource = getClass().getResource(imagePath);

        if (resource == null) {
            System.err.println("Không tìm thấy ảnh: " + imagePath);
            return;
        }

        Image img = new Image(resource.toExternalForm());
        ImageView imageView = new ImageView(img);

        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);

        if (view != null) { // Nếu đã có view cũ, xóa đi
            if (pane != null && pane.getChildren().contains(view)) {
                pane.getChildren().remove(view);
            }
        }

        view = imageView;
        if (pane != null) { // Chỉ thêm vào pane nếu nó đã được set
            pane.getChildren().add(view);
        }
    }

    // Phương thức này sẽ được gọi khi deserialize để khôi phục các thành phần hiển
    // thị
    public void restoreView(Pane parentPane) {
        this.pane = parentPane;
        // Các lớp con sẽ override phương thức này để tự load ảnh hoặc tạo lại view của
        // chúng
    }
}