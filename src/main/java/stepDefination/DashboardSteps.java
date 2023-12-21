package stepDefination;

import org.junit.Assert;

import io.cucumber.java.en.Then;
import pages.DashboardPage;

public class DashboardSteps {
	
	DashboardPage dashboardPage = new DashboardPage();
	
	@Then("User logged in and username is visible")
	public void user_logged_in_and_username_is_visible() {
	    Assert.assertTrue("Username not visible", dashboardPage.isUserNameVisible());
	}

}
