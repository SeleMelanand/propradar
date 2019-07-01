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
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class PhonenumberReterivaladdresssearch extends Loginpropertyradar {
	private XSSFWorkbook workbook1;

	@Test
	public void getphonumber() throws InterruptedException, IOException {
		exceldata();
		int sizeofdata = al.size();
		System.out.println(sizeofdata);
		login();
		launchpropertyradar();
		Thread.sleep(3000);

		// Select the search icon on right top corner
			driver.findElementByXPath("(//span[@data-ref='btnIconEl'])[1]").click();
	        driver.findElementByXPath("//span[text()='Lookup']").click();  
		Thread.sleep(2000);

		for (int j = 0; j <= sizeofdata - 1; j++) {

			driver.findElementByXPath("//input[@placeholder='Address']").click();
			Thread.sleep(2000);

			driver.findElementByXPath("//input[@placeholder='Address']").sendKeys(al.get(j));

			WebDriverWait wait1 = new WebDriverWait(driver, 30);
			wait1.until(
					ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//ul[@class='x-list-plain']")));

			for (int i = 0; i < 10; i++) {
				try {
					Assert.assertTrue(driver.findElementByXPath("(//ul[@class='x-list-plain']/div)[1]").isEnabled());
					break;
				} catch (Exception e) {
					System.out.println(
							"Exception occured - to be verified while selecting the value from dropdown - " + i);
				}
			}

			Thread.sleep(2000);
			String verifyaddress = driver.findElementByXPath("(//ul[@class='x-list-plain']/div)[1]").getText();
			String addressfromexcel = al.get(j);
			System.out.println("verify address text value is : " + verifyaddress);
			System.out.println("Address from the excel sheet is : "+ addressfromexcel);
			
			if (verifyaddress.equalsIgnoreCase(addressfromexcel)) {
				
				System.out.println("Both address are same");				
				driver.findElementByXPath("//div[text()='" + verifyaddress + "']").click();

				Thread.sleep(2000);
				driver.findElementByXPath("(//span[text()='Search'])[1]").click();

				WebDriverWait wait2 = new WebDriverWait(driver, 40);

				try {
					wait2.until(ExpectedConditions
							.elementToBeClickable(driver.findElementByXPath("(//span[text()='Contacts'])[1]")));
					driver.findElementByXPath("(//span[text()='Contacts'])[1]").click();
				} catch (Exception e1) {
					System.out.println("Exception ocured during navigating to contacts page");
				}
				
				
				
				
			/*	try {
					WebDriverWait wait3 = new WebDriverWait(driver, 20);

					wait3.until(ExpectedConditions.elementToBeSelected(driver.findElementByXPath(
							"(//div[contains(@id,'phoneTypeWidget')]/div/div/div/div/label/a)[1]")));
					System.out.println("Name of the person");
				} catch (Exception e1) {
					System.out.println("Exception occured during the ph number occurance");
				}*/
				
				
				// confirming whether page is loaded fully or not
                pageloadstate();
				
				List<WebElement> phonenumberdetails = driver
						.findElementsByXPath("//div[contains(@id,'phoneTypeWidget')]/div/div/div/div/label/a");

				int phonenumbercount = phonenumberdetails.size();

				for (WebElement element : phonenumberdetails) {
					System.out.println(element.getText());
				}

				ArrayList<String> alwrite = new ArrayList<String>();

				FileInputStream fis1 = new FileInputStream(".//testdata/Outputresult.xlsx");
				workbook1 = new XSSFWorkbook(fis1);
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

				driver.findElementByXPath("//span[contains(@class,'icon-pr-left')]").click();
				System.out.println("Navigating to search window");

			} else {
				System.out.println("Address doesnot exists : " + al.get(j));
				
				driver.findElementByXPath("//input[@placeholder=\"Address\"]").clear();
				Thread.sleep(3000);
				
				FileInputStream fis1 = new FileInputStream(".//testdata/Outputresult.xlsx");
				XSSFWorkbook workbook1 = new XSSFWorkbook(fis1);
				XSSFSheet worksheet1 = workbook1.getSheet("Sheet1");
				int rowcount = worksheet1.getLastRowNum() + 1;
				XSSFRow row = worksheet1.createRow(rowcount);
				String cellval = al.get(j) + "- Address not exists";
				row.createCell(10).setCellValue(cellval);
				FileOutputStream fos1 = new FileOutputStream(".//testdata/Outputresult.xlsx");
				workbook1.write(fos1);

			}

		}
		workbook1.close();

	}

}
