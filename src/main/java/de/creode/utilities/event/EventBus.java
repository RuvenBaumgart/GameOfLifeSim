package de.creode.utilities.event;



import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EventBus {

    private Map<Class, List<IEventListener>> listeners = new HashMap<>();

    public void emit(IEvent event){
        Class eventClass = event.getClass();
        List<IEventListener> listener = listeners.get(eventClass);
        for (IEventListener iEventListener : listener) {
            iEventListener.handle(event);
        }
    };

    public<T extends IEvent> void listen(Class<T> eventClass, IEventListener<T> listener){
        if(!listeners.containsKey(eventClass)){
            listeners.put(eventClass, new LinkedList<>());
        }
        listeners.get(eventClass).add(listener);
    }
}
