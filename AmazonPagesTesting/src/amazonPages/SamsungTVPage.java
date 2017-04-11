package amazonPages;

import java.util.HashMap;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import utilities.TestDefines;

public class SamsungTVPage {
	private WebDriver driver;
	By starRating;
	By productName; /* the product name located by the header h2.
						I am using this instead of the link itself so that
						this is a lesser chance of this getting changed than 
						broken links.
					*/
	By productLink; /* the locator for the same element as productName, but using the link
						here so that navigation to the page resulting by clicking on it
						is possible.
					*/
	By resultCnt = By.id("s-result-count"); // the result displayed on top of the page after hitting search.
	By productTitle = By.id("productTitle"); // the product title page on clicking on the first product after search.
	
	// The ProductDetailsPage.
	ProductDetailsPage productDetailsPage;
	
	// The SharedMethods class.
	SharedMethods sharedMethods;
	
	// the result of number of results returned after search.
	String countStr;
	
	/* The map of of product name and star rating for every product 
	 * displayed on the first page of search.
	 */
	Map<String,String> productRatingMap;
	
	public SamsungTVPage(WebDriver driver) {
		this.driver = driver;
		sharedMethods = new SharedMethods(driver);
	}
	
	/* Checks for presence of product name for all items
	 * displayed on the first page and the star rating.
	 * Will return false if any of them is missing.
	 */
	public void GetStarRatingForProduct() {
		String starRatingStr = null;
		String productNameStr = null;
		int count = GetCountFromString();
		productRatingMap = new HashMap<>(count); /*dont know why I cant use new Map<>(count).
													Leaving this for a later study.
												*/
		
		for(int i = 0; i < count; i++) {
			productName = By.xpath(".//*[@id='result_"+i+"']/div/div/div"
					+ "/div[contains(@class,'a-col-right')]/descendant::h2");
			starRating = By.xpath(".//*[@id='result_"+i+"']/div/div/div/div[contains(@class,'a-col-right')]"
					+ "/div[contains(@class,'a-row')]/div[contains(@class,'a-span-last')]"
					+ "/descendant::i[contains(@class,'a-icon-star')]/child::span");
			
			/* I have to use getAttribute("innnerHTML") instead of getText() since the element is 'span' 
			 * and using getText() straight away does not return the text within.
			 */
			try {
				starRatingStr = driver.findElement(starRating).getAttribute("innerHTML");
			}
			catch(Exception NoSuchElementException) {
				starRatingStr = "No star rating displayed";
			}
			
			/* I am using a second try-catch instead of clubbing it together with 
			 * the previous one to get individualized error messages.
			 * Is there a better way to do this?
			 */
			try {
				productNameStr = driver.findElement(productName).getText(); 
			}
			catch(Exception NoSuchElementException) {
				productNameStr = "Product name not identified. "
						+ "The name may have been removed or the header size change from h2.";
			}
						
			productRatingMap.put(productNameStr, starRatingStr);
		}
		
		// Log the count of products on the page.
		sharedMethods.LogDetails("The number of results displayed on the page are " 
				+ countStr + " <br>");
		
		// Log the star ratings of all the products listed on the page.
		sharedMethods.LogDetails("<b>The star rating of each of the products on the "
				+ "page are as follows:</b>");
		sharedMethods.LogDetails(productRatingMap);
	}
	
	public boolean HasResultCnt(String searchTxt) {
		boolean hasResult = false;
		String resultStr = GetSearchResultCntStr();
		countStr = null;
		
		try {
			Pattern p = Pattern.compile(TestDefines.searchPattern1);
			Matcher m = p.matcher(resultStr);
			if(m.find()) {
				countStr = m.group(2);
				hasResult = true;
			}
		}
		catch(Exception ex) {
			sharedMethods.LogDetails("Problem reading the result count. Possbile reason "
					+ "could be result string not having numbers.");
		}
		
		return hasResult;
	}
	
	public ProductDetailsPage NavigateToProductDetailsPage() {
		// We need the product link of only the first one on every search.
		productLink = By.xpath(".//*[@id='result_"+TestDefines.Zero+"']/div/div/div/"
				+ "div[contains(@class,'a-col-right')]/descendant::a");
		
		driver.findElement(productLink).click();
		
		// the next page.
		productDetailsPage = new ProductDetailsPage(driver);
		return productDetailsPage;
	}
	/* Private methods. */

	/* Get the count value from String.
	 * The string value may contain a range as 1-24 or simply 24.
	 */
	private int GetCountFromString() {
		int count = 0;
		String[] countStrSplitArray = countStr.split("-");
		if(countStrSplitArray.length > 1) {
			count = Integer.parseInt(countStrSplitArray[1]);
		}
		else count = Integer.parseInt(countStrSplitArray[0]);
		
		return count;
	}
	
	/* Return the string containing the number of results displayed on the page. */
	private String GetSearchResultCntStr() {
		
		String searchResult = null;
		
		// wait until the result is available.
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(resultCnt));
		
		searchResult = driver.findElement(resultCnt).getText();
		
		// remove the newlines and double-quotes.
		searchResult = searchResult.replaceAll("[\n\r]", "");
		searchResult = searchResult.replaceAll("\\\"", "");
		
		return searchResult;
	}
}
