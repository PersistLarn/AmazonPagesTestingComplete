package amazonPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import utilities.TestDefines;

public class HomePage {

	private WebDriver driver;
	ElectronicsPage electronicsPage;
	
	// the element locators on the home page.
	//By logo = By.className("nav-logo-base.nav-sprite");	// this did not work; hence needed to change to cssSelector.
	By logo = By.cssSelector("span.nav-logo-base.nav-sprite"); //the logo on home page.
	By categorySelect = By.id("searchDropdownBox");
	By searchGlass = By.className("nav-input");
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
	}
	
	public boolean SelectLogo() {
		driver.findElement(logo);
		return ElementPresent(logo);
	}
	
	public ElectronicsPage NavigateElelctronicsPage() {
		// select the category input from the category selection drop down in the home page.
		Select catDropDown = new Select(driver.findElement(categorySelect));
		catDropDown.selectByVisibleText(TestDefines.electronicsCat);
		
		// Click on the search magnifier glass.
		driver.findElement(searchGlass).click();
		electronicsPage = new ElectronicsPage(driver);
		return electronicsPage;
	}
	
	/* May be this methods is really not required? - 
	 * check again during review. 
	 */
	private boolean ElementPresent(By element) {
		return driver.findElement(element).isDisplayed();
	}
	
}
