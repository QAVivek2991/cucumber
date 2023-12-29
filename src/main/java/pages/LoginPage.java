package pages;

import base.PredefinedActions;
import constant.ConstantPath;
import utility.PropOperation;


public class LoginPage extends PredefinedActions {
	public PropOperation propOperation;
	
	public LoginPage() {
		propOperation = new PropOperation(ConstantPath.LOGINPAGELOCATORS);
	}
	
	public boolean isLogoVisible() {
		//System.out.print("logo"+propOperation.getValue("logo"));
		return isDisplayed(propOperation.getValue("logo"), true);
	}
	
	public boolean isConnectTextVisible() {
		return isDisplayed(propOperation.getValue("connectText"), true);
	}
	
	public String getEmailPlaceholder() {
		return getAttribute(propOperation.getValue("emailField"), true, "placeholder");
	}
	
	public String getPasswordPlaceholder() {
		return getAttribute(propOperation.getValue("passwordField"), true, "placeholder");
	}
	
	public boolean isRememberMeClickable() {
		return isClickable(propOperation.getValue("rememberMe"), true);
	}
	
	public boolean isForgatePasswordClickable() {
		return isClickable(propOperation.getValue("forgotPassword"), true);
	}
	
	public boolean isSignInBtnClickable() {
		return isClickable(propOperation.getValue("btnSignIn"), true);
	}
	
	public void enterEmail(String email) {
		sendText(propOperation.getValue("emailField"), email, true );
	}
	
	public void enterPassword(String password) {
		sendText(propOperation.getValue("passwordField"), password, true );
	}
	
	public void clickOnSignIn() {
		clickOnElement(propOperation.getValue("btnSignIn"), true);
	}
	
	public boolean isErrorMessageVisible() {
		return isDisplayed(propOperation.getValue("invalidCredentialErrorMsg"), true);
	}
}
