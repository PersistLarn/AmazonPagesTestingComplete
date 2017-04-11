package tests;

import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertTrue;

import amazonPages.HomePage;

public class TestPages extends TestInit{
	
	@Test
	public void TestHomePage() {
		
		// navigate to amazon home page.
		driver.get(baseUrl);
		
		HomePage homePage = new HomePage(driver);
		homePage.findAmazonLogo();
		assertTrue(homePage.findAmazonLogo().SuccessMessage());
	}
}
