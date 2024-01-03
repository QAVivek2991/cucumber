package pages;

import base.PredefinedActions;
import constant.ConstantPath;
import utility.PropOperation;

public class DashboardPage extends PredefinedActions {
public PropOperation propOperation;
	
	public DashboardPage() {
		propOperation = new PropOperation(ConstantPath.DASHBOARDPAGELOCATORS);
	}
	
	public boolean isUserNameVisible() {
		return isElementDisplayed(propOperation.getValue("userName"), true);
	}
}
