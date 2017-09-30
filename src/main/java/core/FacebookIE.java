package core;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FacebookIE {

    static WebDriver driver;

    public static void main(String[] args) throws InterruptedException {

	Logger logger = Logger.getLogger("");
	logger.setLevel(Level.OFF);

	String driverPath = "./resources/webdrivers/pc/IEDriverServer32.exe";

	String url = "https://facebook.com/";
	String email_address = "testusera056@gmail.com";
	String password = "passwordForUser056";

	if (!System.getProperty("os.name").contains("Windows")) {
	    throw new IllegalArgumentException("Internet Explorer is available only on Windows");
	}

	DesiredCapabilities IEDesiredCapabilities = DesiredCapabilities.internetExplorer();
	IEDesiredCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
		true);
	IEDesiredCapabilities.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "");
	IEDesiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
	IEDesiredCapabilities.setJavascriptEnabled(true);
	IEDesiredCapabilities.setCapability("enablePersistentHover", false);

	System.setProperty("webdriver.ie.driver", driverPath);
	driver = new InternetExplorerDriver(IEDesiredCapabilities);
	// driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	WebDriverWait wait = new WebDriverWait(driver, 10);
	driver.manage().window().maximize();

	driver.get(url);

	// Pause in milleseconds (1000 – 1 sec)

	String title = driver.getTitle();
	String copyright = driver.findElement(By.xpath("//*[@id=\'pageFooter\']/div[3]/div/span")).getText();

	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email"))).clear();

	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email"))).sendKeys(email_address);

	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("pass"))).sendKeys(password);

	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginbutton"))).click();

	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='u_0_a']/div[1]/div[1]/div/a/span")))
		.click();

	String friends = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[2]/ul/li[3]/a/span")))
		.getText();

	if (friends.isEmpty()) {
	    friends = "0";
	}

	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userNavigationLabel"))).click();
	//
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Log Out"))).click();

	driver.quit();

	System.out.println("Browser: IE");
	System.out.println("Title of the page: " + title);
	System.out.println("Copyright: " + copyright);
	System.out.println("Friends: You have " + friends + " friends");
    }

    private static void enableChkBox(WebElement param) {
	if (!param.isSelected()) {
	    System.out.println("Checkbox is toggled Off. Turning it On");
	    param.click();
	}
    }
}
