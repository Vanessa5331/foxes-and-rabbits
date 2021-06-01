package io.muic.ooc.fab;

public class Rabbit extends Animal {
    // Characteristics shared by all rabbits (class variables).

    // The age at which a rabbit can start to breed.
    private static final int BREEDING_AGE = 5;
    // The age to which a rabbit can live.
    private static final int MAX_AGE = 40;
    // The likelihood of a rabbit breeding.
    private static final double BREEDING_PROBABILITY = 0.12;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    // The food value of a single rabbit. In effect, this is the
    // number of steps a predator can go before it has to eat again.
    private static final int FOOD_VALUE = 9;

    public Rabbit(boolean randomAge, Field field, Location location) {
        super(randomAge,field, location);
    }

    public static int getFoodValue() {
        return FOOD_VALUE;
    }

    @Override
    public Location getNewLocation() {
        return field.freeAdjacentLocation(location);
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected int getBreedingAge() {
        return BREEDING_AGE;
    }

    @Override
    protected double getBreedingProbability() {
        return BREEDING_PROBABILITY;
    }

    @Override
    protected int getMaxLitterSize() {
        return MAX_LITTER_SIZE;
    }

    @Override
    protected Animal createYoung(Field field, Location location) {
        return new Rabbit(false, field, location);
    }
}
