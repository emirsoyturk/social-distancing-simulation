import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {

  static MyPanel panel = new MyPanel();
  static Home home = new Home();
  protected MyFrame(){
    setLayout(null);
    getContentPane().setBackground(new Color(46, 41, 78));
    add(panel);
    add(home);
    panel.setVisible(false);

    panel.setBounds(0, 0, 1000, 1000);
    home.setBounds(200, 200, 600, 600);

    setVisible(true);
    getContentPane().setPreferredSize(new Dimension(1000,1000));
    pack();
    setTitle("Social Distance Simulation");
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }
}
