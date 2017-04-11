package amazonPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ReviewsPage {
	private WebDriver driver;
	By custReviewsTitle = By.xpath(".//*[@id='cm_cr-product_info']");
						/* The title block that shows "Customer Reviews". */
	
	By positiveReviewHeader = By.xpath(".//*[@id='cm_cr-rvw_summary-viewpoints']"
			+ "/descendant::div[contains(@class,'positive-review')]"
			+ "/descendant::div[contains(@class,'a-expander-content a-expander-partial-collapse-content')]"
			+ "/descendant::h4");
	By positiveReview = By.xpath(".//*[@id='cm_cr-rvw_summary-viewpoints']"
			+ "/descendant::div[contains(@class,'positive-review')]"
			+ "/descendant::div[contains(@class,'a-expander-content a-expander-partial-collapse-content')]"
			+ "/descendant::div[contains(@class,'a-row a-spacing-top-mini')]"
			+ "/span[contains(@class,'a-size-base')]");
	
	By criticalReviewHeader = By.xpath(".//*[@id='cm_cr-rvw_summary-viewpoints']"
			+ "/descendant::div[contains(@class,'critical-review')]"
			+ "/descendant::div[contains(@class,'a-expander-content a-expander-partial-collapse-content')]"
			+ "/descendant::h4");
	By criticalReview = By.xpath(".//*[@id='cm_cr-rvw_summary-viewpoints']"
			+ "/descendant::div[contains(@class,'critical-review')]"
			+ "/descendant::div[contains(@class,'a-expander-content a-expander-partial-collapse-content')]"
			+ "//descendant::div[contains(@class,'a-row a-spacing-top-mini')]"
			+ "/span[contains(@class,'a-size-base')]");
	
	// SharedMethods class.
	SharedMethods sharedMethods;
	
	public ReviewsPage(WebDriver driver) {
		this.driver = driver;
		sharedMethods = new SharedMethods(driver);
	}
	
	public boolean IsCustReviewsTitleDisplayed() {
		sharedMethods.WaitForLoading(custReviewsTitle, 20);
		return sharedMethods.ElementPresent(custReviewsTitle);
	}
	
	public void PrintTopReviews() {
		GetTopReview(positiveReview, positiveReviewHeader);
		GetTopReview(criticalReview, criticalReviewHeader);
	}
	
	/* Private Methods. */	
	private void GetTopReview(By reviewLocator, By headerLocator) {
		String reviewreviewTxt = driver.findElement(reviewLocator).getText();
		sharedMethods.LogDetails("<b>" + 
				driver.findElement(headerLocator).getText() + "</b>");
		sharedMethods.LogDetails(reviewreviewTxt);
	}
}
