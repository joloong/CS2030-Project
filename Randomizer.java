package cs2030.simulator;

public class Randomizer {
    public static RandomGenerator randomG;

    public Randomizer(int seed, double lambda, double eu, double rho) {
        RandomGenerator randomG = new RandomGenerator(seed, lambda, eu, rho);
        this.randomG = randomG;
    }

    public double getInterArrivalTime() {
        return this.randomG.genInterArrivalTime();
    }

    public double getServiceTime() {
        return this.randomG.genServiceTime();
    }

    public double getRandomRest() {
        return this.randomG.genRandomRest();
    }

    public double getRestPeriod() {
        return this.randomG.genRestPeriod();
    }
}
