package com.attendanceApp.utils;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.util.SystemOutLogger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class getData {
	
	 XSSFWorkbook  wb;
		
	  @DataProvider(name="SearchProvider")
	  public Object[][] getDataFromDataprovider(Method m) {
		try {
			System.out.println(System.getProperty("user.dir") + "\\src\\main\\resources\\config\\Testdata.xlsx");
			FileInputStream fis = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\main\\resources\\config\\Testdata.xlsx");
			wb = new XSSFWorkbook(fis);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Sheet sheet = wb.getSheetAt(0);
		int columns = 0;
		try {
			while (!sheet.getRow(0).getCell(columns).equals("")) {
				columns++;
			}
		} catch (Exception e) {
		}
		Map<String, String> obj = new HashMap();
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				if (sheet.getRow(i).getCell(0).toString().equals(m.getName())) {
					for (int t = 0; t < columns; t++) {
						try{
							obj.put(sheet.getRow(0).getCell(t).toString(), sheet.getRow(i).getCell(t).toString());
						}catch(Exception e){
							continue;
						}
						
					}
					break;
				}
		}
		return new Object[][] { new Object[] { obj } };
	}
}
