package io.muic.ooc.fab;

public enum AnimalType {
    FOX(0.02),
    RABBIT(0.08),
    TIGER(0.01);

    // The probability that the animal will be created in any given grid position.
    private final double CREATION_PROBABILITY;

    AnimalType(double creationProbability) {
        this.CREATION_PROBABILITY = creationProbability;
    }

    public double getCreationProbability() {
        return CREATION_PROBABILITY;
    }
}
