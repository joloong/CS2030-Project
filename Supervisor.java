package cs2030.simulator;

import java.util.PriorityQueue;

/**
 * Encapsulates a supervisor. The Supervisor maintains the entire
 * restaurant by ensuring that each event is correctly scheduled.
 */
public class Supervisor {
    private static int numOfServer;
    private static int numOfSCC;
    private static int maxQLength;
    private static double probOfRest;
    private static double probOfGreedy;
    private Server[] server;
    private int numServed;
    private int numNotServed;
    private double totalWait;

    private PriorityQueue<Event> pq = new PriorityQueue<Event>(new EventComparator());

    /** 
     * A constructor for the supervisor.
     * @param a Number of server(s).
     * @param b Number of self checkout counter(s).
     * @param c Maximum queue length.
     * @param d Probably of rest for server.
     * @param e Probably of greedy customer.
     */
    public Supervisor(int a, int b, int c, double d, double e) {
        this.numOfServer = a;
        this.numOfSCC = b;
        this.maxQLength = c;
        this.probOfRest = d;
        this.probOfGreedy = e;
        this.server = new Server[numOfServer + numOfSCC];
        this.numServed = 0;
        this.numNotServed = 0;
        this.totalWait = 0.0;

        for (int i = 1; i <= (numOfServer + numOfSCC); i++) {
            server[i - 1] = new Server(i);
        }
    }

    /**
     * Creates an event for every new customer and adds into the
     * priority queue of events.
     * @param index index of the customer.
     * @param time time of arrival of the customer.
     */
    public void addArrive(int index, double time) {
        double greedyProb = Randomizer.randomG.genCustomerType();
        Customer c = new Customer(index, greedyProb < this.probOfGreedy);
        Event e = new Event(c, time, 1);
        this.pq.add(e);
    }

    /**
     * Runs the restaurant.
     */
    public void run() {
        while (!this.pq.isEmpty()) {
            Event e = this.pq.poll();
            Customer c = e.getCustomer();
            Server s;
            double time = e.getTime();
            Event newE;
            switch (e.getState()) {

                case Event.ARRIVES:
                    System.out.println(e);
                    boolean isCusServed = false;
                    boolean isCusWait = false;
                    for (int i = 0; i < (this.numOfServer + this.numOfSCC); i++) {
                        if (!server[i].isServing(time) && !server[i].isResting(time)) {
                            newE = new Event(c, time, 2, server[i]);
                            pq.add(newE);
                            isCusServed = true;
                            server[i].setIsServing(true);
                            break;
                        }
                    }

                    if (!isCusServed) {
                        for (int i = 0; i < (this.numOfServer + this.numOfSCC); i++) {
                            if (!server[i].isQMaxed(time)) {
                                if (!c.isGreedy()) {
                                    newE = new Event(c, time, 3, server[i]);
                                    pq.add(newE);
                                    server[i].addToQueue(newE);
                                    isCusWait = true;
                                    break;
                                }
                            }
                        }
                    }

                    if (!isCusServed && !isCusWait) {
                        Server shortest = new Server();
                        int len = Integer.MAX_VALUE;
                        boolean found = false;
                        for (int i = 0; i < (this.numOfServer + this.numOfSCC); i++) {
                            if ((server[i].getQueueSize() < len) && !server[i].isQMaxed(time)) {
                                shortest = server[i];
                                len = shortest.getQueueSize();
                                found = true;
                            }
                        }

                        if (found) {
                            newE = new Event(c, time, 3, shortest);
                            pq.add(newE);
                            shortest.addToQueue(newE);
                            isCusWait = true;
                        }
                    }

                    if (!isCusServed && !isCusWait) {
                        pq.add(new Event(c, time, 4));
                    }

                    break;

                case Event.SERVED:
                    s = e.getServer();
                    if (s.getServerID() <= this.numOfServer) {
                        System.out.println(e);
                    } else {
                        System.out.println(e); 
                    }
                    pq.add(s.serve(e));
                    break;

                case Event.WAITS:
                    s = e.getServer();
                    if (s.getServerID() <= this.numOfServer) {
                        System.out.println(e);
                    } else {
                        System.out.println(e);
                    }
                    break;

                case Event.LEAVE:
                    System.out.println(e);
                    numNotServed++;
                    break;

                case Event.DONE: 
                    s = e.getServer();
                    s.setIsServing(false);
                    if (s.getServerID() <= this.numOfServer) {
                        System.out.println(e);
                    } else {
                        System.out.println(e);
                    }

                    double restProb = 2.0;
                    if (s.getServerID() <= this.numOfServer) {
                        restProb = Randomizer.randomG.genRandomRest();
                    }

                    if (restProb < this.probOfRest) {
                        pq.add(new Event(c, time, 6, s));
                    } else if (s.gotQueue(time)) {
                        Event nextEvent = s.getNextInQueue();
                        double nextTime = nextEvent.getTime();
                        this.totalWait += time - nextTime;
                        Customer nextCus = nextEvent.getCustomer();
                        pq.add(new Event(nextCus, time, 2, s));
                    }
                    break;

                case Event.SERVER_REST:
                    s = e.getServer();
                    System.out.println(e);

                    s.setIsResting(true);
                    double restPeriod = Randomizer.randomG.genRestPeriod();
                    pq.add(new Event(c, time + restPeriod, 7, s));
                    break;

                case Event.SERVER_BACK:
                    s = e.getServer();
                    System.out.println(e);

                    s.setIsResting(false);
                    if (s.gotQueue(time)) {
                        Event nextEvent = s.getNextInQueue();
                        double nextTime = nextEvent.getTime();
                        this.totalWait += time - nextTime;
                        Customer nextCus = nextEvent.getCustomer();
                        pq.add(new Event(nextCus, time, 2, s));
                    }
                    break;

                default:
            }
        }

        for (int i = 0; i < (numOfServer + numOfSCC); i++) {
            this.numServed += server[i].getServed();
        }

        double avgWait = this.numServed == 0 
            ? 0.0 
            : this.totalWait / this.numServed;
        System.out.println("[" + String.format("%.3f", avgWait) + " " +
                this.numServed + " " + this.numNotServed + "]");
    }

    public static int getMaxQLength() {
        return maxQLength;
    }

    public static int getNumOfServer() {
        return numOfServer;
    }
}
