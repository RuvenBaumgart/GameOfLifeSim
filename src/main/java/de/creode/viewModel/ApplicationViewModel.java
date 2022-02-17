package de.creode.viewModel;

import de.creode.utilities.Property;

import java.util.LinkedList;
import java.util.List;

public class ApplicationViewModel {

    private Property<ApplicationState> applicationState = new Property<>(ApplicationState.EDITING);

   public ApplicationViewModel(){
   }

   public Property<ApplicationState> getProperty(){
       return applicationState;
   }

}
