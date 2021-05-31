package io.muic.ooc.fab;

public class ActorFactory {

    public static Actor createHunter(Field field, Location location){
        Hunter hunter = new Hunter();
        hunter.create(field, location);
        return hunter;
    }

    public static Animal createAnimal(AnimalType animalType, Field field, Location location){
        if(animalType.equals(AnimalType.RABBIT)){
            Rabbit rabbit = new Rabbit();
            rabbit.create(field, location);
            return rabbit;
        }else if(animalType.equals(AnimalType.FOX)){
            Fox fox = new Fox();
            fox.create(field, location);
            return fox;
        }else if(animalType.equals(AnimalType.TIGER)){
            Tiger tiger = new Tiger();
            tiger.create(field, location);
            return tiger;
        }else{
            throw new IllegalArgumentException("Unknown animal type");
        }
    }
}
