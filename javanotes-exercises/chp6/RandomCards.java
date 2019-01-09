import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.canvas.*;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

public class RandomCards extends Application {

  private Canvas canvas;
  private Image cardImages;

  public static void main(String[] args) {
    launch(args);
  }

  public void start(Stage stage) {
    cardImages = new Image("Resources/cards.png");

    canvas = new Canvas(5*119 + 120, 163 + 40);
    draw();

    Button redraw = new Button("Deal Again!");
    redraw.setOnAction( e -> draw());

    StackPane bottom = new StackPane(redraw);
    bottom.setStyle("-fx-background-color: gray; -fx-padding:5px;" +
      "-fx-border-color:blue; -fx-border-width: 2px 0 0 0");

    BorderPane root = new BorderPane(canvas);
    root.setBottom(bottom);
    root.setStyle("-fx-border-color:blue; -fx-border-width:2px; -fx-background-color: lightblue");

    stage.setScene(new Scene(root, Color.BLACK));
    stage.setTitle("Random Cards");
    stage.setResizable(false);
    stage.show();
  }

  private void draw() {
    GraphicsContext g = canvas.getGraphicsContext2D();

    Deck deck = new Deck();
    deck.shuffle();

    double sx, sy;
    double dx, dy;

    for (int i = 0; i < 5; i++) {
      Card card = deck.dealCard();
      System.out.println(card);
      sx = 79 * (card.getValue() - 1);
      sy = 123 * (3 - card.getSuit());
      dx = 20 + (119 + 20) * i;
      dy = 20;
      g.drawImage(cardImages, sx, sy, 79, 123, dx, dy, 119, 163);
    }
    System.out.println();
  }

} // end class RandomCards
