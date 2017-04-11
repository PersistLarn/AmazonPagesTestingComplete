package amazonPages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.TestDefines;

public class ProductDetailsPage {

	private WebDriver driver;
	By productTitle = By.id("productTitle"); // the product title page on clicking on the first product after search.
	By productPricingRows = By.xpath(".//*[@id='price']/table/descendant::tr"); // The number of rows in product pricing.
	/* I need to expand further to each table column from the productPricingRows. 
	 * Hence I need a separate string for this.
	 */
	By productFeature = By.xpath(".//*[@id='featurebullets_feature_div']/descendant::ul/descendant::li");
									/* The product features listed as listing. */
	
	By pricing = By.xpath(".//*[@id='olp_feature_div']/descendant::span");
	
	String productPricingRowsStr = ".//*[@id='price']/table/descendant::tr"; 
	By productPricingCols = By.xpath(".//*[@id='price']/table/descendant::tr[1]/td"); // The number of rows in product pricing.
	
	By priceTdKey; /*every price attribute name, like price of the table 
					in the product pricing.
					*/
	By priceTdValue; /* every price value, like $200 for the above key
						of the table in the product listing.
					*/
	By starRating = By.xpath(".//*[@id='bottomRow']/ancestor::div[@id='dp-container']"
			+ "/descendant::div[@id='averageCustomerReviews']/descendant::span[@id='acrPopover']"
			+ "/span[" + TestDefines.One + "]/a/i[" + TestDefines.One + "]");
					/* The star rating of the product in this page.
					 * I have take this back up the DOM since I have already come beyond the star-rating.
					*/
	By reviews = By.xpath(".//*[@id='a-popover-content-2']"
			+ "/descendant::div[contains(@class,'a-section a-spacing-none a-text-center')]/a");
					/* The link from the pop-up on hovering over star rating 
					 * to reviews page.
					 */
	
	// ReviewsPage.
	ReviewsPage reviewsPage;
	
	// The SharedMethods class.
	SharedMethods sharedMethods;
	
	// Price details map.
	Map<String,String> priceDetailsMap;
	
	public ProductDetailsPage(WebDriver driver) {
		this.driver = driver;
		sharedMethods = new SharedMethods(driver);
	}
	
	public boolean GetProductTitle() {
		// wait for the page to load.
		sharedMethods.WaitForLoading(productTitle, 20);
		return sharedMethods.ElementPresent(productTitle);
	}
	
	public void GetProductFeatures() {
		int count = driver.findElements(productFeature).size();
		
		/* This would be better on performance to get all of them at one shot and store
		 * in an array and retrieve later. But the only way I can check that an element is hidden is 
		 * by iterating each of it for the class having hidden. There should be a better way of 
		 * doing this.
		 */
		
		// log the header to report output.
		sharedMethods.LogDetails("<br><b>Features of the product as listed</b>");
		for(int i = 1; i <= count; i++) {
			//ignore hidden lists.
			
			By classTag = By.xpath(".//*[@id='featurebullets_feature_div']/descendant::ul/descendant::li" + 
					 "[" + i + "]" + "[contains(@class,'hidden')]");
			try {
				if(sharedMethods.ElementPresent(classTag)) { /* the listing with 
										hidden tag should not be displayed as a feature as it is not 
										shown on the UI. 
										*/
					continue; //we dont need this element, hence skip.
					
				}
			}
			catch(NoSuchElementException nElEx)	{
				// Do not log this.
				
			}
				
			By productListing = By.xpath(".//*[@id='featurebullets_feature_div']/descendant::ul/descendant::li" +
					"[" + i + "]/span");
			String productFeatureTxt = driver.findElement(productListing).getText();
			System.out.println(productFeatureTxt);
			sharedMethods.LogDetails(productFeatureTxt);
		}
	}
	
	public void GetPricingDetails() {
		sharedMethods.LogDetails("<br><b>Pricing Details</b>");
		int count = driver.findElements(pricing).size(); /*the number of spans that are used
		 													to display pricing details.
		 												*/
		
		
			String priceDetail = driver.findElement(By.xpath(".//*[@id='olp_feature_div']/descendant::"
					+ "span[" + TestDefines.One + "]/a")).getText();
			
			sharedMethods.LogDetails(priceDetail + "  ");
	}
	
	
	/* The pricing data stored in tables got changed to simple texts.
	 * I am retaining this method so that I can look back at the mapping 
	 * I have made. Hence, I am adding a new method GetPricingDetails.
	 */
	public void GetPriceDetails() {
		int rowsCount = GetPriceRowsCount();
		int colsCount = GetPriceColsCount();
		priceDetailsMap = new LinkedHashMap<>(rowsCount); /* I am using a LinkedHashMap here so that 
															the output of the map when I print comes
															out in the same order of input.
															It wouldn't look good for You save: $302
															before the List Price and the Price.
														*/
		for(int i = 1; i <= rowsCount; i++) {
			priceTdKey = By.xpath(productPricingRowsStr+"["+i+"]"+"/td["+TestDefines.One+"]");
			priceTdValue = By.xpath(productPricingRowsStr+"["+i+"]"+"/td["+TestDefines.Two+"]");
			
			String priceTdKeyStr = driver.findElement(priceTdKey).getText();
			String priceTdValueStr = driver.findElement(priceTdValue).getText();
			
			priceDetailsMap.put(priceTdKeyStr, priceTdValueStr);
		}
		
		// Log the results of pricing to output.
		sharedMethods.LogDetails("<b>The pricing details</b>");
		sharedMethods.LogDetails(priceDetailsMap);
	}
	
	public boolean IsStarRatingDisplayed() {
		return sharedMethods.ElementPresent(starRating);
	}
	
	public ReviewsPage NavigateToReviewsPage() {
		/*The following code to hover over the star ratings element to get to 
		 * 'See all verified purchase reviews'.
		 */
		/* 2/15/2017 - Right now, hovering on the star rating element generates 
		 * a javascript(0) error on chrome. Hence, I presume there is an issue 
		 * with the element itself since it was working alright earlier, which means 
		 * the test failed.
		 */
		try {
			Actions actions = new Actions(driver);
			WebElement starRatingEle = driver.findElement(starRating);
		
			actions.moveToElement(starRatingEle).build().perform();
			driver.findElement(reviews).click();
		}
		catch(Exception ex) {
			sharedMethods.LogDetails("Could not proceed to the next page. Possible issue with the star rating element");
		}
		
		// navigate to ReviewsPage.
		reviewsPage = new ReviewsPage(driver);
		return reviewsPage;
	}
	
	/* Private methods. */
	
	/* Retrieve the number of columns in the product's price_feature_div 
	 * with id price_feature_div. 
	 */
	private int GetPriceRowsCount() {
		return driver.findElements(productPricingRows).size();
	}
	
	private int GetPriceColsCount() {
		return driver.findElements(productPricingCols).size();
	}
}
