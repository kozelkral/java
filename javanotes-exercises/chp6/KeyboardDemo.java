import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class KeyboardDemo extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  private static final int SQUARE_SIZE = 60;
  private Color squareColor = Color.RED;
  private boolean filled = true;
  private boolean border = false;
  private double squareTop = 170, squareLeft = 170;
  private Canvas canvas;

  public void start(Stage stage) {
    canvas = new Canvas(400, 400);
    draw();
    Pane root = new Pane(canvas);
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setTitle("Keyboard Demo");
    stage.setResizable(false);

    scene.setOnKeyPressed(this::keyPressed);
    scene.setOnKeyReleased(this::keyReleased);
    scene.setOnKeyTyped(this::keyTyped);

    stage.show();
  }

}
