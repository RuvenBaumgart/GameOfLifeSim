package de.creode.utilities.event;

import javafx.event.Event;

public interface IEventListener<T extends IEvent>{
    public void handle(T event);
}
