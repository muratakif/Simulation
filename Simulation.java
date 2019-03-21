import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;

public class Simulation {

  private static double clock, utilization_time, total_renaged, total_wait_time, total_util;
  private int total_call;

  public Simulation() {};

  public Simulation(int total_call) {
    this.total_call = total_call;
  };

  public void run() {
    int seed = 14;
    run(seed);
  }

  public void run(int seed) {
    ArrayList<Event> eventList = prepareSim(seed, this.total_call);
    runSim(eventList, this.total_call);
    printResultSummary(this.total_call);
  }

  private static ArrayList<Event> prepareSim(int seed, int total_call){
    ArrayList<Event> eventList = new ArrayList<Event>();
    Random rng  = new Random(seed);

    for(int i = 1; i <= total_call; ++i)
      eventList.add(createRandomEvent(rng, i));

    return eventList;
  }

  private static Event createRandomEvent(Random rng, int index) {
    double patience = Statistics.uniform(rng, 10, 30);
    double service = Statistics.normal(rng, 3.2, 0.6);
    clock +=  Statistics.exponantial(rng, 4.5);

    return new Event(patience, clock, service, index);
  }

  private static void runSim(ArrayList<Event> eventList, int total_call){
    Queue<Event> eventQueue = new LinkedList<Event>();

    double departure = 0, waiting_time = 0;
    int index = 0, j = 0;

    while(index < total_call){
      //Process the new arrival
      if(eventQueue.isEmpty()){
        clock = eventList.get(index).getArrivalTime();
        //Update the departure clock
        departure = eventList.get(index).getArrivalTime() + eventList.get(index).getServiceTime();
        j = index + 1;
        waiting_time = 0;
        total_util += eventList.get(index).getServiceTime();

        //Fill the Queue according to the new departure clock
        while(j < total_call && eventList.get(j).getArrivalTime() < departure)
          eventQueue.add(eventList.get(j++));
      }

      //Process the calls in the Queue till the Queue is empty
      while(!eventQueue.isEmpty()){
        //Process the first call in the Queue
        clock = departure;
        waiting_time = clock - eventQueue.peek().getArrivalTime();

        if(eventQueue.peek().getPatience() < waiting_time){
          waiting_time = eventQueue.peek().getPatience();
          total_wait_time += waiting_time;
          total_renaged++;
        }
        else{
          departure = clock + eventQueue.peek().getServiceTime();   //Update the departure clock
          total_wait_time += waiting_time;
          total_util += eventQueue.peek().getServiceTime();

          //Fill the Queue according to the new departure clock
          while(j < total_call && eventList.get(j).getArrivalTime() < departure)
            eventQueue.add(eventList.get(j++));
        }
        eventQueue.remove();
      }

      index = j; //Update the index
    }
  }

  private static void processSingleEvent() {

  }

  private static void printResultSummary(int total_call){
    System.out.println("Total Utilization: "  + total_util / clock +
                       " Total Renaged: "     + total_renaged +
                       " Average Waiting: "   + total_wait_time / total_call);
  }
}
