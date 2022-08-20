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

public class DataProviderWithExcel_001 {
	WebDriver driver;

	@BeforeMethod
	public void beforeMethod() throws Exception {

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();

		new WebDriverWait(driver, Duration.ofSeconds(10));
		driver.get("https://demoqa.com/books");

	}

	@Test(dataProvider = "Authentication")
	public void Registration_data(String sUserName, String sPassword) throws Exception {
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

	@DataProvider
	public Object[][] Authentication() throws Exception {
		
		Object[][] testObjArray = ExcelUtils.getTableArray("C:/Users/lo0ol/Downloads/TestData.xlsx", "Sheet1");
		return (testObjArray);
	}

	@AfterMethod
	public void afterMethod() {
		driver.close();
	}

}
