package base;

import java.net.MalformedURLException;
import java.net.URL;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;

import constant.Credentials;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.github.bonigarcia.wdm.WebDriverManager;

public class PredefinedActions {

	public WebDriverWait wait;
	public WebDriver webDriver;
	public AndroidDriver androidDriver;
	public AppiumDriverLocalService service;
	public Select select;
	public DesiredCapabilities capabilities = new DesiredCapabilities();
	Credentials credentials = new Credentials();

	// ----------------------------------------- Web Automation
	// ---------------------------------------------------------------
	public void configureSelenium() {
		String browser = Strings.isNullOrEmpty(System.getProperty("browserName")) ? "chrome"
				: System.getProperty("browserName");

		switch (browser.toLowerCase()) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			webDriver = new ChromeDriver();
			break;
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			webDriver = new FirefoxDriver();
			break;
		default:
			WebDriverManager.chromedriver().setup();
			webDriver = new ChromeDriver();
			break;
		}
	}

	public void openWebSite(String url) {
		configureSelenium();
		webDriver.manage().window().maximize();
		webDriver.get(url);
	}

	public void start() {
		openWebSite(credentials.AdminURL);
	}

	public void tearDown() {
		webDriver.close();
	}

	// ------------------------------------ Common methods
	// -----------------------------------------------------------------
	public By getElementBy(String locator) {
		By elementBy = null;
		String[] temp = locator.split("]:-");
		temp[0] = temp[0].substring(1).toUpperCase();

		switch (temp[0]) {
		case "ID":
			elementBy = By.id(temp[1]);
			break;
		case "XPATH":
			elementBy = By.xpath(temp[1]);
			break;
		case "CSS":
			elementBy = By.cssSelector(temp[1]);
			break;
		}
		return elementBy;
	}

	public WebElement getElementAfterClickable(String locator) {
		By elementBy = getElementBy(locator);
		WebElement element = null;
		wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
		element = wait.until(ExpectedConditions.elementToBeClickable(elementBy));
		return element;
	}

	public WebElement getElement(String locator, boolean isWaitRequired) {
		By elementBy = getElementBy(locator);
		WebElement element = null;
		if (isWaitRequired == true) {
			wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(elementBy));
		} else {
			element = webDriver.findElement(elementBy);
		}

		return element;
	}

	public List<WebElement> getElements(String locator, boolean isWaitRequired) {
		By elementBy = getElementBy(locator);
		List<WebElement> elements = null;
		if (isWaitRequired == true) {
			wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
			elements = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(elementBy, 0));
		} else {
			elements = webDriver.findElements(elementBy);
		}
		return elements;
	}

	public int getCountOfElements(String locator, boolean isWaitRequired) {
		return getElements(locator, isWaitRequired).size();
	}

	public List<String> getAllElementText(String locator, boolean isWaitRequired) {
		List<String> elementText = new ArrayList<String>();
		List<WebElement> elements = getElements(locator, isWaitRequired);
		for (WebElement element : elements) {
			elementText.add(element.getText());
		}
		return elementText;
	}

	public void clickOnElement(String locator, boolean isWaitRequired) {
		getElement(locator, isWaitRequired).click();
	}

	public void clickOnElementAfterClicable(String locator, boolean isWaitRequired) {
		getElementAfterClickable(locator).click();
	}

	public void sendText(String locator, String value, boolean isWaitRequired) {
		getElement(locator, isWaitRequired).sendKeys(value);
	}

	public boolean isDisplayed(String locator, boolean isWaitRequired) {
		try {
			WebElement element = getElement(locator, isWaitRequired);
			if (element.isDisplayed())
				return true;
			else
				return element.isDisplayed();
		} catch (NoSuchElementException ne) {
			return false;
		}

	}

	public boolean isClickable(String locator, boolean isWaitRequired) {
		boolean flag = false;
		try {
			flag = getElement(locator, isWaitRequired).isEnabled();
		} catch (Exception NoSuchElementFoundException) {

		}
		return flag;
	}

	public String getText(String locator, boolean isWaitRequired) {
		return getElement(locator, isWaitRequired).getText();
	}

	public String getAttribute(String locator, boolean isWaitRequired, String attributeName) {
		return getElement(locator, isWaitRequired).getAttribute(attributeName);
	}

	public void selectByIndex(String locator, boolean isWaitRequired, int index) {
		select = new Select(getElement(locator, isWaitRequired));
		select.selectByIndex(index);
	}

