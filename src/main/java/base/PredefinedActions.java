package base;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ThreadGuard;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import constant.ConstantPath;
import customException.InvalidLocatorType;
import io.github.bonigarcia.wdm.WebDriverManager;
import stepDefinition.AutomationHooks;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class PredefinedActions {

	// public static WebDriverWait wait;
	public static final ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();
	public Select select;
	public DesiredCapabilities capabilities = new DesiredCapabilities();

	// ----------------------------------------- Web Automation
	// ---------------------------------------------------------------

	public static WebDriver getDriver() {
		return webDriver.get();
	}

	public static String readPropertyValue(String propertyKey) throws IOException, XmlPullParserException {
		MavenXpp3Reader reader = new MavenXpp3Reader();
		Model model;
		String propertyValue = null;
		try {
			FileReader fileReader = new FileReader("pom.xml");
			model = reader.read(fileReader);
			propertyValue = model.getProperties().getProperty(propertyKey);
			return propertyValue;
		} catch (IOException | XmlPullParserException e) {
			e.printStackTrace();
			return propertyValue;
		}
	}

	public void configureSelenium(String browser, boolean useWebDriverManager, boolean runHeadless) {

		ChromeOptions chromeOptions = new ChromeOptions();
		EdgeOptions edgeOptions = new EdgeOptions();
		FirefoxOptions firefoxOptions = new FirefoxOptions();

		if (runHeadless) {
			chromeOptions.addArguments("--headless");
			edgeOptions.addArguments("--headless");
			firefoxOptions.addArguments("--headless");
		}

		switch (browser.toLowerCase()) {
		case "chrome":
			if (useWebDriverManager)
				WebDriverManager.chromedriver().setup();
			else
				System.setProperty("webdriver.chrome.driver", ConstantPath.CHROMEDRIVER);
			webDriver.set(ThreadGuard.protect(new ChromeDriver(chromeOptions)));
			break;
		case "firefox":
			if (useWebDriverManager)
				WebDriverManager.firefoxdriver().setup();
			else
				System.setProperty("webdriver.gecko.driver", ConstantPath.GICODRIVER);
			webDriver.set(ThreadGuard.protect(new FirefoxDriver(firefoxOptions)));
			break;
		case "edge":
			if (useWebDriverManager)
				WebDriverManager.edgedriver().setup();
			else
				System.setProperty("webdriver.edge.driver", ConstantPath.EDGEDRIVER);
			webDriver.set(ThreadGuard.protect(new EdgeDriver(edgeOptions)));
			break;
		default:
			if (useWebDriverManager)
				WebDriverManager.edgedriver().setup();
			else
				System.setProperty("webdriver.edge.driver", ConstantPath.EDGEDRIVER);
			webDriver.set(ThreadGuard.protect(new EdgeDriver(edgeOptions)));
			break;
		}
	}

	public void openWebSite(String url) throws IOException, XmlPullParserException {
		String browserName = AutomationHooks.browserName;
		boolean useWebdriverManager = AutomationHooks.useWebdriverManager;
		boolean runHeadless = AutomationHooks.runHeadless;
		configureSelenium(browserName, useWebdriverManager, runHeadless);
		getDriver().manage().window().maximize();
		getDriver().get(url);
	}

	public void start() throws IOException, XmlPullParserException {
		openWebSite(ConstantPath.CONNECT_URL);
	}

	public void tearDown() {
		getDriver().close();
	}

	private By getLocatorBy(String locator) {
		String[] temp = locator.split("]:-");
		String locatorType = temp[0].replace("[", "").toUpperCase();
		String locatorValue = temp[1];
		
		switch (locatorType) {
		case "XPATH":
			return By.xpath(locatorValue);
		case "ID":
			return By.id(locatorValue);
		case "CLASS":
			return By.className(locatorValue);		
		case "CSS":
			return By.cssSelector(locatorValue);
		default:
			throw new InvalidLocatorType(locatorType + " is not valid");
		}
	}

	protected WebElement getElement(String locator, boolean isWaitRequired) {
		WebElement element = null;
		By by = getLocatorBy(locator);
		if (isWaitRequired) {
			WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} else
			element = getDriver().findElement(by);
		return element;
	}

	protected List<WebElement> getAllElements(String locator, boolean isWaitRequired) {
		By by = getLocatorBy(locator);
		List<WebElement> elements = new ArrayList<>();
		if (isWaitRequired)
			try {
				WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
				elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
			} catch (Exception ae) {
			}

		else
			elements = getDriver().findElements(by);
		return elements;
	}

	public String getAttribute(String locator, boolean isWaitRequired, String attributeName) {
		return getElement(locator, isWaitRequired).getAttribute(attributeName);
	}

	public void setText(String locator, boolean isWaitRequired, String text) {
		WebElement element = getElement(locator, isWaitRequired);
		if (element.isEnabled()) {
			element.sendKeys(text);

		} else
			System.out.println(locator + " element is not enabled");
	}

	public void setText(WebElement element, boolean isWaitRequired, String text) {
		if (element.isEnabled()) {
			element.sendKeys(text);

		} else
			System.out.println("element is not enabled");
	}

	public boolean waitUntilElementIsClickable(WebElement element) {
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean waitUntilElementIsVisible(WebElement element) {
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
			wait.until(ExpectedConditions.visibilityOf(element));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isElementClickable(String locator, boolean isWaitRequired) {
		WebElement element = getElement(locator, isWaitRequired);
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(3));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void clickOnElement(String locator, boolean isWaitRequired) {
		WebElement element = getElement(locator, isWaitRequired);
		if (waitUntilElementIsClickable(element))
			element.click();
		else
			throw new ElementNotInteractableException(locator + " is not clickable");

	}

	public void clickOnElementAfterVisible(String locator, boolean isWaitRequired) {
		WebElement element = getElement(locator, isWaitRequired);
		if (waitUntilElementIsVisible(element))
			element.click();
		else
			throw new ElementNotInteractableException(locator + " is not visible");

	}

	public void clickOnElementUsingAction(String locator, boolean isWaitRequired) {
		WebElement element = getElement(locator, isWaitRequired);
		Actions actions = new Actions(getDriver());
		actions.moveToElement(element).click().build().perform();

	}

	public void clickOnElementUsingJavaScript(String locator, boolean isWaitRequired) {
		WebElement element = getElement(locator, isWaitRequired);
		JavascriptExecutor executor = (JavascriptExecutor) getDriver();
		executor.executeScript("arguments[0].click();", element);

	}

	public void clickOnElement(WebElement element, boolean isWaitRequired) {
		if (waitUntilElementIsClickable(element))
			element.click();
		else
			throw new ElementNotInteractableException(" Element is not clickable");

	}

	public void pressEnterKey() {
		Actions actions = new Actions(getDriver());
		actions.sendKeys(Keys.ENTER).perform();

	}

	public void pressDownArrowKey() {
		Actions actions = new Actions(getDriver());
		actions.sendKeys(Keys.ARROW_DOWN).perform();

	}

	protected boolean waitUntilPageTitalToBe(String tital) {
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
		boolean titalFlag = wait.until(ExpectedConditions.titleIs(tital));
		return titalFlag;
	}

	protected List<WebElement> waitUntilNumberOfElementToBoMoreThan(String locator, int count) {
		By by = getLocatorBy(locator);
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
		List<WebElement> elements = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(by, count));
		return elements;
	}

	protected boolean isElementDisplayed(String locator, boolean isWaitRequired) {
		try {
			WebElement element = getElement(locator, isWaitRequired);
			if (element.isDisplayed())
				return true;
			else
				// call method to scroll to element
				return element.isDisplayed();
		} catch (NoSuchElementException ne) {
			return false;
		}
	}

	public void waitUntilElementIsVisible(String locator) {
		isElementDisplayed(locator, true);
	}

	public boolean isElementVisible(String locator) {
		return isElementDisplayed(locator, true);
	}

	public void waitUntilElementIsInVisible(String locator) {
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
		wait.until(ExpectedConditions.invisibilityOf(getElement(locator, true)));
	}

	public String getElementText(String locator, boolean isWaitRequired) {
		return getElement(locator, isWaitRequired).getText();
	}

	public List<String> getAllElementsText(String locator, boolean isWaitRequired) {
		List<WebElement> elements = getAllElements(locator, isWaitRequired);
		List<String> AllElementstext = new ArrayList<String>();
		for (int i = 0; i < elements.size(); i++)
			AllElementstext.add(elements.get(i).getText());
		return AllElementstext;
	}

	public String getElementText(WebElement element) {
		return element.getText();
	}

	public void selectByText(WebElement element, String valueToSelect) {
		select = new Select(element);
		select.selectByVisibleText(valueToSelect);
	}

	public void scrollToElement() {
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		js.executeScript("scrollBy(0, 4500)");
	}

	public byte[] takeScreenshot() {
		TakesScreenshot ts = (TakesScreenshot) getDriver();
		return ts.getScreenshotAs(OutputType.BYTES);
	}

}
