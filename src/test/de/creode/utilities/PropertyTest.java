package de.creode.utilities;

import de.creode.utilities.ISimpleChangeListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class PropertyTest {

   @Test
   void constructor_WithValueReturnsAInstanzeWithValue() {
      String expected = "Hello World";
      Property<String> stringProperty = new Property(expected);
      assertEquals(expected, stringProperty.get());
   }

   @Test
   void constructor_NoValueReturnsAInstanzeWithNull() {
      Property<String> stringProperty = new Property();
      assertEquals(null, stringProperty.get());
   }

   @Test
   void notifyAll_CallsTheNotifier(){
      DoubleListener dl = new DoubleListener();
      Property<Double> dp = new Property<>();
      dp.listen(dl);
      dp.set(4.44);
      assertEquals(4.44, dl.value);
      assertTrue(dl.notified);
   }


   private static class DoubleListener implements ISimpleChangeListener<Double> {
      private boolean notified = false;
      private Double value;
      @Override
      public void valueChanged(Double value) {
         this.notified = true;
         this.value = value;
      }
   }

}

