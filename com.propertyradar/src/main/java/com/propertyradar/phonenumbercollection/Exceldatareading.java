package com.propertyradar.phonenumbercollection;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

public class Exceldatareading {

	public static String[] datareading() throws IOException{

		
		FileInputStream fis = new FileInputStream(".//testdata/Propertyradartestdataupdated.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet worksheet = workbook.getSheet("Sheet1");
		int rowcount = worksheet.getLastRowNum()+1;
		System.out.println(rowcount);
		String[] data = new String[rowcount];
		
		for(int i=0;i<rowcount;i++){
			//String cell= worksheet.getRow(i).getCell(0);
			String cellvalue= worksheet.getRow(i).getCell(0).getStringCellValue();				
			System.out.println(cellvalue);
			data[i] = cellvalue;
		}
		
		fis.close();

		return data;
		
		
	}

}
