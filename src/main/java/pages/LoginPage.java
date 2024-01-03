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
		return isElementDisplayed(propOperation.getValue("logo"), true);
	}
	
	public boolean isConnectTextVisible() {
		return isElementDisplayed(propOperation.getValue("connectText"), true);
	}
	
	public String getEmailPlaceholder() {
		return getAttribute(propOperation.getValue("emailField"), true, "placeholder");
	}
	
	public String getPasswordPlaceholder() {
		return getAttribute(propOperation.getValue("passwordField"), true, "placeholder");
	}
	
	public boolean isRememberMeClickable() {
		return isElementClickable(propOperation.getValue("rememberMe"), true);
	}
	
	public boolean isForgatePasswordClickable() {
		return isElementClickable(propOperation.getValue("forgotPassword"), true);
	}
	
	public boolean isSignInBtnClickable() {
		return isElementClickable(propOperation.getValue("btnSignIn"), true);
	}
	
	public void enterEmail(String email) {
		setText(propOperation.getValue("emailField"), true, email );
	}
	
	public void enterPassword(String password) {
		setText(propOperation.getValue("passwordField"), true, password );
	}
	
	public void clickOnSignIn() {
		clickOnElement(propOperation.getValue("btnSignIn"), true);
	}
	
	public boolean isErrorMessageVisible() {
		return isElementDisplayed(propOperation.getValue("invalidCredentialErrorMsg"), true);
	}
}
