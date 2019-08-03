package cs2030.simulator;

/**
 * Encapsulates a customer.
 */
public class Customer {
    private int index;
    private boolean greedy;
    
    /**
     * A constructor of the customer.
     * @param index Index of the customer.
     * @param greedy If the customer is greedy.
     */
    public Customer(int index, boolean greedy) {
        this.index = index;
        this.greedy = greedy;
    }

    public int getID() {
        return this.index;
    }

    public boolean isGreedy() {
        return this.greedy;
    }
    
    /**
     * Gets the index and type of customer.
     * @return If Customer is greedy, Customer.ID and (greedy). Else,
     *         Customer.ID.
     */
    public String getCusType() {
        if (this.greedy) {
            return this.index + "(greedy)";
        } else {
            return this.index + "";
        }
    }
}
