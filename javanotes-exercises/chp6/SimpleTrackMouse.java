import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class SimpleTrackMouse extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  private Canvas canvas;
  private StringBuilder eventInfo;

  public void start(Stage stage) {

    eventInfo = new StringBuilder();

    canvas = new Canvas(550, 400);
    Pane root = new Pane(canvas);
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setTitle("Mouse Event Info");
    stage.setResizable(false);

    GraphicsContext g = canvas.getGraphicsContext2D();
    g.setFont(Font.font(18));
    g.setFill(Color.WHITE);
    g.fillRect(0,0,550,400);
    g.setFill(Color.BLACK);
    g.fillText("WATING FOR FIRST MOUSE EVENT", 50, 50);

    scene.addEventFilter(MouseEvent.ANY, e -> mouseEventOnScene(e));

    canvas.setOnMousePressed( e -> mouseEventOnCanvas(e, "Mouse Pressed"));
    canvas.setOnMouseReleased( e -> mouseEventOnCanvas(e, "Mouse Released"));
    canvas.setOnMouseClicked( e -> mouseEventOnCanvas(e, "Mouse Clicked"));
    canvas.setOnMouseDragged( e -> mouseEventOnCanvas(e, "Mouse Dragged"));
    canvas.setOnMouseMoved( e -> mouseEventOnCanvas(e, "Mouse Moved"));
    canvas.setOnMouseEntered( e -> mouseEventOnCanvas(e, "Mouse Entered"));
    canvas.setOnMouseExited( e -> mouseEventOnCanvas(e, "Mouse Exited"));

    stage.show();
  }

  private void draw() {
    GraphicsContext g = canvas.getGraphicsContext2D();
    g.setFill(Color.WHITE);
    g.fillRect(0,0,g.getCanvas().getWidth(), g.getCanvas().getHeight());
    g.setFill(Color.BLACK);
    g.fillText(eventInfo.toString(), 40, 40);
  }

  private void mouseEventOnScene(MouseEvent e) {
    if (e.getTarget() == canvas) {
      eventInfo.append("MOUSE EVENT ON SCENE: " + e.getEventType() + "\n\n");
    }
  }

  private void mouseEventOnCanvas(MouseEvent e, String eventType) {
    eventInfo.append(eventType + " on canvas at (");
    eventInfo.append((int)e.getX() + "," + (int)e.getY() + ")\n");
    if (eventType.equals("Mouse Pressed") || eventType.equals("Mouse Released") || eventType.equals("Mouse Clicked")) {
      eventInfo.append("Mouse button pressed or released: " + e.getButton() + "\n");
    }
    if (eventType.equals("Mouse Clicked")) {
      eventInfo.append("Click count: " + e.getClickCount() + "\n");
    }
    eventInfo.append("Modifier keys held down: ");
    if (e.isShiftDown()) {
      eventInfo.append("Shift  ");
    }
    if (e.isControlDown()) {
      eventInfo.append("Control  ");
    }
    if (e.isMetaDown()) {
      eventInfo.append("Meta  ");
    }
    if (e.isAltDown()) {
      eventInfo.append("Alt");
    }
    eventInfo.append("\n");
    eventInfo.append("Mouse buttons held down:  ");
    if (e.isPrimaryButtonDown()) {
      eventInfo.append("Primary  ");
    }
    if (e.isSecondaryButtonDown()) {
      eventInfo.append("Secondary  ");
    }
    if (e.isMiddleButtonDown()) {
      eventInfo.append("Middle  ");
    }
    draw();
    if (eventType.equals("Mouse Entered")) {
      eventInfo.append("\n\n(Info not erased after Mouse Entered)\n\n\n");
    } else {
      eventInfo.setLength(0);
    }
  }

}
