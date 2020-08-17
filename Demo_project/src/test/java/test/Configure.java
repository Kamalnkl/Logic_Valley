package test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Configure {

	public static WebDriver driver;
	
	public static ExtentHtmlReporter htmlreporter;
	public static ExtentReports extentReport;
	public static ExtentTest testCase;
	


	@BeforeTest
	public void initilization() throws InterruptedException  {
		htmlreporter = new ExtentHtmlReporter("extentReport.html");
		extentReport =new ExtentReports();
		extentReport.attachReporter(htmlreporter);
		htmlreporter.config().setReportName("Automation Testing");

		testCase=extentReport.createTest("Browser", "Browser initilization");		


		
		System.setProperty("webdriver.chrome.driver","C:\\Users\\kamal\\Downloads\\chromedriver_83.exe");
		driver = new ChromeDriver();
		testCase.log(Status.INFO,"chrome is initialized");
		driver.get("https://www.freshworks.com/");
		testCase.log(Status.INFO,"Url is entered, page opened successfully");
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(10,TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		
		Thread.sleep(7000);
		extentReport.flush();
	}	

//commits

	@Test(priority=1)
	public void titletest() {
		testCase=extentReport.createTest("Title Test", "Check whether title is matched");
		
		
	
		try {
			
			String expectedtitle="A fresh approach to customer engagement";
			String title=driver.getTitle();
			System.out.println(title);
			testCase.log(Status.INFO,"The Actual title is:"  +title);

		Assert.assertEquals(expectedtitle, title);
		testCase.log(Status.PASS,"Title is matched");
		
		
		}catch(Exception e){
			testCase.log(Status.FAIL,"Title is NOT matched"+e.getMessage());
		}
		extentReport.flush();
	}


	@Test(priority=2)
	public void logotest() {
		testCase=extentReport.createTest("Logo Test", "Check whether Logo is available");
		
		boolean logo=driver.findElement(By.xpath("//a[@class='logo logo-fworks']")).isDisplayed();	
		
try {
	Assert.assertTrue(logo, "The logo is displayed");

		testCase.log(Status.PASS,"Logo is Displayed");
		
}catch(Exception e){
	testCase.log(Status.FAIL,"Logo is NOT Displayed");
}
		extentReport.flush();

	}

	@AfterTest
	public void close() {
		driver.quit();
	}


}
