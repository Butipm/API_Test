package API;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import com.gargoylesoftware.htmlunit.javascript.host.Screen;
import com.opencsv.CSVReader;
import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class API_Test {

//public static void main(String[] args) throws IOException {
	static String Breed,sub_Breed;
	public static ExtentReports extent;
	public static ExtentTest test;
	
	@BeforeSuite
	public void beforesuite() 
	{
		extent = new ExtentReports("C:\\Users\\PC\\eclipse-workspace\\Assessment\\API_Test\\Reports\\API.html",true);
		extent.loadConfig( new File("C:\\Users\\PC\\eclipse-workspace\\Assessment\\API_Test\\Lib\\Config\\extent-config.xml"));
	} 
	
	@BeforeMethod
	public void beforemethod()
	{
	test = extent.startTest("Testing API");
	test.assignAuthor("Itu Assessment");
	test.assignCategory("API Tests");
	test.log(LogStatus.INFO, "Test Began Running");
	}
	
	
	@Test
	public void allBreeds() throws IOException {

		// Specify the base URL to the RESTful web service
		RestAssured.baseURI="https://dog.ceo/api/breeds/list/all";
		
 		RequestSpecification httpRequest = RestAssured.given();
 		
 		Response response=httpRequest.get();
		String responseBody = response.getBody().asString();
		int statusCode = response.getStatusCode();
		 
		// Assert that correct status code is returned.
		Assert.assertEquals(statusCode, 200, "Correct status code returned");
		
		
		System.out.println("Response Body for all Breeds is =>  " + responseBody);
		if(responseBody != null) {
			test.log(LogStatus.PASS, "Actual Results :: " +responseBody );
		}else {
			test.log(LogStatus.FAIL, "List of Breeds Not Found ::" +responseBody);
		}
		
		Assert.assertEquals(responseBody.toLowerCase().contains("retriever"), true, "Verify - retriever is within list");
	
		if(responseBody.toLowerCase().contains("retriever") == true) {
		test.log(LogStatus.PASS, "Actual Results :: Retriever found" );
		}else {
			test.log(LogStatus.FAIL, "Retriever Not Found ::" +responseBody);
		}
	}
	
	
	@Test
	public void subBreeds() throws IOException {
		// Specify the base URL to the RESTful web service
		Breed = "retriever";
		RestAssured.baseURI="https://dog.ceo/api/breed/"+Breed+"/list";
		
 		RequestSpecification httpRequest = RestAssured.given();
 		
 		Response response=httpRequest.get();
		String responseBody = response.getBody().asString();
		int statusCode = response.getStatusCode();
		 
		// Assert that correct status code is returned.
		Assert.assertEquals(statusCode, 200, "Correct status code returned");
		
		
		System.out.println("Response Body for All Sub-Breeds is =>  " + responseBody);
		if(responseBody != null) {
		test.log(LogStatus.PASS, "Actual Results :: " +responseBody );
	}else {
		test.log(LogStatus.FAIL, "Sub-Breeds Not Found ::" +responseBody);
	} 
	}
	@Test
	public void randomImageLink() throws IOException {
		// Specify the base URL to the RESTful web service
		Breed = "retriever";
		sub_Breed = "golden";
		RestAssured.baseURI="https://dog.ceo/api/breed/"+Breed+"/"+sub_Breed+"/images/random";
		
 		RequestSpecification httpRequest = RestAssured.given();
 		
 		Response response=httpRequest.get();
		String responseBody = response.getBody().asString();
		int statusCode = response.getStatusCode();
		 
		// Assert that correct status code is returned.
		Assert.assertEquals(statusCode, 200, "Correct status code returned");
		
		System.out.println("Response Body for Random Image/link is =>  " + responseBody);
		if(responseBody != null) {
			test.log(LogStatus.PASS, "Actual Results :: " +responseBody );
		}else {
			test.log(LogStatus.FAIL, "Link to image Not Found ::" +responseBody);
		}
	} 
	@AfterMethod
	public void aftermethod() throws IOException{
		test.log(LogStatus.PASS, "Test execution finished");
		extent.endTest(test);
	}
	@AfterSuite
	public void afterSuite() {
		extent.flush();
		extent.close();
		
	}
	
}
