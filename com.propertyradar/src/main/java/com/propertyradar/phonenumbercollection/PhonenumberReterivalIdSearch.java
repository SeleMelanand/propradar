package com.propertyradar.phonenumbercollection;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.xml.LaunchSuite;

import com.propertyradar.phonenumbercollection.loginpropertyradar;

public class PhonenumberReterivalIdSearch extends loginpropertyradar {

	@Test
	public void getphonumber() throws InterruptedException, IOException {
		// reading data from Excel
		exceldata();

		int sizeofdata = al.size();

		// Browser launch and login to Application
		login ();
		launchpropertyradar();
		Thread.sleep(5000);

		// Select the search icon on right top corner
		driver.findElementByXPath("(//span[@data-ref='btnIconEl'])[1]").click();
        driver.findElementByXPath("//span[text()='Lookup']").click();        
		Thread.sleep(2000);

		// Depending up on the total inputs given , following loop will be executed.
		for (int j = 0; j <= sizeofdata - 1; j++) {

			// Enter data n the RadarID search edit box
			driver.findElementByXPath("//input[@placeholder= 'RadarID']").sendKeys(al.get(j));
			Thread.sleep(2000);

			driver.findElementByXPath("(//span[text()='Search'])[3]").click();

			WebDriverWait wait2 = new WebDriverWait(driver, 60);
			// Selecting the Contacts tab to navigate from Overview tab to Contacts tab.
			try {
				wait2.until(ExpectedConditions
						.elementToBeClickable(driver.findElementByXPath("(//span[text()='Contacts'])[1]")));
				driver.findElementByXPath("(//span[text()='Contacts'])[1]").click();
			} catch (Exception e1) {
				System.out.println("Exception ocured during navigating to contacts page");
			}

			/*
			 * try { WebDriverWait wait3 = new WebDriverWait(driver, 90);
			 * wait3.until(ExpectedConditions.elementToBeSelected(driver
			 * .findElementByXPath(
			 * "(//div[contains(@id,'phoneTypeWidget')]/div/div/div/div/label/a)[1]")));
			 * System.out.println("Name of the person - " +
			 * driver.findElementByXPath("(//span[contains(@class,'panel-headers')])[1]").
			 * getText()); } catch (Exception e1) {
			 * System.out.println("Exception occured during the ph number occurance for " +
			 * driver.findElementByXPath("(//span[contains(@class,'panel-headers')])[1]").
			 * getText()); }
			 */

			// confirming whether page is loaded fully or not
			pageloadstate();

			// retrieving the list of available Phone numbers and saving it in the list
			List<WebElement> phonenumberdetails = driver
					.findElementsByXPath("//div[contains(@id,'phoneTypeWidget')]/div/div/div/div/label/a");

			int phonenumbercount = phonenumberdetails.size();

			for (WebElement element : phonenumberdetails) {
				System.out.println(element.getText());
			}

			ArrayList<String> alwrite = new ArrayList<String>();

			FileInputStream fis1 = new FileInputStream(".//testdata/Outputresult.xlsx");
			XSSFWorkbook workbook1 = new XSSFWorkbook(fis1);
			XSSFSheet worksheet1 = workbook1.getSheet("Sheet1");
			int rowcount = worksheet1.getLastRowNum() + 1;
			System.out.println("rowcount:" + rowcount);
			System.out.println("phonnumber count:" + phonenumbercount);
			XSSFRow row = worksheet1.createRow(rowcount);
			row.createCell(10).setCellValue(al.get(j));
			String phnumber = "";

			if (phonenumbercount > 0) {
				for (WebElement phonenumber : phonenumberdetails) {
					phnumber = phonenumber.getText();
					alwrite.add(phnumber);
				}
			} else {
				System.out.println("Phone number doesnot exists for : " + al.get(j));
			}

			LinkedHashSet<String> lhs = new LinkedHashSet<String>();
			lhs.addAll(alwrite);

			ArrayList<String> als = new ArrayList<String>();
			als.addAll(lhs);
			System.out.println(als);

			for (int k = 0; k < als.size(); k++) {
				try {
					row.createCell(k).setCellValue(als.get(k));
				} catch (Exception e) {
					System.out.println("Exception occured while saving the data in excel" + e);
				}
			}

			System.out.println("Going to save data in excel");

			FileOutputStream fos = new FileOutputStream(".//testdata/Outputresult.xlsx");
			workbook1.write(fos);
			workbook1.close();

			// driver.findElementByXPath("//span[contains(@class,'icon-pr-left')]").click();

			driver.navigate().back();
			System.out.println("Navigating to search window");

		}

	}

}
