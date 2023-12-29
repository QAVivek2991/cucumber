package base;



import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ThreadGuard;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import io.github.bonigarcia.wdm.WebDriverManager;

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
	public void configureSelenium(String browser, Boolean useWebDriverManager) {
		//String browser = Strings.isNullOrEmpty(System.getProperty("browserName")) ? "chrome": System.getProperty("browserName");
		System.out.println("Browser - "+ System.getProperty("browser"));
		
		if(useWebDriverManager==true) {
			switch (browser.toLowerCase()) {
			case "chrome":
				WebDriverManager.chromedriver().setup();
				webDriver.set(ThreadGuard.protect(new ChromeDriver()));
				//webDriver = new ChromeDriver();
				break;
			case "firefox":
				WebDriverManager.firefoxdriver().setup();
				webDriver.set(ThreadGuard.protect(new FirefoxDriver()));
				//webDriver = new FirefoxDriver();
				break;
			case "edge":
				WebDriverManager.edgedriver().setup();
				webDriver.set(ThreadGuard.protect(new EdgeDriver()));
				//webDriver = new EdgeDriver();
				break;
			default:
				WebDriverManager.edgedriver().setup();
				webDriver.set(ThreadGuard.protect(new EdgeDriver()));
				//webDriver = new EdgeDriver();
				break;
		}
		}else {
			switch (browser.toLowerCase()) {
			case "chrome":
				System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
				webDriver.set(ThreadGuard.protect(new ChromeDriver()));
				//webDriver = new ChromeDriver();
				break;
			case "firefox":
				System.setProperty("webdriver.gecko.driver", "path/to/geckodriver");
				webDriver.set(ThreadGuard.protect(new FirefoxDriver()));
				//webDriver = new FirefoxDriver();
				break;
			case "edge":
				System.setProperty("webdriver.edge.driver", "path/to/msedgedriver");
				webDriver.set(ThreadGuard.protect(new EdgeDriver()));
				//webDriver = new EdgeDriver();
				break;
			default:
				System.setProperty("webdriver.edge.driver", "path/to/msedgedriver");
				webDriver.set(ThreadGuard.protect(new EdgeDriver()));
				//webDriver = new EdgeDriver();
				break;
			
		}
	}
	}

	public void openWebSite(String url) {
		configureSelenium("edge", true);
		getDriver().manage().window().maximize();
		getDriver().get(url);
	}

	public void start() {
		openWebSite("https://staging.connect.pressurepro.us/");
	}

	public void tearDown() {
		getDriver().close();
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
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
		element = wait.until(ExpectedConditions.elementToBeClickable(elementBy));
		return element;
	}

	public WebElement getElement(String locator, boolean isWaitRequired) {
		By elementBy = getElementBy(locator);
		WebElement element = null;
		if (isWaitRequired == true) {
			WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(elementBy));
		} else {
			element = getDriver().findElement(elementBy);
		}

		return element;
	}

	public List<WebElement> getElements(String locator, boolean isWaitRequired) {
		By elementBy = getElementBy(locator);
		List<WebElement> elements = null;
		if (isWaitRequired == true) {
			WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
			elements = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(elementBy, 0));
		} else {
			elements = getDriver().findElements(elementBy);
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
	
	public byte[] takeScreenshot() {
		TakesScreenshot ts = (TakesScreenshot)getDriver();
		return ts.getScreenshotAs(OutputType.BYTES);
	}

}
