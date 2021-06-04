package io.muic.ooc.fab;

import java.util.List;
import java.util.Random;

public class Tiger extends Animal{
    // Characteristics shared by all tigers (class variables).

    // The age at which a tiger can start to breed.
    private static final int BREEDING_AGE = 35;
    // The age to which a tiger can live.
    private static final int MAX_AGE = 100;
    // The likelihood of a tiger breeding.
    private static final double BREEDING_PROBABILITY = 0.04;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 1;
    // Random generator
    private static final Random RANDOM = new Random();

    // The tiger's food level, which is increased by eating rabbits.
    private int foodLevel;

    public Tiger(boolean randomAge, Field field, Location location) {
        super(randomAge, field, location);
        foodLevel = RANDOM.nextInt(12);
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
     * @param newActors A list to return newly born tigers.
     */
    @Override
    public void act(List<Actor> newActors) {
        incrementHunger();
        super.act(newActors);
    }

    @Override
    protected int getFoodValue() {
        return 0;
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
            Object actor = field.getObjectAt(where);
            if (actor instanceof Animal && !(actor instanceof Tiger)) {
                Animal animal = (Animal) actor;
                int foodValue = eatFood(animal);
                if (foodValue != 0 && animal.isAlive()) {
                    animal.setDead();
                    foodLevel = foodValue;
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
    protected Actor createYoung(Field field, Location location) {
        return new Tiger(false, field, location);
    }
}
