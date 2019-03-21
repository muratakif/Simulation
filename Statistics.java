import java.util.Random;

public class Statistics {
  public static double normal(Random rng, double mean, double std){
    return 3.2 + rng.nextGaussian() * 0.6;
  }

  public static double exponantial(Random rng, double mean){
    return - Math.log(1 - rng.nextDouble()) * mean;
  }

  public static double uniform(Random rng, int MinPatience, int MaxPatience) {
    return MinPatience + (MaxPatience - MinPatience) * rng.nextDouble();
  }
}