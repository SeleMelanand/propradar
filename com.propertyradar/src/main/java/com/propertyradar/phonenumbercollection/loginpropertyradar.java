package com.propertyradar.phonenumbercollection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class loginpropertyradar {
	RemoteWebDriver driver;
	ArrayList<String> al = new ArrayList<String>();

	//@Test
	public void login() {
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.get("https://www.propertyradar.com/");
		driver.findElementByXPath("(//a[text()='Login'])[1]").click(); 
		driver.findElementByName("userEmail").sendKeys("jkralik@upstartresidential.com");
		driver.findElementByName("userPW").sendKeys("1101Marian");
		WebDriverWait waitlogin = new WebDriverWait(driver, 30);
		waitlogin.until(
				ExpectedConditions.elementToBeClickable(driver.findElementByXPath("(//span[@data-ref='btnEl'])[4]")));
		driver.findElementByXPath("(//span[@data-ref='btnEl'])[4]").click();
	}

	public void pageloadstate() throws InterruptedException {
		// get the state whether page is loading or not
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String states = null;
		do {
			states = js.executeScript("return document.readyState").toString();
			Thread.sleep(3000);
			System.out.println("page loaded!!!: " + states);
		} while (states == "complete");
		System.out.println("page loaded sucessfully");

	}

	public void exceldata() throws IOException {
		FileInputStream fis = new FileInputStream(".//testdata/PropertyAddressReference.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet worksheet = workbook.getSheet("Sheet1");
		int rowcount = worksheet.getLastRowNum() + 1;

		for (int i = 0; i <= rowcount - 1; i++) {
			String cellvalue = worksheet.getRow(i).getCell(0).getStringCellValue();
			System.out.println(cellvalue);
			al.add(cellvalue);
		}

	}

}
