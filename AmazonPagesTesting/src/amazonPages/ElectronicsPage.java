package amazonPages;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import utilities.TestDefines;

public class ElectronicsPage {
	
	private WebDriver driver;
	By searchInputBox = By.id("twotabsearchtextbox"); //the text box for the user to type in search text.
	By searchBtn = By.cssSelector("input.nav-input");
	By resultCnt = By.id("s-result-count"); // the result displayed on top of the page after hitting search.
	
	// The next page.
	SamsungTVPage samsungTVPage;
	
	public ElectronicsPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public SamsungTVPage SendSearchText(String searchText)  {
		driver.findElement(searchInputBox).clear(); //clear the previous text, if any.
		driver.findElement(searchInputBox).sendKeys(searchText);
		driver.findElement(searchBtn).click(); //click the search button
		samsungTVPage = new SamsungTVPage(driver);
		
		// wait until the result is available.
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(resultCnt));
				
		return samsungTVPage;
	}
	
	/* Return the string containing the number of results displayed on the page. */
	private String GetSearchResultCntStr() {
		
		String searchResult = null;
		
		searchResult = driver.findElement(resultCnt).getText();
		
		// remove the newlines and double-quotes.
		searchResult = searchResult.replaceAll("[\n\r]", "");
		searchResult = searchResult.replaceAll("\\\"", "");
		
		return searchResult;
	}
}
