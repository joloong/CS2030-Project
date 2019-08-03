import java.util.Scanner;
import cs2030.simulator.Supervisor;
import cs2030.simulator.Randomizer;

/**
 * Main is the main driver class for the restaurant.
 */
public class Main {
    /**
     * Main method.
     * @param args Arguments to input. (If any)
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int seed = sc.nextInt();
        int numOfServer = sc.nextInt();
        int numOfSCC = sc.nextInt();
        int maxQLength = sc.nextInt();
        int numOfCustomer = sc.nextInt();
        double arrivalRate = sc.nextDouble();
        double serviceRate = sc.nextDouble();
        double restingRate = sc.nextDouble();
        double probOfRest = sc.nextDouble();
        double probOfGreedy = sc.nextDouble();

        Supervisor supervisor = new Supervisor(numOfServer, numOfSCC, 
                maxQLength, probOfRest, probOfGreedy);
        Randomizer randomizer = new Randomizer(seed, arrivalRate, serviceRate, restingRate);
        double now = 0.0;
        supervisor.addArrive(1, now);

        for (int i = 2; i <= numOfCustomer; i++) {
            now += randomizer.getInterArrivalTime();
            supervisor.addArrive(i, now);
        }

        supervisor.run();
    }
}
