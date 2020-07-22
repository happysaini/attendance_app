package com.attendanceApp.utils;

import java.io.File;

import org.apache.commons.exec.OS;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ExtentManager {
	
	private static ExtentReports extent;
    private static String reportFileName = "Test-Automaton-Report"+".html";
    private static String fileSeperator = System.getProperty("file.separator");
    private static String reportFilepath = System.getProperty("user.dir") + fileSeperator + "target" + fileSeperator + "TestReport";
    private static String reportFileLocation =  reportFilepath + fileSeperator + reportFileName;
  
 
    public static ExtentReports getInstance() {
        if (extent == null)
            createInstance();
        return extent;
    }
 
    //Create an extent report instance
    public static ExtentReports createInstance() {
    	
        String fileName = getReportPath(reportFilepath);
       
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        htmlReporter.loadXMLConfig(new File(System.getProperty("user.dir")+"\\src\\main\\resources\\config\\extent-config.xml"));
 
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        //Set environment details
        if(OS.isFamilyWindows()){
        	extent.setSystemInfo("OS", "Windows");
        }else if(OS.isFamilyMac()){
        	extent.setSystemInfo("OS", "Mac");
        } else{
        	extent.setSystemInfo("OS", "Linux");
        }
		extent.setSystemInfo("AUT", "QA");
        return extent;
    }
     
    //Create the report path
    private static String getReportPath (String path) {
    	File testDirectory = new File(path);
        if (!testDirectory.exists()) {
        	if (testDirectory.mkdir()) {
                System.out.println("Directory: " + path + " is created!" );
                return reportFileLocation;
            } else {
                System.out.println("Failed to create directory: " + path);
                return System.getProperty("user.dir");
            }
        } else {
            System.out.println("Directory already exists: " + path);
        }
		return reportFileLocation;
    }
}