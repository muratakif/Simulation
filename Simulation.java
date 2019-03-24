import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;

public class Simulation {

  private static double clock, utilizationTime, totalRenaged, totalWaitTime, totalUtil, nextDepartureTime;
  private int totalCall, index, j;
  private static ArrayList<Event> eventList;
  private static Event currentEvent;
  private static Queue<Event> eventQueue;

  public Simulation() {};

  public Simulation(int totalCall) {
    this.totalCall = totalCall;
  };

  public void run() {
    int seed = 14;
    run(seed);
  }

  public void run(int seed) {
    prepareSim(seed);
    runSim();
    printResultSummary(this.totalCall);
  }

  private void prepareSim(int seed){
    eventList = new ArrayList<Event>();
    eventQueue = new LinkedList<Event>();
    Random rng  = new Random(seed);

    for(int i = 1; i <= totalCall; ++i)
      eventList.add(createRandomEvent(rng, i));
  }

  private static Event createRandomEvent(Random rng, int index) {
    double patience = Statistics.uniform(rng, 10, 30);
    double service = Statistics.normal(rng, 3.2, 0.6);
    clock +=  Statistics.exponantial(rng, 4.5);

    return new Event(patience, clock, service, index);
  }

  private void runSim(){
    while(index < totalCall){
      if(eventQueue.isEmpty())
        processReadyEvent();

      while(!eventQueue.isEmpty())
        processPendingEvent();

      index = j;
    }
  }

  private void processReadyEvent() {
    currentEvent = eventList.get(index);
    j = index + 1;
    clock = currentEvent.getArrivalTime();
    updateNextDepartureTime();
    updateStatistics();
    fillEventQueue();
  }

  private void processPendingEvent() {
    clock = nextDepartureTime;
    currentEvent = eventQueue.peek();
    if(hasCurrentEventRenaged())
      currentEvent.setWaitingTime(currentEvent.getPatience());
    else {
      updateNextDepartureTime();
      currentEvent.setWaitingTime(clock - currentEvent.getArrivalTime());
      fillEventQueue();
    }
    updateStatistics();
    eventQueue.remove();
  }

  private void updateStatistics() {
    totalWaitTime += currentEvent.getWaitingTime();

    if(hasCurrentEventRenaged())
      ++totalRenaged; // No need to update totalUtil
    else
      totalUtil += currentEvent.getServiceTime();
  }

  private void fillEventQueue() {
    while(j < totalCall && eventList.get(j).getArrivalTime() < nextDepartureTime)
      eventQueue.add(eventList.get(j++));
  }

  private boolean hasCurrentEventRenaged() {
    double scheduledWaitTime = clock - currentEvent.getArrivalTime();
    return currentEvent.getPatience() < scheduledWaitTime;
  }

  private void updateNextDepartureTime() {
    nextDepartureTime = calculateDepartureTime();
  }

  private static double calculateDepartureTime() {
    if(eventQueue.isEmpty())
      return currentEvent.getArrivalTime() + currentEvent.getServiceTime();
    else
      return clock + currentEvent.getServiceTime();
  }

  private static void processSingleEvent() {

  }

  // TODO: Trasport to a helper class
  private static void printResultSummary(int totalCall){
    System.out.println("Total Utilization: "  + totalUtil / clock +
                       " Total Renaged: "     + totalRenaged +
                       " Average Waiting: "   + totalWaitTime / totalCall);
  }
}
