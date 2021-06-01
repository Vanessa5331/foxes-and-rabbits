package io.muic.ooc.fab;

public enum ActorType {
    FOX(0.02, Fox.class),
    RABBIT(0.08, Rabbit.class),
    TIGER(0.01, Tiger.class),
    HUNTER(0.001, Hunter.class);

    // The probability that the animal will be created in any given grid position.
    private final double CREATION_PROBABILITY;
    private final Class<? extends Actor> actorClass;

    ActorType(double creationProbability, Class<? extends Actor> actorClass) {
        this.CREATION_PROBABILITY = creationProbability;
        this.actorClass = actorClass;
    }

    public double getCreationProbability() {
        return CREATION_PROBABILITY;
    }

    public Class<? extends Actor> getActorClass(){
        return actorClass;
    }
}