// --------------------------------------------------- Mobile Automation ----------------------------------------------------------------	
	/*
	 * public void setCapabilitiesForApplication() {
	 * capabilities.setCapability("app",
	 * "C:\\Users\\Vive.Kumar\\Downloads\\app-stage-debug.apk");
	 * capabilities.setCapability("autoGrantPermissions", true);
	 * capabilities.setCapability("noReset", true);
	 * capabilities.setCapability("appPackage",
	 * "us.pressurepro.fxconsumerapp.stage");
	 * capabilities.setCapability("appActivity",
	 * "us.pressurepro.fxconsumerapp.view.MainActivity"); }
	 * 
	 * 
	 * 
	 * public AndroidDriver setGeneralCapabilities() throws MalformedURLException {
	 * capabilities.setCapability("platformName", "android");
	 * capabilities.setCapability("automationName", "Uiautomator2");
	 * capabilities.setCapability("deviceName", "Pixel2"); androidDriver = new
	 * AndroidDriver(new URL("http://20.20.20.125:4723/"), capabilities); return
	 * androidDriver; }
	 * 
	 * 
	 * public void ConfigureAppium(Boolean isAppInstalled) throws
	 * MalformedURLException {
	 * 
	 * 
	 * // service = new AppiumServiceBuilder() .withAppiumJS(new //
	 * File("//usr//local//lib//node_modules//appium//build//lib//main.js")) //
	 * .withIPAddress("127.0.0.1").usingPort(4723).build(); service.start();
	 * 
	 * if (isAppInstalled == false) { setCapabilitiesForApplication(); }
	 * setGeneralCapabilities().manage().timeouts().implicitlyWait(Duration.
	 * ofSeconds(10)); }
	 * 
	 * public void longPressAction(WebElement ele) { ((JavascriptExecutor)
	 * androidDriver).executeScript("mobile: longClickGesture",
	 * ImmutableMap.of("elementId", ((RemoteWebElement) ele).getId(), "duration",
	 * 2000)); }
	 * 
	 * public void scrollToEndAction() { boolean canScrollMore; do { canScrollMore =
	 * (Boolean) ((JavascriptExecutor)
	 * androidDriver).executeScript("mobile: scrollGesture", ImmutableMap.of("left",
	 * 100, "top", 100, "width", 200, "height", 200, "direction", "down", "percent",
	 * 3.0
	 * 
	 * )); } while (canScrollMore); }
	 * 
	 * public void swipeAction(WebElement ele, String direction) {
	 * ((JavascriptExecutor) androidDriver).executeScript("mobile: swipeGesture",
	 * ImmutableMap.of("elementId", ((RemoteWebElement) ele).getId(), "direction",
	 * direction, "percent", 0.75)); }
	 * 
	 * public void scrollUp() { boolean canScrollMore; do { canScrollMore =
	 * (Boolean) ((JavascriptExecutor)
	 * androidDriver).executeScript("mobile: scrollGesture", ImmutableMap.of(
	 * "left", 100, "top", 100, "width", 200, "height", 200, "direction", "up",
	 * "percent", 3.0 ));}while(canScrollMore); }
	 * 
	 * public void scrollDown() { boolean canScrollMore; do { canScrollMore =
	 * (Boolean) ((JavascriptExecutor)
	 * androidDriver).executeScript("mobile: scrollGesture", ImmutableMap.of(
	 * "left", 100, "top", 300, "width", 200, "height", 800, "direction", "down",
	 * "percent", 10.0 ));}while(canScrollMore); }
	 */
}
