package de.creode.viewModel;

import de.creode.model.ApplicationState;
import de.creode.utilities.Property;
import de.creode.utilities.event.IEvent;
import de.creode.utilities.event.ToolBarEvent;

public class ApplicationViewModel {

    private Property<ApplicationState> applicationState = new Property<>(ApplicationState.EDITING);

   public ApplicationViewModel(){
   }

   public Property<ApplicationState> getProperty(){
       return applicationState;
   }

}
