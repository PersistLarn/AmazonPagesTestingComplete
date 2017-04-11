package tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.*;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTSheetDimension;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import utilities.TestDefines;

public class TestPages extends TestInit{
	
	
	@Test(priority=0)
	public void TestSelectLogo() {
		Reporter.log("Checking presence of Amazon Log on home page");
		Assert.assertTrue(homePage.SelectLogo());		
	}
	
	
	@Test(priority=1)
	public void TestNavigateElelctronicsPage() {
		Reporter.log("Selecting Electronics page");
		electronicsPage = homePage.NavigateElelctronicsPage();
	}	
	
	/* Parameterize input from data sheet TestData.ods. */
	/* Since the requirement is to test data with multiple products on a single page
	 * and repeat it for a set of data, all the checks within have to come into this
	 * single method and hence this method tests and checks lot of activities.
	 */
	@Test(priority=2, dataProvider="inputData")
	public void TestSendSearchText1(String product, String model, String dpSize) {
		
		String searchTxt = product + ":" + model + ":" + dpSize; 
		Reporter.log("Input search string: " + searchTxt + "\n<br>");
		
		// set1
		samsungTVPage = electronicsPage.SendSearchText(searchTxt);
		
		// Test the result numbers returned.
		Assert.assertEquals(samsungTVPage.HasResultCnt(searchTxt),true);
		samsungTVPage.GetStarRatingForProduct();
		
		// Click on the first product title.
		/*
		 * This can be enhanced in future to run in a loop to get details of
		 * each product listed. Relevant changes should be made on ProductDetailsPage.
		 */
		productDetailsPage = samsungTVPage.NavigateToProductDetailsPage();
		
		//Test the presence of the product title.
		Assert.assertEquals(productDetailsPage.GetProductTitle(), true);
		
		// Test the product features display.
		productDetailsPage.GetProductFeatures();
		
		//Test the product pricing details display.
		productDetailsPage.GetPricingDetails();
		
		// Verify the display of star rating.
		Assert.assertEquals(productDetailsPage.IsStarRatingDisplayed(), true);
		
		// Navigate to the reviews page.
		reviewsPage = productDetailsPage.NavigateToReviewsPage();
		
		// Verify the "Customer Reviews" title is displayed.
		Assert.assertEquals(reviewsPage.IsCustReviewsTitleDisplayed(), true);
		
		// Test top reviews for the product.
		reviewsPage.PrintTopReviews();
	}
	
	/* Data methods - involving xlsx */
	@DataProvider(name = "inputData")
	public Object[][] inputData() {
		Object[][] arrayObject = GetExcelData(TestDefines.fileName,TestDefines.sheetName);
		return arrayObject;
	}
	
	/* Read paraeterized input data from the xls file. */
	public String[][] GetExcelData(String fileName, String sheetName) {
		String[][] arrayExcelData = null;

		try {
			FileInputStream fs = new FileInputStream(fileName);
			XSSFWorkbook wb = new XSSFWorkbook(fs);
			XSSFSheet sh = wb.getSheet(sheetName);
			DataFormatter objDefaultFormat = new DataFormatter();
			FormulaEvaluator objFormulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) wb);

			int totalRows = sh.getLastRowNum() - sh.getFirstRowNum();

			/* The following manipulation to get the sheet max columns.
			 * Not actually required here since I need to be extracting just the 1st
			 * to the 3nd column i.e., column id = 1 to 3, ignoring 0.
			 */
			Row r1 = sh.getRow(1);
			int totalCols = r1.getLastCellNum();

			arrayExcelData = new String[totalRows][totalCols-1];
			
			for (int i = 1 ; i <= totalRows; i++) {
				for (int j=1; j < totalCols; j++) { /* right now, the inputs are only the model and the display size.
														Ignore the first column since it contains the name of the test case.
				 									*/
					Row r = sh.getRow(i);
					Cell cellValue = r.getCell(j); 
					String cellValueStr;
					
					cellValueStr = objDefaultFormat.formatCellValue(cellValue,objFormulaEvaluator);
					
					arrayExcelData[i-1][j-1] = cellValueStr;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			e.printStackTrace();
		}
		return arrayExcelData;
	}
}
