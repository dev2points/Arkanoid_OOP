package uet.arkanoid;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadMapFile {

    public static List<Brick> readMapFXML(int level, Pane pane) {
        List<Brick> bricks = new ArrayList<>();

        try {
            String fxmlPath = "/assets/maps/map" + level + ".fxml";

            // Load fxml
            FXMLLoader loader = new FXMLLoader(ReadMapFile.class.getResource(fxmlPath));
            Pane mapPane = loader.load();

            // Lấy list node trong fxml
            List<Node> nodes = new ArrayList<>(mapPane.getChildren());

            // Duyệt qua các node trong file FXML
            for (Node node : nodes) {
                if (node instanceof Rectangle rect) {
                    double x = rect.getLayoutX();
                    double y = rect.getLayoutY();
                    double width = rect.getWidth();
                    double height = rect.getHeight();

                    // Lấy type_brick
                    int type = 1;
                    for (String style : rect.getStyleClass()) {
                        if (style.startsWith("brick")) {
                            try {
                                type = Integer.parseInt(style.substring(5));
                            } catch (NumberFormatException ignored) {
                                System.out.println("Styleclass brick sai");
                            }
                            break;
                        }
                    }
                    System.out.println(type);

                    // Tạo brick
                    Brick brick = new Brick(x, y, width, height, pane, type);
                    bricks.add(brick);
                    // rect.setVisible(false);
                }
            }

        } catch (IOException e) {
            System.err.println("Lỗi khi đọc file map FXML: " + e.getMessage());
            e.printStackTrace();
        }

        return bricks;
    }
}
