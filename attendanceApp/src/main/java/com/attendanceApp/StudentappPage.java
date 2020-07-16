package com.attendanceApp;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by manikaur
 */

public class StudentappPage extends AbstractPage{
	
	public StudentappPage(WebDriver webDriver) {
		super(webDriver);
	}
	
	By welcomeText = By.id("com.example.androidattendancesystem:id/textView1");
	By startButton = By.id("com.example.androidattendancesystem:id/buttonstart");
	By loginAsButton = By.id("com.example.androidattendancesystem:id/spinnerloginas");
	By loginAsOptions = By.id("android:id/text1");
	By userName = By.id("com.example.androidattendancesystem:id/editTextusername");
	By password = By.id("com.example.androidattendancesystem:id/editTextpassword");
	By login = By.id("com.example.androidattendancesystem:id/buttonlogin");
	By parentScreen = By.id("android:id/content");
	By buttonsBy = By.className("android.widget.Button");
	By submitButton = By.id("com.example.androidattendancesystem:id/submitButton");
	By labelsBy = By.id("com.example.androidattendancesystem:id/label");
	By registerButton = By.id("com.example.androidattendancesystem:id/RegisterButton");

	/**
	 * This method is used to tap on Start Button
	 */
	public void tapOnStartButton(){
		waitforElement(welcomeText);
		tap(startButton);
	}
	
	/**
	 * This method is used to get the header text
	 * 
	 * @return String
	 */
	public String getPageHeader(){
		return getTextOfElement(welcomeText);
	}
	
	/**
	 * This method is used to select options from Login As Field
	 * 
	 * @param value : value to be selected
	 */
	public void selectOptionsFromLoginAs(String value){
		tap(loginAsButton);
		findAllElements(loginAsOptions).stream().filter(option -> getTextOfElement(option).equals(value)).findFirst().orElse(null).click();
	}
	
	/**
	 * This method is used to enter text in username field
	 *  
	 * @param value : value to enter
	 * 
	 * @throws InterruptedException
	 */
	public void enterUsername(String value) throws InterruptedException{
		Thread.sleep(1000);
		sendkeys(userName, value);
		Thread.sleep(1000);
	}
	
	/**
	 * This method is used to enter text in password field
	 *  
	 * @param value : value to enter
	 * 
	 * @throws InterruptedException
	 */
	public void enterPassword(String value) throws InterruptedException{
		sendkeys(password, value);
	}
	
	/**
	 * This method is used to tap on login button
	 */
	public void tapOnLoginButton(){
		tap(login);
	}
	
	/**
	 * This method is used to get list of all the button displayed
	 * 
	 * @return : list
	 * 
	 * @throws InterruptedException
	 */
	public List<String> getListOfAllButtonsDisplayed() throws InterruptedException{
		Thread.sleep(2000);
		List<String> actualList = new ArrayList<String>();
		findAllElementsFromWithin(parentScreen, buttonsBy).stream().forEach(button -> actualList.add(getTextOfElement(button)));
		return actualList;
	}
	
	/**
	 * This method is used to tap on given button
	 * 
	 * @param button : name of the button
	 * 
	 * @throws InterruptedException
	 */
	public void tapOnGivenButton(String button) throws InterruptedException{
		Thread.sleep(2000);
		tap(By.id("com.example.androidattendancesystem:id/button" + button));
	}
	
	/**
	 * This method is used to tap on Submit button
	 */
	public void tapOnSubmitButton(){
		tap(submitButton);
	}
	
	/**
	 * This method is used to get list of all the values displayed
	 * 
	 * @return : list 
	 * 
	 * @throws InterruptedException
	 */
	public List<String> getListOfAllValuesDisplayed() throws InterruptedException{
		Thread.sleep(1000);
		List<String> actualList = new ArrayList<String>();
		findAllElements(labelsBy).stream().forEach(button -> actualList.add(getTextOfElement(button)));
		return actualList;
	}
	
	/**
	 * This method is used to enter value in the given field
	 * 
	 * @param fieldName : Name of the field
	 * @param value : value to enter
	 * @throws InterruptedException
	 */
	public void enterValueForGivenField(String fieldName,String value) throws InterruptedException{
		Thread.sleep(1000);
		sendkeys(By.id("com.example.androidattendancesystem:id/editText" + fieldName), value);
		tapDeviceBackButton();
	}
	
	/**
	 * This method is used to tap on Register button
	 */
	public void tapOnRegisterButton(){
		tap(registerButton);
	}
}