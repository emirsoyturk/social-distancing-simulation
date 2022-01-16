public final class Utility {
  public static final int freshRate = 17;
  public static final int HEIGHT = 1000;
  public static final int WIDTH = 1000;
  public static int gameSpeed = 1;

  public static final int secondsToFreshRate(int seconds){ return seconds * freshRate * 60; }

  public static final int freshRateToSeconds(int totalFreshRate){ return totalFreshRate / freshRate / 60; }
}
