package io.muic.ooc.fab;

import java.util.List;
import java.util.Random;

public class Tiger extends Animal{
    // Characteristics shared by all tigers (class variables).

    // The age at which a tiger can start to breed.
    private static final int BREEDING_AGE = 30;
    // The age to which a tiger can live.
    private static final int MAX_AGE = 150;
    // The likelihood of a tiger breeding.
    private static final double BREEDING_PROBABILITY = 0.045;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 1;
    // The food value of a single rabbit. In effect, this is the
    // number of steps a tiger can go before it has to eat again.
    private static final int RABBIT_FOOD_VALUE = 6;
    // The food value of a single fox. In effect, this is the
    // number of steps a tiger can go before it has to eat again.
    private static final int FOX_FOOD_VALUE = 10;
    // Random generator
    private static final Random RANDOM = new Random();

    // The tiger's food level, which is increased by eating rabbits.
    private int foodLevel;

    /**
     * Create a tiger. A tiger can be created as a new born (age zero and not
     * hungry) or with a random age and food level.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    @Override
    public void create(Field field, Location location) {
        super.create(field, location);
        foodLevel = RANDOM.nextInt(RABBIT_FOOD_VALUE);
    }

    @Override
    public Location getNewLocation(){
        Location newLocation = findFood();
        if (newLocation == null) {
            // No food found - try to move to a free location.
            newLocation = field.freeAdjacentLocation(location);
        }
        return newLocation;
    }

    /**
     * This is what the tiger does most of the time: it hunts for rabbits. In the
     * process, it might breed, die of hunger, or die of old age.
     *
     * @param newTigers A list to return newly born tigers.
     */
    @Override
    public void act(List<Animal> newTigers) {
        incrementHunger();
        super.act(newTigers);
    }

    /**
     * Make this tiger more hungry. This could result in the tiger's death.
     */
    private void incrementHunger() {
        foodLevel--;
        if (foodLevel <= 0) {
            setDead();
        }
    }

    /**
     * Look for rabbits adjacent to the current location. Only the first live
     * rabbit is eaten.
     *
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood() {
        List<Location> adjacent = field.adjacentLocations(location);
        for (Location where : adjacent) {
            Object animal = field.getObjectAt(where);
            if (animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if (rabbit.isAlive()) {
                    rabbit.setDead();
                    foodLevel = RABBIT_FOOD_VALUE;
                    return where;
                }
            } else if (animal instanceof Fox) {
                Fox fox = (Fox) animal;
                if (fox.isAlive()) {
                    fox.setDead();
                    foodLevel = FOX_FOOD_VALUE;
                    return where;
                }
            }
        }
        return null;
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
        Tiger tiger = new Tiger();
        tiger.create(field, location);
        return tiger;
    }
}
