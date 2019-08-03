package cs2030.simulator;

/**
 *  Encapsulate an event.
 */
public class Event {
    public static final int ARRIVES = 1;
    public static final int SERVED = 2;
    public static final int WAITS = 3;
    public static final int LEAVE = 4;
    public static final int DONE = 5;
    public static final int SERVER_REST = 6;
    public static final int SERVER_BACK = 7;
    
    private Customer customer;
    private double time;
    private int state;
    private Server server;

    /**
     * A constructor for the event.
     * @param customer The customer.
     * @param time The time of event.
     * @param state The state of event.
     */
    public Event(Customer customer, double time, int state) {
        this.customer = customer;
        this.time = time;
        this.state = state;
    }

    /**
     * Another constructor for the event.
     * @param customer The customer.
     * @param time The time of event.
     * @param state The state of event.
     * @param server The server.
     */
    public Event(Customer customer, double time, int state, Server server) {
        this.customer = customer;
        this.time = time;
        this.state = state;
        this.server = server;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public double getTime() {
        return this.time;
    }

    public int getState() {
        return this.state;
    }

    public Server getServer() {
        return this.server;
    }
    
    /*
     * Returns a string representation of the event.
     * @return The string representation of this event.
     */
    @Override
    public String toString() {
        if (this.state == this.ARRIVES) {
            return String.format("%.3f", this.time) + " " 
                + this.customer.getCusType() + " arrives";
        } else if (this.state == this.SERVED) {
            return this.server.getServerID() <= Supervisor.getNumOfServer()
                ? String.format("%.3f", this.time) + " " + this.customer.getCusType()
                + " served by server " + this.server.getServerID()
                : String.format("%.3f", this.time) + " " + this.customer.getCusType()
                + " served by self-check " + this.server.getServerID();
        } else if (this.state == this.WAITS) {
            return this.server.getServerID() <= Supervisor.getNumOfServer()
                ? String.format("%.3f", this.time) + " " + this.customer.getCusType()
                + " waits to be served by server " + this.server.getServerID()
                : String.format("%.3f", this.time) + " " + this.customer.getCusType()
                + " waits to be served by self-check " + this.server.getServerID();
        } else if (this.state == this.LEAVE) {
            return String.format("%.3f", this.time) + " " 
                + this.customer.getCusType() + " leaves";
        } else if (this.state == this.DONE) {
            return this.server.getServerID() <= Supervisor.getNumOfServer()
                ? String.format("%.3f", this.time) + " " + this.customer.getCusType()
                + " done serving by server " + this.server.getServerID()
                : String.format("%.3f", this.time) + " " + this.customer.getCusType()
                + " done serving by self-check " + this.server.getServerID();
        } else if (this.state == this.SERVER_REST) {
            return String.format("%.3f", this.time) + " server "
                + this.server.getServerID() + " rest";
        } else if (this.state == this.SERVER_BACK) {
            return String.format("%.3f", this.time) + " server "
                + this.server.getServerID() + " back";
        }
        return "";
    }
}
