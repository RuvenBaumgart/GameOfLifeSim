package de.creode.utilities;

import java.util.LinkedList;
import java.util.List;

public class Property<T> {
    private T value;
    private List<ISimpleChangeListener<T>> listeners = new LinkedList<>();

    public Property(T value){
        this.value = value;
    }

    public Property(){
        this(null);
    }

    public void listen(ISimpleChangeListener<T> listener){
        this.listeners.add(listener);
    }

    public void set(T value){
        this.value = value;
        notifyAllListeners();
    }

    public T get(){
        return value;
    }

    public boolean isPresent(){
        return value != null;
    }

    public void notifyAllListeners(){
        for (ISimpleChangeListener<T> listener : listeners) {
            listener.valueChanged(this.value);
        }
    }

}
