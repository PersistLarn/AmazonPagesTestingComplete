package tests;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import amazonPages.ElectronicsPage;
import amazonPages.HomePage;
import amazonPages.SamsungTVPage;
import amazonPages.ProductDetailsPage;
import amazonPages.ReviewsPage;
import utilities.TestDefines;

public class TestInit {
	
	protected WebDriver driver;	
	HomePage homePage;
	ElectronicsPage electronicsPage;
	SamsungTVPage samsungTVPage;
	ProductDetailsPage productDetailsPage;
	ReviewsPage reviewsPage;
	
	@BeforeSuite
	public void BeforeSuite() {
		try {
			//Initialize the chrome driver before testing
			System.setProperty("webdriver.chrome.driver", TestDefines.CHROME_EXE);
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@Test
	public void Init() {
		Reporter.log("Navigating to amazon home page");
		homePage = new HomePage(driver);
		
		try {
			driver.get(TestDefines.baseUrl);
		}
		catch(Exception ex) {
			Reporter.log("Cannot open amazon homepages.\n Please check the following message.");
			Reporter.log(ex.getMessage());
		}
	}
	
	@AfterSuite
	public void AfterSuite() {
		driver.quit();
	}

}

