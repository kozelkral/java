import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class chp8_5 extends JPanel {

  public static void main(String[] args) {
    JFrame window = new JFrame("Expression Grapher");
    chp8_5 content = new chp8_5();
    window.setContentPane(content);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setResizable(false);
    window.setLocation(250,250);
    window.pack();
    window.setVisible(true);
  } // end main

  /*
  Private use variables
  */

  private String userInput;
  private Expr expression;
  private JTextField input;
  private Font font;
  private JPanel graph;
  private boolean expressionSet = false;

  /*
  Constructors
  */

  public chp8_5() {
    setBackground(Color.BLACK);
    setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));

    font = new Font("Verdana", Font.PLAIN, 18);

    JPanel title = makeTextPanel("Enter an expression in the box and it will be graphed.");
    JPanel inputArea = makePanel("textbox");
    JPanel graphArea = makePanel("graph");

    setLayout(new BorderLayout());

    JPanel inputBox = new JPanel();
    inputBox.setLayout(new GridLayout(2,1));
    inputBox.add(title);
    inputBox.add(inputArea);

    add(inputBox, BorderLayout.NORTH);
    add(graphArea, BorderLayout.CENTER);

    input.selectAll();
  } // end constructor

  /*
  Utility functions
  */

  private JPanel makeTextPanel(String text) {
    JPanel box = new JPanel();
    box.setBackground(Color.BLACK);
    JLabel label = new JLabel(text);
    label.setForeground(Color.WHITE);
    label.setFont(font);
    box.add(label);
    return box;
  } // end makePanel(String text)

  private JPanel makePanel(String type) {
    JPanel box = new JPanel();
    box.setBackground(Color.BLACK);
    box.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
    box.setLayout(new GridLayout(1,1));

    switch (type.toLowerCase()) {
      case "textbox":
        input = new JTextField("...");
        input.setMargin(new Insets(5,5,5,5));
        input.setFont(font);
        input.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e) {
            userInput = input.getText();
            try {
              expression = new Expr(userInput);
              System.out.println();
              System.out.println("user input: " + expression.toString());
              expressionSet = true;
              repaint();
            } catch (IllegalArgumentException error) {
              input.setText("Invalid equation!");
              input.requestFocusInWindow();
              input.selectAll();
            }
          }
        });
        box.add(input);
        break;
      case "graph":
        graph = new JPanel(){
          public void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setColor(Color.ORANGE);
            g.drawLine(0,300,600,300);
            g.drawLine(300,0,300,600);

            g.setFont(font);
            g.drawString("-5",0,318);
            g.drawString("5",582,318);
            g.drawString("-5",278,600);
            g.drawString("5",286,18);

            if (expressionSet) {
              drawGraph(g, expression);
            }
          }
        };
        graph.setPreferredSize(new Dimension(600,600));
        graph.setBackground(Color.BLACK);
        graph.setForeground(Color.WHITE);
        box.add(graph);
        break;
      default:
        throw new RuntimeException("Invalid request given to makePanel");
    }
    return box;
  } // end makePanel(String text, String type)

  private void drawGraph(Graphics g, Expr ex) {
    double step = 0.04; // how much x increases each time
    double a = -5.0, b;
    int x, y, lastX = 100, lastY = 0;
    int width = graph.getWidth(), height = graph.getHeight();

    g.setColor(Color.PINK);

    for (int i = 0; i < 250; i++) {
      b = ex.value(a);
      if (Double.isNaN(b)) {
        a += step;
        continue;
      }
      if (lastX == 100) {
        lastX = (int)((a + 5)/10 * width);
        lastY = (int)((5 - b)/10 * height);
        a += step;
        continue;
      }

      x = (int)((a + 5)/10 * width);
      y = (int)((5 - b)/10 * height);

      g.drawLine(lastX,lastY,x,y);

      lastX = x;
      lastY = y;
      a += step;
    }
  } // end drawGraph

} // end class
