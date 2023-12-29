package stepDefination;

import base.PredefinedActions;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class AutomationHooks{

	PredefinedActions predefinedActions = new PredefinedActions();
	 
	
	@Before
	public void setUp() {
		predefinedActions.start();
	}
	
	@After
	public void tearDown(Scenario scenario) {
		if(scenario.isFailed()) {
			scenario.attach(predefinedActions.takeScreenshot(), "image/jpeg", "SS");
		}
		predefinedActions.tearDown();
	}
}
