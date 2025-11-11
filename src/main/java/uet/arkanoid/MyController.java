// package uet.arkanoid;

// import javafx.fxml.FXML;
// import javafx.scene.image.Image;
// import javafx.scene.layout.Pane;
// import javafx.scene.paint.ImagePattern;
// import javafx.scene.shape.Rectangle;

// public class MyController {

// @FXML
// private Pane root; // or AnchorPane, StackPane, etc.

// public void updateAllClass1Images() {
// // Load your image
// Image image = new
// Image(getClass().getResource("brick_1.png").toExternalForm());
// ImagePattern pattern = new ImagePattern(image);

// // Find all nodes with the style class "class1"
// root.lookupAll(".brick1").forEach(node -> {
// if (node instanceof Rectangle rect) {
// rect.setFill(pattern);
// }
// });
// }
// }
