package io.muic.ooc.fab;

import java.util.List;

public class Hunter extends Actor {

    public void create(Field field, Location location){
        this.field = field;
        setLocation(location);
    }

    @Override
    public Location getNewLocation() {
        Location newLocation = findPrey();
        if (newLocation == null) {
            // No food found - try to move to a free location.
            newLocation = field.freeAdjacentLocation(location);
        }
        return newLocation;
    }

    private Location findPrey() {
        List<Location> adjacent = field.adjacentLocations(location);
        for (Location where : adjacent) {
            Object animal = field.getObjectAt(where);
            if (animal instanceof Animal) {
                Animal prey = (Animal) animal;
                if (prey.isAlive()) {
                    prey.setDead();
                    return where;
                }
            }
        }
        return null;
    }

    @Override
    protected void act(List<Animal> newAnimals) {
        Location newLocation = getNewLocation();
        if (newLocation != null) {
            setLocation(newLocation);
        }
    }

}
