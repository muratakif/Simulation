public class Event {

  private double patience;
  private double arrivalTime;
  private double serviceTime;
  private double waitingTime;
  private int index;

  public Event() {};

  public Event(double patience, double arrivalTime, double serviceTime, int index){
    this.patience = patience;
    this.arrivalTime = arrivalTime;
    this.serviceTime = serviceTime;
    this.waitingTime = 0;
    this.index = index;
  };

  public void setPatience(double patience){
    this.patience = patience;
  }

  public void setArrivalTime(double arrivalTime){
    this.arrivalTime = arrivalTime;
  }

  public void setServiceTime(double serviceTime){
    this.serviceTime = serviceTime;
  }

  public void setWaitingTime(double waitingTime){
    this.waitingTime = waitingTime;
  }

  public void setIndex(int index){
    this.index = index;
  }

  public double getPatience(){
    return this.patience;
  }

  public double getArrivalTime(){
    return this.arrivalTime;
  }

  public double getServiceTime(){
    return this.serviceTime;
  }

  public double getWaitingTime(){
    return this.waitingTime;
  }

  public int getIndex(){
    return this.index;
  }
}
