package io.muic.ooc.fab;

import java.util.List;
import java.util.Random;

public abstract class Animal extends Actor{
    // Whether the animal is alive or not.
    private boolean alive;
    // Individual characteristics (instance fields).
    // The animal's age.
    protected int age;

    private static final Random RANDOM = new Random();

    /**
     * Create a new animal. A animal may be created with age zero (a new born)
     * or with a random age.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    @Override
    public void create(Field field, Location location) {
        age = 0;
        setAlive(true);
        this.field = field;
        setLocation(location);
    }

    /**
     * Check whether the animal is alive or not.
     *
     * @return true if the animal is still alive.
     */
    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * Increase the age. This could result in the animal's death.
     */
    protected void incrementAge() {
        age++;
        if (age > getMaxAge()) {
            setDead();
        }
    }

    public abstract int getMaxAge();

    /**
     * Indicate that the animal is no longer alive. It is removed from the field.
     */
    protected void setDead() {
        setAlive(false);
        if (location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * This is what the animal does most of the time - it runs around. Sometimes
     * it will breed or die of old age.
     *
     * @param newAnimals A list to return newly born animals.
     */
    @Override
    protected void act(List<Animal> newAnimals){
        incrementAge();
        if (isAlive()) {
            giveBirth(newAnimals);
            // Try to move into a free location.
            Location newLocation = getNewLocation();
            if (newLocation != null) {
                setLocation(newLocation);
            } else {
                // Overcrowding.
                setDead();
            }
        }
    }

    /**
     * Generate a number representing the number of births, if it can breed.
     *
     * @return The number of births (may be zero).
     */
    protected int breed() {
        int births = 0;
        if (canBreed() && RANDOM.nextDouble() <= getBreedingProbability()) {
            births = RANDOM.nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }

    protected abstract double getBreedingProbability();
    protected abstract int getMaxLitterSize();

    /**
     * An animal can breed if it has reached the breeding age.
     *
     * @return true if the animal can breed, false otherwise.
     */
    private boolean canBreed() {
        return age >= getBreedingAge();
    }

    protected abstract int getBreedingAge();

    protected abstract Animal createYoung(Field field, Location location);

    /**
     * Check whether or not this animal is to give birth at this step. New
     * births will be made into free adjacent locations.
     *
     * @param newAnimals A list to return newly born animals.
     */
    protected void giveBirth(List<Animal> newAnimals) {
        // New rabbits are born into adjacent locations.
        // Get a list of adjacent free locations.
        List<Location> free = field.getFreeAdjacentLocations(location);
        int births = breed();
        for (int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Animal young = createYoung(field, loc);
            newAnimals.add(young);
        }
    }

}
