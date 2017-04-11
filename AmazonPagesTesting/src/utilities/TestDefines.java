package utilities;

public class TestDefines {
	public static final String CHROME_EXE = "C:\\Selenium\\ChromeDriver\\chromedriver.exe";
	public static final String baseUrl = "http://www.amazon.com";
	
	// Category selection strings on the home page.
	public static final String electronicsCat = "Electronics";
	
	// Regular expressions for pattern matching.
	// Right now, useful for the electronics page.
	public static final String searchPattern1 = "((([0-9]*)(\\-[0-9]*)?)(.*))";
	
	// Input data sheet constants for the xls sheet.
	public static final String fileName = "src//tests//TestData.xlsx";
	public static final String sheetName = "TestInputDataSheet";
	
	// A few numeric constants that are needed.
	public static final String Zero = "0";
	public static final String One = "1";
	public static final String Two = "2";
}
