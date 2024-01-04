package stepDefination;

import java.io.IOException;

import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import base.PredefinedActions;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class AutomationHooks {

	PredefinedActions predefinedActions = new PredefinedActions();
	public static String browserName;
	public static String useWebdriverManager;

	@Before
	public void setUp() throws IOException, XmlPullParserException {
		browserName = System.getProperty("browserName", "defaultValue");
		useWebdriverManager = System.getProperty("useWebdriverManager", "defaultValue");
		predefinedActions.start();
	}

	@After
	public void tearDown(Scenario scenario) {
		if (scenario.isFailed()) {
			scenario.attach(predefinedActions.takeScreenshot(), "image/jpeg", "Screen Shot");
		}
		predefinedActions.tearDown();
	}
}
