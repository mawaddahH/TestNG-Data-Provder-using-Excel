# TestNG-Data-Provder-using-Excel
Assignment 3 W10D2 - SDA - Software QA Bootcamp


# Table of contents
* [Question](#question)
* [Answer With Output Screenshots](#answer-with-output-screenshots)

---
# Question
Create an automation script using TestNG Data Provider using Excel.

- STEP 1: Create a test case for the Application with TestNG Data Provider.
- STEP 2: Create a Test Datasheet.
- STEP 3: Create functions to Open & Read data from Excel.
- STEP 4: Create a TestNg test case for accepting data from Excel using Data Provider.
- STEP 5: Run the test against the Test Case name in the Test Data file.

---

# Answer With Output Screenshots
## Set up

I used [demoqa-Book Store](https://demoqa.com/books) website
and I follow all the steps in [TestNG Data Provider with Excel](https://www.toolsqa.com/testng/testng-data-provider-excel/) website.


And before running the code, there are some steps that need to take considered:

### First:
Setup:
- [JDK](https://www.oracle.com/java/technologies/downloads/) (Lastest)
- [Eclipse](https://www.eclipse.org/) (Lastest)
- [Web Driver](https://chromedriver.chromium.org/downloads) for Chrome Driver (Lastest)

Donwload the necessary jar files:
- [Selenium](https://www.selenium.dev/downloads/) (Lastest).
- [TestNG](http://www.java2s.com/Code/Jar/t/Downloadtestng685jar.htm) (Lastest).
- [webdrivermanager](https://jar-download.com/artifact-search/webdrivermanager) (Lastest).
- [poi-ooxml](https://jar-download.com/artifact-search/poi-ooxml) (Lastest).

### Second:
Add them as a library in the classpath of the project
- _click-reight on the file project >Build path > configure Bild path > Java Build Path > Libraries > classpath > add external JARs > Apply and close_.


<p align="center">

https://user-images.githubusercontent.com/48597284/185722489-7fc51b9a-c8c6-44cc-860a-f23f30a9b49a.mp4

</p>

---

## STEP 1: Create a test case for the Application with TestNG Data Provider.
In this step, I used `WebDriverManager` to open the chrome webdriver

```md
WebDriverManager.chromedriver().setup();
driver = new ChromeDriver();
```

The final code for `DataProviderTest` class
```md
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

		driver.get("https://www.store.demoqa.com");
		
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
```

### Output Screenshots:

<p align="center">
<img src="https://user-images.githubusercontent.com/48597284/185724720-5bdd58f4-7fef-426f-bff0-2952515bf003.png" width=80% height=80%>

https://user-images.githubusercontent.com/48597284/185724749-b548c869-e13d-4887-8044-ffc1ce72b526.mp4

</p>



---

## STEP 2: Create a Test Datasheet.
Download the Excel file [TestData.xlsx](https://github.com/mawaddahH/TestNG-Data-Provder-using-Excel/files/9385946/TestData.xlsx)

<p align="center">
<img src="https://user-images.githubusercontent.com/48597284/185724601-f2c81f01-9f63-4102-b039-ed67d0cc3c94.png" width=40% height=40%>

</p>

---

## STEP 3: Create functions to Open & Read data from Excel.
Here I just followed the guide on the [TestNG Data Provider with Excel](https://www.toolsqa.com/testng/testng-data-provider-excel/) website, 
and I just added the `getTableArray` method two times with different argument.
```md
Object[][] testObjArray = ExcelUtils.getTableArray("C:/Users/lo0ol/Downloads/TestData.xlsx", "Sheet1", 1);
```

```md
public static Object[][] getTableArray(String FilePath, String SheetName)
```

```md
public static Object[][] getTableArray(String FilePath, String SheetName, int iTestCaseRow)
```


The code for `ExcelUtils` class
```md
public class ExcelUtils {
	private static XSSFSheet ExcelWSheet;
	private static XSSFWorkbook ExcelWBook;
	private static XSSFCell Cell;

	// This method is to set the File path and to open the Excel file, Pass Excel
	// Path and Sheetname as Arguments to this method

	public static void setExcelFile(String Path, String SheetName) throws Exception {
		try {
			// Open the Excel file
			FileInputStream ExcelFile = new FileInputStream(Path);

			// Access the required test data sheet
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);

		} catch (Exception e) {
			throw (e);
		}
	}

	public static Object[][] getTableArray(String FilePath, String SheetName) throws Exception {
		String[][] tabArray = null;
		try {
			FileInputStream ExcelFile = new FileInputStream(FilePath);

			// Access the required test data sheet
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);

			int startRow = 1;
			int startCol = 1;
			int ci, cj;
			int totalRows = ExcelWSheet.getLastRowNum();

			// you can write a function as well to get Column count
			int totalCols = 2;
			tabArray = new String[totalRows][totalCols];

			ci = 0;
			for (int i = startRow; i <= totalRows; i++, ci++) {
				cj = 0;

				for (int j = startCol; j <= totalCols; j++, cj++) {
					tabArray[ci][cj] = getCellData(i, j);
					System.out.println(tabArray[ci][cj]);
				}
			}
		}

		catch (FileNotFoundException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		}

		catch (IOException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		}
		return (tabArray);
	}

	public static Object[][] getTableArray(String FilePath, String SheetName, int iTestCaseRow) throws Exception {

		String[][] tabArray = null;
		try {
			FileInputStream ExcelFile = new FileInputStream(FilePath);

			// Access the required test data sheet
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);

			int startCol = 1;
			int ci = 0, cj = 0;
			int totalRows = 1;
			int totalCols = 2;

			tabArray = new String[totalRows][totalCols];
			for (int j = startCol; j <= totalCols; j++, cj++) {
				tabArray[ci][cj] = getCellData(iTestCaseRow, j);
				System.out.println(tabArray[ci][cj]);
			}
		}

		catch (FileNotFoundException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		}
		return (tabArray);

	}

	// This method is to read the test data from the Excel cell, in this we are
	// passing parameters as Row num and Col num
	public static String getCellData(int RowNum, int ColNum) throws Exception {
		try {
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			String CellData = Cell.getStringCellValue();
			return CellData;

		} catch (Exception e) {
			return "";
		}
	}

	public static String getTestCaseName(String sTestCase) throws Exception {
		String value = sTestCase;

		try {
			int posi = value.indexOf("@");
			value = value.substring(0, posi);
			posi = value.lastIndexOf(".");

			value = value.substring(posi + 1);
			System.out.println("The value is " + value);
			return value;

		} catch (Exception e) {
			throw (e);
		}
	}

	public static int getRowContains(String sTestCaseName, int colNum) throws Exception {
		int i;

		try {
			int rowCount = ExcelUtils.getRowUsed();
			System.out.println("The rowCount is " + rowCount);

			for (i = 0; i < rowCount; i++) {
				if (ExcelUtils.getCellData(i, colNum).equalsIgnoreCase(sTestCaseName)) {
					break;
				}
			}
			return i;
		} catch (Exception e) {
			throw (e);
		}
	}

	public static int getRowUsed() throws Exception {
		try {

			int RowCount = ExcelWSheet.getLastRowNum();
			System.out.println("The RowCount is " + RowCount);
			return RowCount;

		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw (e);
		}
	}
}
```
---

## STEP 4: Create a TestNg test case for accepting data from Excel using Data Provider.
In this step I used `WebDriverWait` because the method `implicitlyWait(long, TimeUnit) from the type WebDriver.Timeouts` is deprecated
```md
new WebDriverWait(driver, Duration.ofSeconds(10));
```

also, add the `[TestData.xlsx](https://github.com/mawaddahH/TestNG-Data-Provder-using-Excel/files/9385946/TestData.xlsx)` path in the code:
```md
Object[][] testObjArray = ExcelUtils.getTableArray("C:/Users/lo0ol/Downloads/TestData.xlsx", "Sheet1", 1);
```

The final code for `DataProviderWithExcel_001` class
```md
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
		
		Object[][] testObjArray = ExcelUtils.getTableArray("C:/Users/lo0ol/Downloads/TestData.xlsx", "Sheet1", 1);
		return (testObjArray);
}

@AfterMethod
public void afterMethod() {
		driver.close();
}

}
```

> Note: _This LogIn test will execute two times as there are two users credentials in data provider Array_.

### Output Screenshots

<p align="center">
<img src="https://user-images.githubusercontent.com/48597284/185724836-bf8675b7-76d4-49d5-b32a-eeea931309b5.png" width=80% height=80%>

https://user-images.githubusercontent.com/48597284/185724847-89d9f28e-4d4e-43a6-b106-668f2b676f32.mp4

</p>

---

## STEP 5: Run the test against the Test Case name in the Test Data file.
same as STEP 4 , I add `WebDriverWait` 

so, The final code for `DataProviderWithExcel_002` class
```md
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
```

### Output Screenshots

<p align="center">
<img src="https://user-images.githubusercontent.com/48597284/185724901-a4b9e0e3-f3c0-4803-a851-74ccba69006d.png" width=80% height=80%>

https://user-images.githubusercontent.com/48597284/185724907-bee3fdc3-f044-498b-9576-c8d2d669b18d.mp4


</p>
