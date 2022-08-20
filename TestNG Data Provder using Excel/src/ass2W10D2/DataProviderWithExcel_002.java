/**
 * @author Mawaddah Hanbali
 */
package ass2W10D2;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DataProviderWithExcel_002 {
	private String sTestCaseName;
	private int iTestCaseRow;
	WebDriver driver;

	@BeforeMethod
	public void beforeMethod() throws Exception {

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();

		new WebDriverWait(driver, Duration.ofSeconds(10));
		driver.get("https://demoqa.com/books");
	}

	@Test(dataProvider = "Authentication")
	public void f(String sUserName, String sPassword) throws InterruptedException {

		driver.findElement(By.xpath("//button[@id='login']")).click();
		Thread.sleep(3000);

		driver.findElement(By.id("userName")).sendKeys(sUserName);
		System.out.println(sUserName);

		driver.findElement(By.xpath("//input[@id='password']")).sendKeys(sPassword);
		System.out.println(sPassword);

		driver.findElement(By.id("login")).click();
		Thread.sleep(3000);

		System.out.println("Login Successfully, now it is the time to Log Off buddy.");

		driver.findElement(By.xpath("//button[normalize-space()='Log out']")).click();
		Thread.sleep(3000);

	}

	@AfterMethod
	public void afterMethod() {
		driver.close();
	}

	@DataProvider
	public Object[][] Authentication() throws Exception {

		// Setting up the Test Data Excel file
		ExcelUtils.setExcelFile("C:/Users/lo0ol/Downloads/TestData.xlsx", "Sheet1");
		
		sTestCaseName = this.toString();
		System.out.println("The sTestCaseName is " + sTestCaseName);

		// From above method we get long test case name including package and class name
		// etc.
		// The below method will refine your test case name, exactly the name use have
		// used
		sTestCaseName = ExcelUtils.getTestCaseName(this.toString());

		// Fetching the Test Case row number from the Test Data Sheet
		// Getting the Test Case name to get the TestCase row from the Test Data Excel
		// sheet

		iTestCaseRow = ExcelUtils.getRowContains(sTestCaseName, 0);
		System.out.println("The iTestCaseRow is " + iTestCaseRow);

		Object[][] testObjArray = ExcelUtils.getTableArray("C:/Users/lo0ol/Downloads/TestData.xlsx", "Sheet1",
				iTestCaseRow);

		return (testObjArray);
	}
}
