import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.FontPosture;
import javafx.scene.canvas.*;
import javafx.scene.paint.Color;
/* javac --module-path "C:\Program Files\Java\javafx-sdk-11.0.1\lib" --add-modules=javafx.base,javafx.controls,javafx.graphics <class>.java */
public class chp6_0 extends Application {

  /*
  private static Font font;

  public void start(Stage stage) {

    Label message = new Label("FX App");
    font = new Font(40);
    message.setFont(font);

    Button hello = new Button("Say Hi");
    hello.setOnAction( e -> message.setText("Hi there!") );
    Button bye = new Button("Say Bye");
    bye.setOnAction( e -> message.setText("Ok, bye now!") );
    Button quit = new Button("Quit");
    quit.setOnAction( e -> Platform.exit() );

    HBox buttonBar = new HBox(20, hello, bye, quit);
    buttonBar.setAlignment(Pos.CENTER);

    Button bold = new Button("Bold");
    bold.setOnAction(e -> {
      String name = font.getName();
      double size = font.getSize();
      String style = font.getStyle().toUpperCase();

      if (style.equals("BOLD")) {
        font = Font.font(name, FontWeight.NORMAL, size);
      } else {
        font = Font.font(name, FontWeight.BOLD, size);
      }

      message.setFont(font);
    });
    Button italic = new Button("Italic");
    italic.setOnAction(e -> {
      String name = font.getName();
      double size = font.getSize();
      String style = font.getStyle().toUpperCase();

      if (style.equals("ITALIC")) {
        font = Font.font(name, FontPosture.REGULAR, size);
      } else {
        font = Font.font(name, FontPosture.ITALIC, size);
      }
      message.setFont(font);
    });
    Button changeSize = new Button("Change Size");
    changeSize.setOnAction(e -> {
      String name = font.getName();
      double size = font.getSize();

      if (size > 30) {
        size = 24;
      } else {
        size = 40;
      }

      font = Font.font(name, size);
      message.setFont(font);
    });
    Button reset = new Button("Reset");
    reset.setOnAction(e -> {
      font = Font.font(40);
      message.setFont(font);
    });

    HBox fontBar = new HBox(20, bold, italic, changeSize, reset);
    fontBar.setAlignment(Pos.CENTER);

    BorderPane root = new BorderPane();
    root.setCenter(message);
    root.setBottom(buttonBar);
    root.setTop(fontBar);

    Scene scene = new Scene(root, 450, 200);
    stage.setScene(scene);
    stage.setTitle("JavaFx App");
    stage.show();

  } // end start()
  */

} // end class chp6_0
