package de.creode.utilities.event;

public class ToolBarEvent implements IEvent{

    public enum Type{
       START,
       STOP,
       RESET,
       STEP
   }

    private Type type;

   public ToolBarEvent (Type type){
       this.type = type;
   }

    public Type getType() {
        return type;
    }
}
