package com.propertyradarnots;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class ReterivalIdSearchNots extends LoginpropertyradarNots {

     @Test
     public void getphonumber() throws InterruptedException, IOException {
           exceldata();
           int sizeofdata = al.size();

           login();
           launchpropertyradar();
           Thread.sleep(5000);

           driver.findElementByXPath("(//span[@data-ref='btnIconEl'])[3]").click();

           Thread.sleep(2000);

           for (int j = 0; j <= sizeofdata - 1; j++) {
                System.out.println("Searching on property is getting started");
                // searching for a particular RadarID
                driver.findElementByXPath("//input[@placeholder= 'RadarID']").sendKeys(al.get(j));
                Thread.sleep(2000);
                WebDriverWait wait2 = new WebDriverWait(driver, 60);
                driver.findElementByXPath("(//span[text()='Search'])[3]").click();

                // getting values from Proeprty tab summary section
                Thread.sleep(6000);
                driver.findElementByXPath("(//span[text()='Property'])[2]").click();

                wait2.until(
                     ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//span[text()='Add Photo']")));

                String estimatedvalue = null;
                String assessedvalue = null;
                String estimatedtotalloanbalance = null;
                String estimatedequity = null;
                String purchaseamount = null;
                String downpayment = null;
                String purchasedate = null;

                Thread.sleep(2000);
                try {
                     estimatedvalue = driver.findElementByXPath("//div[contains(text(),'Est. Value')]/following::div[1]")
                                .getText();
                     System.out.println("estimatedvalue : " + estimatedvalue);
                } catch (Exception e2) {
                     System.out.println("No  value present in estimated value field - ui");
                     estimatedvalue = "value not present";
                }

                try {
                     assessedvalue = driver
                                .findElementByXPath("(//div[text()='Assessed Value $'])[1]//../div[2]")
                                .getText();
                     //.......(//div[contains(text(),'Assessed Value')])[1]/following::div[1] ----- old Xpath values used
                     
                     System.out.println("assessedvalue : " + assessedvalue);
                } catch (Exception e2) {
                     System.out.println("No  value present in assessed value field - ui");
                     assessedvalue = "value not present";

                }

                try {
                     estimatedtotalloanbalance = driver
                                .findElementByXPath("(//div[text()='Est. Total Loan Bal. $'])[1]//../div[2]").getText();
                     //..... //div[contains(text(),'Total Loan Bal')]/following::div[1]
                   
                     System.out.println("estimatedtotalloanbalance : " + estimatedtotalloanbalance);
                } catch (Exception e2) {
                     System.out.println("No  value present in estimated total loan balance value field - ui");
                     estimatedtotalloanbalance = "value not present";

                }

                try {
                     estimatedequity = driver.findElementByXPath("//div[contains(text(),'Est. Equity')]/following::div[1]")
                                .getText();
                     System.out.println("estimatedequity: " + estimatedequity);
                } catch (Exception e2) {
                     System.out.println("No  value present in estimated equity field - ui");
                     estimatedequity = "value not present";

                }

                // getting values from Proeprty tab purchase section

                try {
                     purchasedate = driver.findElementByXPath("//div[contains(text(),'Purchase Date')]/following::div[1]")
                                .getText();
                     System.out.println("purchasedate: " + purchasedate);
                } catch (Exception e2) {
                     System.out.println("No value present in purchase date field - ui");
                     purchasedate = "value not present";

                }

                try {
                     purchaseamount = driver.findElementByXPath("//div[contains(text(),'Purchase Amt')]/following::div[1]")
                                .getText();
                     System.out.println("purchaseamount : " + purchaseamount);
                } catch (Exception e2) {
                     System.out.println("No value present in purchase amount field - ui");
                     purchaseamount = "value not present";

                }

                try {
                     downpayment = driver.findElementByXPath("//div[contains(text(),'Down Payment')]/following::div[1]")
                                .getText();
                     System.out.println("downpayment: " + downpayment);
                } catch (Exception e2) {
                     System.out.println("No value present in down payment field - ui");
                     downpayment = "value not present";
                }

                //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                // getting values from contacts tab
                
                /*try {
                     wait2.until(ExpectedConditions
                           .elementToBeClickable(driver.findElementByXPath("(//span[text()='Contacts'])[1]")));
                     driver.findElementByXPath("(//span[text()='Contacts'])[1]").click();
                } catch (Exception e1) {
                     System.out.println("Exception ocured during navigating to contacts page");
                }

                try {
                     WebDriverWait wait3 = new WebDriverWait(driver, 90);
                     wait3.until(ExpectedConditions.elementToBeSelected(driver
                           .findElementByXPath("(//div[contains(@id,'phoneTypeWidget')]/div/div/div/div/label/a)[1]")));
                     System.out.println("Name of the person - "
                                + driver.findElementByXPath("(//span[contains(@class,'panel-headers')])[1]").getText());
                } catch (Exception e1) {
                     System.out.println("Exception occured during the ph number occurance for "
                                + driver.findElementByXPath("(//span[contains(@class,'panel-headers')])[1]").getText());
                }

                List<WebElement> phonenumberdetails = driver
                     .findElementsByXPath("//div[contains(@id,'phoneTypeWidget')]/div/div/div/div/label/a");

                int phonenumbercount = phonenumberdetails.size();

                for (WebElement element : phonenumberdetails) {
                     System.out.println(element.getText());
                }
*/
                ArrayList<String> alwrite = new ArrayList<String>();

                FileInputStream fis1 = new FileInputStream(".//testdata/OutputresultNOTS.xlsx");
                XSSFWorkbook workbook1 = new XSSFWorkbook(fis1);
                XSSFSheet worksheet1 = workbook1.getSheet("Sheet1");
                int rowcount = worksheet1.getLastRowNum() + 1;
                System.out.println("rowcount:" + rowcount);
                //System.out.println("phonnumber count:" + phonenumbercount);
                XSSFRow row = worksheet1.createRow(rowcount);
                row.createCell(17).setCellValue(al.get(j));
                String phnumber = "";

                /*if (phonenumbercount > 0) {
                     for (WebElement phonenumber : phonenumberdetails) {
                           phnumber = phonenumber.getText();
                           alwrite.add(phnumber);
                     }
                } else {
                     System.out.println("Phone number doesnot exists for : " + al.get(j));
                }*/

                LinkedList<String> lls = new LinkedList<String>();
                lls.add(estimatedvalue);
                lls.add(assessedvalue);
                lls.add(estimatedtotalloanbalance);
                lls.add(estimatedequity);
                lls.add(purchaseamount);
                lls.add(purchasedate);
                lls.add(downpayment);
                //lls.addAll(alwrite);

                System.out.println(lls);

                for (int k = 0; k < lls.size(); k++) {
                     try {
                           row.createCell(k).setCellValue(lls.get(k));
                     } catch (Exception e) {
                           System.out.println("Exception occured while saving the data in excel" + e);
                     }
                }

                System.out.println("Going to save data in excel");

                FileOutputStream fos = new FileOutputStream(".//testdata/OutputresultNOTS.xlsx");
                workbook1.write(fos);
                workbook1.close();
                
                System.out.println("Data written in excel successfully");

                driver.findElementByXPath("//a[@data-qtip='Return to Previous Page']/span/span/span").click();
                //driver.findElementByXPath("//a[@data-qtip='Return to My Lists']").click();

                wait2.until(
                     ExpectedConditions.elementToBeClickable(driver.findElementByXPath("(//span[text()='Search'])[3]")));
                System.out.println("Navigating to search window");

           }

     }

}

