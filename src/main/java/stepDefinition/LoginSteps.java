package stepDefinition;

import org.junit.Assert;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.LoginPage;

public class LoginSteps {

	LoginPage loginPage = new LoginPage();

	@Then("^Verify that logo is visible$")
	public void verify_that_logo_is_visible() {
		Assert.assertTrue("Logo not visible", loginPage.isLogoVisible());
	}

	@Then("^Verify that connect text is visible$")
	public void verify_that_connect_text_is_visible() {
		Assert.assertTrue("Connect Text not visible", loginPage.isConnectTextVisible());

	}

	@Then("^Verify placeholder of email and password field$")
	public void verify_placeholder_of_email_and_password_field() {
		Assert.assertEquals("Email placeholder not correct", loginPage.getEmailPlaceholder(), "Enter your email");
		Assert.assertEquals("Password placeholder not correct", loginPage.getPasswordPlaceholder(), "Password");

	}

	@Then("^Verify that Remember me is clickable$")
	public void verify_that_Remember_me_is_clickable() {
		Assert.assertTrue("Remember is not clickable", loginPage.isRememberMeClickable());

	}

	@Then("^Verify that Forgate Password is clickable$")
	public void verify_that_Forgate_Password_is_clickable() {
		Assert.assertTrue("Forgate Password is not clickable", loginPage.isForgatePasswordClickable());

	}

	@Then("^Verify that Sine in button is clickable$")
	public void verify_that_Sine_in_button_is_clickable() {
		Assert.assertTrue("Sine in button is not clickable", loginPage.isSignInBtnClickable());

	}

	@When("User enter {string} as email")
	public void user_enter_as_email(String email) {
		loginPage.enterEmail(email);

	}

	@When("User enter {string} as password")
	public void user_enter_as_password(String password) {
		loginPage.enterPassword(password);

	}

	@When("User click on sine in button")
	public void user_click_on_sine_in_button() {
		loginPage.clickOnSignIn();

	}

	@Then("Error message should display as {string}")
	public void error_message_should_display_as(String errorMessage) {
		Assert.assertTrue("Error message is not visible", loginPage.isErrorMessageVisible());
	}

	@Then("Verify that user is not logged in")
	public void verify_that_user_is_not_logged_in() {
		Assert.assertTrue("User is logged in", loginPage.isConnectTextVisible());
	}

}
