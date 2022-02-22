package de.creode.utilities.event;

import de.creode.model.CursorPosition;

public class MyMouseEvent implements IEvent{
    private Type type;
    private CursorPosition cursorPosition;

    public enum Type{
        CLICKED,
        MOVED,
        DRAGED
    }

    public MyMouseEvent(Type type, CursorPosition cursorPosition){
        this.type = type;
        this.cursorPosition = cursorPosition;
    }

    public Type getType() {
        return type;
    }

    public CursorPosition getCursorPosition() {
        return cursorPosition;
    }
}
