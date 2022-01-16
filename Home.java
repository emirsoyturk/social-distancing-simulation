import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Home extends JPanel {

  private JSlider jsliderArray [] = new JSlider[]{
    new JSlider(JSlider.HORIZONTAL,0,1500,500),
    new JSlider(JSlider.HORIZONTAL,0,100,3),
    new JSlider(JSlider.HORIZONTAL,0,100,1),
    new JSlider(JSlider.HORIZONTAL,0,100,50)
  };

  private JLabel textArray [] = new JLabel[]{
    new JLabel(),
    new JLabel(),
    new JLabel(),
    new JLabel()
  };

  private JLabel headerArray [] = new JLabel[]{
    new JLabel("People : "),
    new JLabel("Infection % : "),
    new JLabel("Starting % : "),
    new JLabel("Moving % : ")
  };

  private JButton start = new JButton("Start");

  private JCheckBox isPatientMoving = new JCheckBox("Patient is Moving");

  protected Home(){
    setBackground(new Color(46, 41, 78));
    setLayout(null);
    setVisible(true);

    start.setBounds(250, 450, 80, 30);
    start.setFocusable(false);
    start.setBackground(new Color(234, 222, 218));
    start.setOpaque(true);
    start.setContentAreaFilled(true);
    start.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        MyFrame.panel.startGame(
          jsliderArray[0].getValue(),
          jsliderArray[1].getValue(),
          jsliderArray[2].getValue(),
          jsliderArray[3].getValue()
        );
      }
    });
    add(start);

    //isPatientMoving.setBounds(30, 350, 150, 30);
    //isPatientMoving.setOpaque(false);
    //isPatientMoving.setForeground(Color.WHITE);

    //add(isPatientMoving);

    for(int i=0; i<jsliderArray.length; i++){
      JSlider curr = jsliderArray[i];
      add(curr);
      curr.setOpaque(false);
      curr.setMajorTickSpacing(curr.getMaximum()/5);
      curr.setMinorTickSpacing(curr.getMaximum()/20);
      curr.setBounds(150, 150 + i * 50, 300, 20);
    }

    for(int i=0; i<textArray.length; i++){
      JLabel curr = textArray[i];
      add(curr);
      curr.setText("" + jsliderArray[i].getValue());
      curr.setBounds(460, 150 + i * 50, 100, 20);
      curr.setForeground(Color.WHITE);
    }

    for(int i=0; i<headerArray.length; i++){
      JLabel curr = headerArray[i];
      add(curr);
      curr.setBounds(30, 150 + i * 50, 100, 20);
      curr.setForeground(Color.WHITE);
    }

  }

  protected void paintComponent(Graphics g){
    super.paintComponent(g);
    for(int i=0; i<textArray.length; i++){
      textArray[i].setText("" + jsliderArray[i].getValue());
    }
    g.setColor(new Color(234, 222, 218));
    g.drawRect(0,0,550,500);
  }
}
