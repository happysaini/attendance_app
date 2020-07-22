package com.attendanceApp.common;

import org.testng.annotations.BeforeTest;

import com.attendanceApp.AppiumServerJava;
import com.attendanceApp.utils.ExtentManager;
import com.attendanceApp.utils.Log;
import com.aventstack.extentreports.ExtentReports;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

import org.testng.annotations.Parameters;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.AfterMethod;

public class SetUp {
	
	protected static ThreadLocal<AppiumDriver<MobileElement>> driver = new ThreadLocal<>();
	
	/**
	 * This method is used to Launch the given emulator, Start Appium server at given port and initialize Android Driver
	 * 
	 * @param portNumber : on which port appium server is start
	 * @param udid : udid of the device
	 * @param deviceName : avd name of the device
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Parameters({"portNumber", "udid", "deviceName"})
	@BeforeTest(groups = {"Regression","Smoke"})
	public void beforeSuite(String portNumber,String udid, String deviceName)
			throws IOException, InterruptedException {
		String log4jConfPath = "src/main/resources/config/log4j.properties";
		PropertyConfigurator.configure(log4jConfPath);
			try {
				if(!deviceName.equals("Android")){
					List<String> commandToLaunchEmulator = Arrays.asList("/c","c:","&&","cd",System.getenv("ANDROID_HOME") + "\\emulator","&&","emulator","-avd",deviceName,"-port",udid.split("-")[1], "-no-boot-anim");
					runGivenCommand(commandToLaunchEmulator);
					Thread.sleep(60000);
					Log.info("Wait for " + deviceName + " emulator to launch");
					Thread.sleep(60000);
					Log.info("Wait for emulator to restart the emulator");
				}
				
				AppiumServerJava appiumServer = new AppiumServerJava();
				if (!appiumServer.checkIfServerIsRunnning(Integer.parseInt(portNumber))) {
					appiumServer.startServer(Integer.parseInt(portNumber));
					Log.info("Appium Server is up on port " + portNumber);
					
				} else {
					Log.info("Appium Server already running on Port - " + portNumber);
				}
				
				
			} catch (Exception e) {
				throw new RuntimeException("Error occurs before suite starts : " + e.getMessage());
			}
			
			DesiredCapabilities capabilities = new DesiredCapabilities();

			capabilities.setCapability("deviceName", deviceName);
			capabilities.setCapability("platformName", "Android");
			capabilities.setCapability("udid", udid);
			capabilities.setCapability("adbExecTimeout", 40000);
			capabilities.setCapability("newCommandTimeout", 30000);
			capabilities.setCapability("allowTestPackages", true);
			capabilities.setCapability("automatioName", "UiAutomation2");
			capabilities.setCapability("app",
					System.getProperty("user.dir") + "/app/app-debug.apk");
			try {
				driver.set(new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:" + portNumber + "/wd/hub"),
						capabilities));
				LogEntries logEntries = driver.get().manage().logs().get("server");
				 for (LogEntry entry : logEntries) {
					 Log.info("Appium Logs of " + portNumber + " and device " + udid + " -------- " +entry);
			        }
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
	}

	/**
	 * This method is used to get the driver
	 * 
	 * @return driver
	 */
	public static WebDriver getDriver() {
		return driver.get();
	}
	
	/**
	 * This method is used to quit the driver and close the emulator
	 * 
	 * @param udid : udid of the device
	 */
	@Parameters({"udid"})
	@AfterTest(groups = {"Regression","Smoke"} , alwaysRun = true) 
	public void afterTest(String udid) {
		try {
			Log.info("Closing the app");
			closeApp();
			getDriver().quit();
			if(udid.contains("emulator")){
				Thread.sleep(1000);
				List<String> commandsToCloseEmulator = Arrays.asList("/c","adb","-s",udid,"shell","reboot","-p");
				runGivenCommand(commandsToCloseEmulator);
				Log.info("Closing the emulator open at port - " + udid.split("-")[0]);
			}
		} catch (Exception e) {
		}
	}
	
	/**
	 * This method is used to close the app
	 */
	public void closeApp() {
		try {
			((AndroidDriver<MobileElement>) getDriver()).closeApp();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * This method is used to Close App/Launch App and take screenshot if test failed
	 * 
	 * @param result : Result of the method
	 */
	@AfterMethod(groups = {"Regression","Smoke"})
	public void afterMethod(ITestResult result) {
		if(!result.isSuccess()){
			File screenshotDir = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "target/Screenshots/");
			int randomNumber = new Random().nextInt(999999);
	    	File screenshot = new File(screenshotDir, "Screenshot_" + randomNumber + ".png");
	    	File file = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
	        try {
	        	FileUtils.copyFile(file, screenshot);
	        	Log.info("Screenshot taken --------------------> " + "Screenshot_" + randomNumber + ".png");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        Log.info("Closing the app");
	        closeApp();
	        Log.info("Launching the app");
			((AndroidDriver<MobileElement>) SetUp.getDriver()).launchApp();
		}
	}
	
	/**
	 * This method is used to stop appium server
	 */
	@AfterSuite(groups = {"Regression","Smoke"} ,alwaysRun = true) 
	public void afterSuite() {
		try {
			List<String> commandsToStopAppiumServers = Arrays.asList("/c","taskkill","/F","/IM","node.exe");
			runGivenCommand(commandsToStopAppiumServers);
			Log.info("Kill the appium server");
		} catch (Exception e) {
		}
	}
	
	/**
	 * This method is used to run the given commands through commandLine
	 * 
	 * @param commands : given commands to run 
	 * @throws IOException
	 */
	public static void runGivenCommand(List<String> commands) throws IOException{
		CommandLine command = new CommandLine("cmd");
		for (String commandText : commands){
			command.addArgument(commandText);
		}
		DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
		DefaultExecutor executor = new DefaultExecutor();
		executor.setExitValue(1);
		executor.execute(command, resultHandler);
	}
	 
}
