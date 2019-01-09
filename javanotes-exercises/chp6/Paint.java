import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Paint extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  private final Color[] palette = {
    Color.BLACK, Color.RED, Color.GREEN, Color.BLUE,
    Color.CYAN, Color.MAGENTA, Color.color(0.95,0.9,0)
  };
  private int currentColorNum = 0;
  private double prevX, prevY;
  private boolean dragging;
  private Canvas canvas;
  private GraphicsContext g;

  public void start(Stage stage) {
    canvas = new Canvas(1200, 800);
    g = canvas.getGraphicsContext2D();
    clearAndDrawPalette();

    canvas.setOnMousePressed(this::mousePressed);
    canvas.setOnMouseDragged(this::mouseDragged);
    canvas.setOnMouseReleased(this::mouseReleased);

    Pane root = new Pane(canvas);
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setResizable(false);
    stage.setTitle("Paint");
    stage.show();
  }

  public void clearAndDrawPalette() {
    int w = (int)canvas.getWidth();
    int h = (int)canvas.getHeight();

    g.setFill(Color.rgb(255,255,240));
    g.fillRect(0,0,w,h);

    int colorSpacing = (h - 106) / 7;

    g.setStroke(Color.GRAY);
    g.setLineWidth(3);
    g.strokeRect(1.5, 1.5, w-3, h-3);

    g.setFill(Color.GRAY);
    g.fillRect(w - 106, 0, 106, h);

    g.setFill(Color.rgb(255,255,240));
    g.fillRect(w - 103, h - 103, 100, 100);
    g.setFill(Color.BLACK);
    g.setFont(Font.font(22));
    g.fillText("CLEAR", w-86, h-45);

    for (int N = 0; N < 7; N++) {
      g.setFill(palette[N]);
      g.fillRect(w-103, 3 + N*colorSpacing, 100, colorSpacing-3);
    }

    g.setStroke(Color.WHITE);
    g.setLineWidth(2);
    g.strokeRect(w-104, 2 + currentColorNum*colorSpacing, 102, colorSpacing-1);
  }

  private void changeColor(int y) {
    int w = (int)canvas.getWidth();
    int h = (int)canvas.getHeight();
    int colorSpacing = (h - 106) / 7;
    int newColor = y / colorSpacing;

    if (newColor < 0 || newColor > 6) {
      return;
    }

    g.setLineWidth(2);
    g.setStroke(Color.GRAY);
    g.strokeRect(w - 104, 2+currentColorNum*colorSpacing, 102, colorSpacing-1);
    currentColorNum = newColor;
    g.setStroke(Color.WHITE);
    g.strokeRect(w-104, 2 + currentColorNum*colorSpacing, 102, colorSpacing-1);
  }

  public void mousePressed(MouseEvent e) {
    if (dragging == true) return;

    int x = (int)e.getX();
    int y = (int)e.getY();

    int w = (int)canvas.getWidth();
    int h = (int)canvas.getHeight();

    if (x > w - 103) {
      if (y > h - 103) {
        clearAndDrawPalette();
      } else {
        changeColor(y);
      }
    } else if (x > 3 && x < w - 106 && y > 3 && y < h - 3) {
      prevX = x;
      prevY = y;
      dragging = true;
      g.setLineWidth(2);
      g.setStroke(palette[currentColorNum]);
    }
  }

  public void mouseReleased(MouseEvent e) {
    dragging = false;
  }

  public void mouseDragged(MouseEvent e) {
    if (dragging == false) return;

    boolean draw = true;

    double x = e.getX();
    double y = e.getY();

    if (x < 3) {
      x = 3;
      draw = false;
    }

    if (x > canvas.getWidth() - 107) {
      x = canvas.getWidth() - 107;
      draw = false;
    }

    if (y < 3) {
      y = 3;
      draw = false;
    }

    if (y > canvas.getHeight() - 4) {
      y = canvas.getHeight() - 4;
      draw = false;
    }

    if (draw)
      g.strokeLine(prevX, prevY, x, y);

    prevX = x;
    prevY = y;
  }

}
