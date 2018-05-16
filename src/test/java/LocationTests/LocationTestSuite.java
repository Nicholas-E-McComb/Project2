package LocationTests;

import org.testng.annotations.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.worldgate.buildingMethods.AddBuildingMethods;
import com.worldgate.locationMethods.AddLocationMethods;
import com.worldgate.locationMethods.McCombLogin;

public class LocationTestSuite {
	static WebDriver wd;
	static int locNum, bldgNum, rmNum;
	@BeforeSuite
	public void setup() {
		AddLocationMethods a = AddLocationMethods.getAddLocInstance();
		AddBuildingMethods b = AddBuildingMethods.getAddBldgInstance();
		File chrome = new File("src/main/resources/chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", chrome.getAbsolutePath());
		wd = new ChromeDriver();
		wd.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		McCombLogin.login(wd);
	}
	
	@AfterSuite
	public void tearDown() {
		//wd.close();
	}
	
	@Test (priority = 1)
	public void navigationTest() {
		  assertTrue(com.worldgate.locationMethods.PageMethods.navigateTo(wd).equals("https://dev.assignforce.revaturelabs.com/locations"));
	}
	
	@Test (priority = 2, groups = "location")
	public void validLocationTest() {
		locNum = AddLocationMethods.addValidLocation(wd);
//		System.out.println("Number: " + locNum);
		System.out.println(wd.getCurrentUrl());
		assertTrue(wd.getPageSource().contains("Unused Test Location " + locNum));
	}
	
	@Test (priority = 2, groups = "location")
	public void invalidLocationTest() {
		assertFalse(AddLocationMethods.addInvalidLocation(wd));
	}
	
	@Test (priority = 2, groups = "location")
	public void reusedNameTest() {
		assertTrue(AddLocationMethods.addPrevUsedName(wd));
	}
	
	@Test (priority = 3, groups = "building")
	public void validBuildingTest() {
		assertTrue(AddBuildingMethods.addValidBuilding(wd));
	}
	
	@Test (priority = 4, groups = "building")
	public void invalidBuildingTest() {
		assertFalse(AddBuildingMethods.addValidBuilding(wd));
	}
	
	@Test(priority = 5, groups = "building")
	public void reusedBuildingTest() {
		assertTrue(AddBuildingMethods.readdBuilding(wd));
	}
}

