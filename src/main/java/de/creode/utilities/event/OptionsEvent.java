package de.creode.utilities.event;

public class OptionsEvent implements IEvent{
    public enum Type{
        GRID_SIZE,
        SIMULATION_SPEED,
    }
    private Type type;
    private int value;

    public OptionsEvent (Type type, int value){
        this.type = type;
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public int getValue() {
        return value;
    }
}
