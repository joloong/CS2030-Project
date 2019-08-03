package cs2030.simulator;

import cs2030.simulator.Supervisor;
import java.util.LinkedList;

/**
 * Encapsulate a server.
 */
public class Server {
    private int index;
    private double start;
    private double end;

    private int numServed;

    private boolean isServing;
    private boolean isResting;

    private LinkedList<Event> queue = new LinkedList<Event>();

    /**
     * A constructor for the server.
     * @param index Index of the customer.
     */
    public Server(int index) {
        this.index = index;
        this.start = 0.0;
        this.end = 0.0;
        this.numServed = 0;
        this.isServing = false;
        this.isResting = false;
    }
    
    /**
     * Another (empty) constructor for the server.
     */
    public Server() {

    }

    /**
     * Serves the customer.
     * @param e An event where the server will server the customer in the event.
     * @return An event after the server is done serving the customer.
     */
    public Event serve(Event e) {
        Customer c = e.getCustomer();
        double time = e.getTime();

        this.start = time;
        this.numServed++;
        this.isServing = true;
        this.end = time + Randomizer.randomG.genServiceTime();
        Event served = new Event(c, this.end, 5, this);
        return served;
    }

    public void addToQueue(Event e) {
        this.queue.add(e);
    }

    public void removeFromQueue() {
        this.queue.remove();
    }

    public int getServerID() {
        return this.index;
    }

    public double getEnd() {
        return this.end;
    }

    public void setIsServing(boolean b) {
        this.isServing = b;
    }

    public boolean isServing(double time) {
        return this.isServing;
    }

    public void setIsResting(boolean b) {
        this.isResting = b;
    }

    public boolean isResting(double time) {
        return this.isResting;
    }

    public Event getNextInQueue() {
        return this.queue.remove();
    }

    public boolean gotQueue(double time) {
        return this.queue.size() >= 1;
    }

    public int getQueueSize() {
        return this.queue.size();
    }

    public boolean isQMaxed(double time) {
        return queue.size() >= Supervisor.getMaxQLength();
    }

    public int getServed() {
        return this.numServed;
    }
}
