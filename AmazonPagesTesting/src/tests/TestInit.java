package tests;

import utilities.*;
import org.testng.annotations.Test;

import amazonPages.HomePage;

import org.testng.annotations.BeforeSuite;
import java.util.concurrent.TimeUnit;

import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;

public class TestInit {
	
	protected WebDriver driver;	
	protected String baseUrl = "http://www.amazon.com";
	
		
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
	
	@AfterSuite
	public void AfterSuite() {
		driver.quit();
	}

}
