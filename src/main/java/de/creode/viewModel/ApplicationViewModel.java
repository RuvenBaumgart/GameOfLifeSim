package de.creode.viewModel;

import de.creode.model.ApplicationState;
import de.creode.utilities.Property;

public class ApplicationViewModel {

    private Property<ApplicationState> applicationState = new Property<>(ApplicationState.EDITING);

   public ApplicationViewModel(){
   }

   public Property<ApplicationState> getProperty(){
       return applicationState;
   }

}
