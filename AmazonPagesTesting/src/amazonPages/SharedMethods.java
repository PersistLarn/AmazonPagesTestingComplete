package amazonPages;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

/* This class contains shared methods used by all of the classes. */
public class SharedMethods {
	private WebDriver driver;
	
	public SharedMethods(WebDriver driver) {
		this.driver = driver;
	}
	
	public void WaitForLoading(By element, int secs) {
		// wait until the result is available.
		WebDriverWait wait = new WebDriverWait(driver, secs);
		wait.until(ExpectedConditions.visibilityOfElementLocated(element));
	}
	
	/* Tests the existence of element locators. */
	public boolean ElementPresent(By element) {
		return driver.findElement(element).isDisplayed();
	}
	
	/* Print the details of page elements. */
	/*
	 * This method simply logs the input string.
	 */
	public void LogDetails(String logStr) {
		Reporter.log(logStr + " \n<br>");
	}
	
	/*
	 * This overloaded method loops through a map and prints the values.
	 */
	public void LogDetails(Map<String,String> map) {
		for(String key: map.keySet()){
            String value = map.get(key);  
            Reporter.log(key + ": " + value + "\n<br>");  
		}
		//Want an extra line break after a mapping is recorded.
		Reporter.log("\n<br>");
	}
}
