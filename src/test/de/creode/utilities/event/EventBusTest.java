package de.creode.utilities.event;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventBusTest {
    private EventBus eventBus;
    @BeforeEach
    void setUp() {
        eventBus = new EventBus();
    }

    @Test
    void emit_handlesAllListeners() {
        ChangeEvent changeEvent = new ChangeEvent();
        ChangeEventListener listener = new ChangeEventListener();
        eventBus.listen(ChangeEvent.class, listener::handle);
        eventBus.emit(changeEvent);
        assertTrue(listener.changed);
    }

    private static class ChangeEventListener implements IEventListener<ChangeEvent>{
        public boolean changed = false;
        @Override
        public void handle(ChangeEvent event) {
            changed = event.change;
        }
    }

    private static class ChangeEvent implements IEvent{
         private boolean change = true;
    }
}