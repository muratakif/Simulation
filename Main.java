public class Main {

  public static void main(String[] args) throws Exception {
    int seed = 14, total_call;

    if(args.length == 2){
      seed = Integer.parseInt(args[0]);
      total_call = Integer.parseInt(args[1]);
    } else if (args.length == 1)
      total_call = Integer.parseInt(args[0]);
    else
      throw new Exception("You must type arguments!");

    Simulation sim = new Simulation(total_call);
    sim.run(seed);
  }
}
