/**
 * @author Mawaddah Hanbali
 */
package ass2W10D2;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DataProviderTest {
	private static WebDriver driver;

	@DataProvider(name = "Authentication")
	public static Object[][] credentials() {

		// The number of times data is repeated, test will be executed the same no. of
		// times
		// Here it will execute two times
		return new Object[][] { { "testuser_1", "Test@123" }, { "testuser_2", "Test@123" } };

	}

	// Here we are calling the Data Provider object with its Name
	@Test(dataProvider = "Authentication")
	public void test(String sUsername, String sPassword) throws InterruptedException {

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();

		new WebDriverWait(driver, Duration.ofSeconds(10));

		driver.get("https://demoqa.com/books");
		
		driver.findElement(By.xpath("//button[@id='login']")).click();
		Thread.sleep(3000);
		
		// Argument passed will be used here as String Variable

		driver.findElement(By.id("userName")).sendKeys(sUsername);
		System.out.println(sUsername);

		driver.findElement(By.xpath("//input[@id='password']")).sendKeys(sPassword);
		System.out.println(sPassword);

		driver.findElement(By.id("login")).click();
		Thread.sleep(3000);

		System.out.println("Login Successfully, now it is the time to Log Off buddy.");

		driver.findElement(By.xpath("//button[normalize-space()='Log out']")).click();
		Thread.sleep(3000);

		driver.quit();

	}
}
