package core;

import java.io.File;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FacebookFFluent {

    static WebDriver driver;

    public static void main(String[] args) throws InterruptedException {
	// Logger logger = Logger.getLogger("");
	// logger.setLevel(Level.OFF);

	String driverPath = "./resources/webdrivers/pc/geckodriver.exe";

	String url = "https://facebook.com/";
	String email_address = "testusera056@gmail.com";
	String password = "passwordForUser056";

	// add firebug to FF
	final String firebugPath = "C:/Users/Ivan/AppData/Roaming/Mozilla/Firefox/Profiles/x5r65ue5.default/extensions/firebug@software.joehewitt.com.xpi";
	FirefoxProfile profile = new FirefoxProfile();
	profile.addExtension(new File(firebugPath));

	if (System.getProperty("os.name").toUpperCase().contains("MAC"))
	    driverPath = "./resources/webdrivers/mac/geckodriver.sh";
	else if (System.getProperty("os.name").toUpperCase().contains("WINDOWS"))
	    driverPath = "./resources/webdrivers/pc/geckodriver.exe";
	else
	    throw new IllegalArgumentException("Unknown OS");

	System.setProperty("webdriver.gecko.driver", driverPath);
	driver = new FirefoxDriver(profile);
	// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	driver.manage().window().maximize();
	WebDriverWait wait = new WebDriverWait(driver, 10);
	driver.get(url);

	// Pause in milleseconds (1000 – 1 sec)

	String title = driver.getTitle();
	String copyright = wait.until(
		ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\'pageFooter\']/div[3]/div/span")))
		.getText();

	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email"))).clear();

	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email"))).sendKeys(email_address);

	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("pass"))).sendKeys(password);

	// use data-* attribute for password
	// driver.findElement(By.cssSelector("[data-testid='royal_pass']")).sendKeys(password);

	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginbutton"))).click();

	for (int i = 0; i < 2; i++) {
	    driver.navigate().refresh();
	}
	WebElement abc = driver.findElement(By.linkText("Not Now"));
	if (abc != null) {
	    abc.click();
	}

	// if (wait.until(ExpectedConditions.alertIsPresent()) != null) {
	// driver.findElement(By.linkText("Not Now")).click();
	// }

	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='u_0_a']/div[1]/div[1]/div/a/span")))
		.click();

	String friends = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[2]/ul/li[3]/a/span")))
		.getText();
	System.out.println(friends);
	if (friends.isEmpty()) {
	    friends = "0";
	}

	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userNavigationLabel"))).click();

	// driver.findElement(By.partialLinkText("Log Out")).click();

	wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Log Out"))).click();

	driver.quit();

	System.out.println("Browser: Firefox");
	System.out.println("Title of the page: " + title);
	System.out.println("Copyright: " + copyright);
	// System.out.println("Friends: You have " + friends + " friends");
    }

}
