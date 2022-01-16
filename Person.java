import java.awt.*;
import java.util.Random;

public class Person {
    int x,y;
    double velX,velY;
    double velocityX=0, velocityY=0;
    int status = 2;
    int recoveryTime;
    int rT;
    boolean openToCollision = true;
    double infectionPercentage;
    Random random = new Random();
    private final int size = 7;

    public Person(double infectionPercentage, double startingPercentage, double percentageOfPeopleMoving)
    {
        x = random.nextInt(Utility.HEIGHT - size * 2);
        y = random.nextInt(Utility.WIDTH - size * 2);

        if(random.nextInt(100) < startingPercentage) {
          status = 0;
          MyFrame.panel.increasePatientNumber();
        }
        else{
          status = 2;
          MyFrame.panel.increaseHealthyNumber();
        }

        if((int)(Math.random()*100+1)<=percentageOfPeopleMoving) {
            int lowestSpeed = 5;
            velocityX = (int) (Math.random()* lowestSpeed + 1);
            velocityY = (int) (Math.random()* lowestSpeed + 1);
        }
        velX=velocityX;
        velY=velocityY;
        recoveryTime = (int) (Math.random()* Utility.secondsToFreshRate(10) + Utility.secondsToFreshRate(10));
        this.infectionPercentage = infectionPercentage;
    }

    public void paint(Graphics g){
      checkSpeedDirection();
      checkRecoverPossobility();
      checkDiePossibility();

      switch (status) {
          case 2 : g.setColor(Color.GREEN); break;
          case 1 : g.setColor(Color.BLUE); break;
          case 0 : g.setColor(Color.RED); break;
          case -1 : g.setColor(Color.LIGHT_GRAY); break;
      }

      g.fillOval(x, y, size, size);
    }

    private void checkSpeedDirection(){
      x += velocityX * Utility.gameSpeed;
      y += velocityY * Utility.gameSpeed;
      if (x > Utility.HEIGHT - 2*size || x < 0) {
          velX *= -1;
          velocityX *= -1;
      }
      if (y > Utility.WIDTH - 2*size || y < 0) {
          velY *= -1;
          velocityY *= -1;
      }
    }

    private void checkRecoverPossobility(){
      if(status == 0) {
        recoveryTime = recoveryTime - Utility.gameSpeed;
      }
      if (recoveryTime <= 0 && status == 0) {//natural immunity
        status = 1;
        MyFrame.panel.increaseImmuneNumber();
        MyFrame.panel.decreasePatientNumber();
      }
    }

    private void checkDiePossibility(){

      if(Math.random() < 0.003 / 17 / 60 * Utility.gameSpeed && status == 0) {
          status = -1;
          MyFrame.panel.increaseDeathNumber();
          MyFrame.panel.decreasePatientNumber();
          velX = 0;
          velY = 0;
      }
    }

    public boolean collision(Person person1){
      Rectangle rectangle1 = new Rectangle(person1.x, person1.y, size, size);
      Rectangle rectangle2 = new Rectangle(this.x, this.y ,size ,size);

      if(rectangle1.intersects(rectangle2)) {

          if(!this.openToCollision || !person1.openToCollision) return true;

          this.openToCollision = false;
          person1.openToCollision = false;

          if (person1.status == 0 && this.status == 2 && random.nextInt(100) < infectionPercentage) {
            this.status = 0;
            MyFrame.panel.increasePatientNumber();
            MyFrame.panel.decreaseHealthyNumber();
          }
          else if (person1.status == 2 && this.status == 0 && random.nextInt(100) < infectionPercentage) {
            person1.status = 0;
            MyFrame.panel.increasePatientNumber();
            MyFrame.panel.decreaseHealthyNumber();
          }
          else if (person1.status == 0 && this.status == 1 && random.nextInt(100 * 70) < infectionPercentage) {
            this.status = 0;
            MyFrame.panel.increasePatientNumber();
            MyFrame.panel.decreaseImmuneNumber();
          }
          else if (person1.status == 1 && this.status == 0 && random.nextInt(100 * 70) < infectionPercentage) {
            person1.status = 0;
            MyFrame.panel.increasePatientNumber();
            MyFrame.panel.decreaseImmuneNumber();
          }
          return true;
      }
      return false;
    }
}
