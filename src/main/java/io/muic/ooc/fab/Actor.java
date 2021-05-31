package io.muic.ooc.fab;

import java.util.List;

public abstract class Actor {
    // The actor's position.
    protected Location location;
    // The field occupied.
    protected Field field;

    /**
     * Create a new actor. An actor may be created with age zero (a new born)
     * or with a random age.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public abstract void create(Field field, Location location);

    public abstract Location getNewLocation();

    /**
     * Place the actor at the new location in the given field.
     *
     * @param newLocation The actor's new location.
     */
    protected void setLocation(Location newLocation) {
        if (location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    /**
     * This is what the actor does.
     *
     * @param newActors A list to return newly born actors.
     */
    protected abstract void act(List<Animal> newActors);

}
