package amazonPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import utilities.ElementsMap;

public class HomePage {
	
	private WebDriver driver;
	private ElementsMap elementsMap;
	WebElement logo;
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
	}
	
	public HomePage findAmazonLogo() {		
		
		// Read the home page locators from the properties file.
		//Get current working directory
		String workingDir = System.getProperty("user.dir");		
		elementsMap = new ElementsMap(workingDir + "\\resources\\webElements.properties");
		
	    try {
			logo = driver.findElement(elementsMap.getLocator("Amazon_Logo"));
			System.out.println("TEST");
			System.out.println(logo);
			
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		} catch (Exception e) {			
			e.printStackTrace();
		}
		return this;
	}
	
	public boolean SuccessMessage() {
		return logo.isDisplayed();
	}

}
