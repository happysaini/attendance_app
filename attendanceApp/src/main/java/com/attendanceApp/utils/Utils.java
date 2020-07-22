package com.attendanceApp.utils;


import org.apache.commons.lang3.RandomStringUtils;
import java.util.Random;

/**
 * Created by gvashisht.
 */
public class Utils {

    /**
	 * Generate random number.
	 *
	 * @return the int
	 */
	public static int generateRandomNumber() {
		Random randomGenerator = new Random();
		int randomNumber = randomGenerator.nextInt(999999);
		return randomNumber;
	}
	
	public static String generateUserName() {
		Random randomGenerator = new Random();
		int randomNumber = 11 + randomGenerator.nextInt(87);
		String randomAlphabets = RandomStringUtils.randomAlphabetic(6);
		randomAlphabets = randomAlphabets.toLowerCase();
		String userName = randomAlphabets + randomNumber;
		return userName;
	}
//
//    public static void fluentWaitForElementToBeClickable(WebElement element){
//        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
//                .withTimeout(30, TimeUnit.SECONDS)
//                .pollingEvery(3, TimeUnit.SECONDS)
//                .ignoring(StaleElementReferenceException.class);
//     wait.until(ExpectedConditions.elementToBeClickable(element));
//    }
//
//    public static void fluentWaitForElementToBeVisible(WebElement element){
//        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
//                .withTimeout(30, TimeUnit.SECONDS)
//                .pollingEvery(3, TimeUnit.SECONDS)
//                .ignoring(StaleElementReferenceException.class);
//        wait.until(ExpectedConditions.visibilityOf(element));
//    }

    /**
	 * Generate any random number having given digit count
	 *
	 * @return random number having given digit count
	 */
	public static int generateAnyRandomNumber(int digitCount) {
		Random randomGenerator = new Random();
		int randomNumber = 0;
		for (int i = 0 ; i < digitCount ; i++){
			randomNumber = randomNumber * 10;
			randomNumber = randomNumber  + randomGenerator.nextInt(10);
		}
		System.out.println(randomNumber);
		return randomNumber;
	}
}
