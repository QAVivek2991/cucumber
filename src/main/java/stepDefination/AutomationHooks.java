package stepDefination;

import base.PredefinedActions;
import io.cucumber.java.After;
import io.cucumber.java.Before;


public class AutomationHooks{

	PredefinedActions predefinedActions = new PredefinedActions();
	
	@Before
	public void setUp() {
		predefinedActions.start();
	}
	
	@After
	public void tearDown() {
		predefinedActions.tearDown();
	}
}
