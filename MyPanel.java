import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class MyPanel extends JPanel{

    private List<Person> personList = new ArrayList<>();

    private int personNumber;
    private double infectionPercentage;
    private double startingPercentage;
    private double percentageOfPeopleMoving;

    private double healthyNumber = 0;
    private double patientNumber = 0;
    private double deathNumber = 0;
    private double immuneNumber = 0;

    private final int freshRate = Utility.freshRate;
    private final int HEIGHT = Utility.HEIGHT;
    private final int WIDTH = Utility.WIDTH;
    private static JSlider speed = new JSlider(JSlider.HORIZONTAL,0,10,1);
    private JButton restart = new JButton("Restart");

    private Timer timer = new Timer(freshRate, e -> {
      repaint();
    });

    MyPanel(){
      add(speed);
      setVisible(true);

      speed.setMajorTickSpacing(2);
      speed.setMinorTickSpacing(1);
      speed.setPaintTicks(true);
      speed.setBounds(400,50,150,50);
      speed.setVisible(true);

      speed.addChangeListener(e -> {
        Utility.gameSpeed = speed.getValue();
      });
    }

    protected void increaseHealthyNumber(){ healthyNumber++; }
    protected void increasePatientNumber(){ patientNumber++; }
    protected void increaseDeathNumber(){ deathNumber++; }
    protected void increaseImmuneNumber(){ immuneNumber++; }

    protected void decreaseHealthyNumber(){ healthyNumber--; }
    protected void decreasePatientNumber(){ patientNumber--; }
    protected void decreaseImmuneNumber(){ immuneNumber--; }

    protected void startGame(int personNumber, double infectionPercentage, double startingPercentage, double percentageOfPeopleMoving){
      this.personNumber = personNumber;
      this.infectionPercentage = infectionPercentage;
      this.startingPercentage = startingPercentage;
      this.percentageOfPeopleMoving = percentageOfPeopleMoving;

      setVisible(true);
      MyFrame.home.setVisible(false);

      for(int i=0; i<personNumber; i++)
        personList.add(new Person(infectionPercentage, startingPercentage, percentageOfPeopleMoving));

      timer.start();
    }

    protected void restart(){
      healthyNumber = 0;
      patientNumber = 0;
      deathNumber = 0;
      immuneNumber = 0;
      setVisible(false);
      MyFrame.home.setVisible(true);

      personList = new ArrayList<>();
      timer.stop();
    }

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      for(int i=0; i < personList.size(); i++){
        Person first = personList.get(i);
        boolean collisionDetect = false;
        for(int j=0; j < personList.size(); j++){
          if(i == j) continue;
          Person second = personList.get(j);
          if(first.collision(second)){
            collisionDetect = true;
          }
        }
        if(!collisionDetect) {
          first.openToCollision = true;
        }

        first.paint(g);
      }

      drawChart(g);

      if(patientNumber == 0){
        drawFinalResults(g);
      }
    }

    private void drawFinalResults(Graphics g){
      for(Person w: personList) {
          w.velocityX = 0;
          w.velocityY = 0;
      }

      g.setColor(new Color(46, 41, 78));
      g.fillRect(250,300,500,300);
      g.setColor(new Color(205,250,250));
      g.setFont(new Font("Times New Roman",Font.PLAIN,25));
      g.drawString("Simulation is completed",350,340);

      g.setFont(new Font("Times New Roman",Font.PLAIN,15));
      g.drawString("Simulation Conditions",300,420);

      g.drawString("Number of Person : " + personNumber,280,450);
      g.drawString("Infection Percentage : " + infectionPercentage,280,470);
      g.drawString("Starting Percentage : " + startingPercentage,280,490);
      g.drawString("Moving Percentage: " + percentageOfPeopleMoving,280,510);

      g.drawString("Results",540,420);

      g.drawString("Number of healthy  : " + (int) healthyNumber, 520, 450);
      g.drawString("Number of patient: " + (int) patientNumber,520,470);
      g.drawString("Number of immune : " + (int) immuneNumber,520,490);
      g.drawString("Number of death : " + (int) deathNumber,520,510);

      restart.setVisible(true);
      restart.setBounds(450, 550, 100, 30);
      restart.setFocusable(false);
      restart.setBackground(Color.WHITE);
      restart.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          restart();
          restart.setVisible(false);
          setVisible(false);
          MyFrame.home.setVisible(true);
        }
      });
      add(restart);
    }

    private void drawChart(Graphics g){
      int barWidth = 50;
      int barMaxHeight = 200;
      double healthyPercent = ((double) healthyNumber / (double) personNumber * 100) ;
      double patientPercent = ((double) patientNumber / (double) personNumber * 100) ;
      double immunePercent = ((double) immuneNumber / (double) personNumber * 100);
      double deathPercent = ((double) deathNumber / (double) personNumber * 100);

      g.setColor(new Color(30,190,30));
      g.fillRect(30, (int) (950 - barMaxHeight / 150 * healthyPercent), barWidth, (int) (barMaxHeight / 100 * healthyPercent));
      g.setColor(Color.RED);
      g.fillRect(95, (int) (950 - barMaxHeight / 150 * patientPercent), barWidth, (int) (barMaxHeight / 100 * patientPercent));
      g.setColor(Color.BLUE);
      g.fillRect(160, (int) (950 - barMaxHeight / 150 * immunePercent), barWidth , (int) (barMaxHeight / 100 * immunePercent));
      g.setColor(Color.GRAY);
      g.fillRect(225, (int) (950 - barMaxHeight / 150 * deathPercent), barWidth, (int) (barMaxHeight / 100 * deathPercent));
      g.setColor(Color.ORANGE);
      g.fillRect(15,950,280,50);

      g.setColor(Color.BLACK);
      g.drawString("Healthy", 30, 966);
      g.drawString("%" + String.format("%.2f", healthyPercent), 30, 982);

      g.drawString("Patient", 95, 966);
      g.drawString("%" + String.format("%.2f", patientPercent), 95, 982);

      g.drawString("Immune", 160, 966);
      g.drawString("%" + String.format("%.2f", immunePercent), 160, 982);

      g.drawString("Dead", 225, 966);
      g.drawString("%" + String.format("%.2f", deathPercent), 225, 982);
    }

}
