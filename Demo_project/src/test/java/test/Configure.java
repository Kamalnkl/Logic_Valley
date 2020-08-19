package test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import io.github.bonigarcia.wdm.WebDriverManager;
import junit.framework.AssertionFailedError;

public class Configure {

	public static WebDriver driver;

	public static ExtentHtmlReporter htmlreporter;
	public static ExtentReports extentReport;
	public static ExtentTest testCase;



	@BeforeTest
	public void initilization() throws InterruptedException  {
	
		report();
		
		testCase=extentReport.createTest("Browser", "Browser initilization");		
		


		System.setProperty("webdriver.chrome.driver","C:\\Users\\kamal\\Downloads\\chromedriver_83.exe");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		testCase.log(Status.INFO,"chrome is initialized");
		driver.get("https://www.freshworks.com/");
		testCase.log(Status.INFO,"Url is entered, page opened successfully");
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(10,TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);

		Thread.sleep(7000);
	
	}	
	//commits

	public void report() {
		htmlreporter = new ExtentHtmlReporter("extentReport.html");
		extentReport =new ExtentReports();
		extentReport.attachReporter(htmlreporter);
		htmlreporter.config().setReportName("Automation Testing");
	}

	@Test(priority=1)
	public void titletest() {
		testCase=extentReport.createTest("Title Test", "Check whether title is matched");

		String expectedtitle="A fresh approach to customer engagement";
		String title=driver.getTitle();
		System.out.println("Passed" +title);
		testCase.log(Status.INFO,"The Actual title is:"  +title);

		try {

			Assert.assertEquals(expectedtitle, title);
			testCase.log(Status.PASS,"Title is matched");

		}catch(AssertionError e){
			System.out.println("Failed"+e.getMessage());
			testCase.log(Status.FAIL,"Title is NOT matched"+e.getMessage());
		}
		
	}


	@Test(priority=2)
	public void logotest() {
		testCase=extentReport.createTest("Logo Test", "Check whether Logo is available");

		boolean logo=driver.findElement(By.xpath("//a[@class='logo logo-fworks']")).isDisplayed();	
	
		try {
			Assert.assertEquals(logo, true);
			System.out.println("Passed");
			testCase.log(Status.PASS,"Logo is Displayed");

		}catch(AssertionError e){
			e.printStackTrace();
			System.out.println("Failed"+e.getMessage());
			testCase.log(Status.FAIL, "The Expected and Actual Result are NotEqual:"+"  "+ e.getMessage());
		}
	

	}

	@AfterTest
	public void close() {
		extentReport.flush();

		driver.quit();
	}


}
