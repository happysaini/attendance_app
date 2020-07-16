package com.attendanceApp;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.attendanceApp.utils.Log;

import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.offset.PointOption;


/**
 * Created by manikaur.
 */
public abstract class AbstractPage {

	private WebDriver driver;
	
	public AbstractPage(WebDriver driver){
		this.driver = driver; 
	}
	
	/**
	 * Gets the web driver.
	 *
	 * @return the webDriver
	 */
	public WebDriver getDriver() {
		return driver;
	}
	
	/**
	 * Gets the wait.
	 *
	 * @return the wait
	 */
	public Wait<WebDriver> getWait() {
		return new WebDriverWait(getDriver(), 10);
	}

	/**
	 * Gets the wait.
	 *
	 * @param delay
	 *            the delay
	 * @return the wait
	 */
	public Wait<WebDriver> getWait(int delay) {
		return new WebDriverWait(getDriver(), delay);
	}    
	
	/**
	 * This method is used to wait for the element till the element is present
	 * @param by
	 */
	public void waitforElement(By by){
		Log.info("Wait for element " + by);
		getWait(20).until(ExpectedConditions.presenceOfElementLocated(by));
	}
	
	/**
	 * This method is used to wait for invisibility of the element 
	 * @param by
	 */
	public void waitforInvisibilityOfElement(By by){
		Log.info("Wait for invisibility of element " + by);
		getWait(20).until(ExpectedConditions.invisibilityOfElementLocated(by));
	}
	
	/**
	 * This method is used to find the element
	 * 
	 * @param by
	 * @return : WebElement
	 */
	public WebElement findElement(By by) {
		try {
			waitforElement(by);
			Log.info("Find element " + by);
			return getDriver().findElement(by);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * This method is used to get all the elements present
	 * 
	 * @param by
	 * @return list of elements
	 */
	public List<WebElement> findAllElements(By by) {
		try {
			waitforElement(by);
			Log.info("Find all elements " + by);
			return getDriver().findElements(by);
		} catch (Exception  e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * This method is used to tap on element
	 * 
	 * @param by
	 */
	public void tap(By by){
		try {
			Log.info("Wait for element " + by + " to be clickable");
			getWait(20).until(ExpectedConditions.elementToBeClickable(by));
			findElement(by).click();
			Log.info("Tap on the element " + by);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * This method is used to tap on element is without wait for 
	 * element to be clickable
	 * 
	 * @param by
	 */
	public void tapWithoutWait(By by){
		try {
			findElement(by).click();
			Log.info("Tap on element " + by);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * This method is used to sendkeys to the element 
	 * 
	 * @param by
	 * @param text
	 */
	public void sendkeys(By by,String text){
		try {
			waitforElement(by);
			findElement(by).click();
			findElement(by).sendKeys(text);
			Log.info("Sendkeys to element " + by);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * This method is used to get text from the element 
	 * 
	 * @param by
	 * 
	 * @return Text of the element
	 */
	public String getTextOfElement(By by){
		try {
			waitforElement(by);
			Log.info("Get text of element " + by);
			return findElement(by).getText();
		} catch (Exception  e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * This method is used to get value of given attribute of element 
	 * 
	 * @param by
	 * @param attributeName : Name of the attribute 
	 * 
	 * @return value of attribute
	 */
	public String getAttributeValue(By by, String attributeName){
		try {
			waitforElement(by);
			Log.info("Get attribute value of element " + by);
			return findElement(by).getAttribute(attributeName);
		} catch (Exception  e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * This method is used to verify element is displayed or not
	 *  
	 * @param by
	 * @return true/false
	 */
	public boolean isDisplayed(By by){
		try {
			return getWait().until(ExpectedConditions.visibilityOf(getDriver().findElement(by))).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * This method is used to wait for element fluently
	 * 
	 * @param by
	 */
	public void waitForElementFluently(By by){
		Wait<WebDriver> wait = new FluentWait<WebDriver>(getDriver());
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}
	
	/**
	 * This method is used to find the element from the parent element
	 * 
	 * @param parentBy
	 * @param by
	 * @return WebElement
	 */
	public WebElement findElementFromWith(By parentBy, By by){
		try {
			waitforElement(parentBy);
			WebElement parent = getDriver().findElement(parentBy);
			Log.info("Find element " + by + " from parent element " + parentBy);
			return parent.findElement(by);
		} catch (Exception  e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * This method is used to get if element displayed or not
	 * @param element
	 * @return true/false
	 */
	public boolean isDisplayed(WebElement element){
		try {
			return getWait().until(ExpectedConditions.visibilityOf(element)).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * This method is used to get the webelement
	 * @param by
	 * @return
	 */
	public WebElement getWebElement(By by){
		try {
			getWait().until(ExpectedConditions.visibilityOfElementLocated(by));
			return getDriver().findElement(by);
		} catch (Exception  e) {
			throw new RuntimeException(e);
		}
	} 
	
	/**
	 * This method is used to scroll element to down
	 */
	public void scrollElementToDown() {
	    //if pressX was zero it didn't work for me
	    int pressX = getDriver().manage().window().getSize().width / 2;
	    // 4/5 of the screen as the bottom finger-press point
	    int topY = getDriver().manage().window().getSize().height / 7;
	    // just non zero point, as it didn't scroll to zero normally
	    int bottomY = getDriver().manage().window().getSize().height -2;
	    //scroll with TouchAction by itself
	    Log.info("Scroll the screen to downward direction");
	    scroll(pressX, topY, pressX, bottomY);
	}
	
	/**
	 * This method is used to scroll mobile screen
	 * 
	 * @param fromX
	 * @param fromY
	 * @param toX
	 * @param toY
	 */
	public void scroll(int fromX, int fromY, int toX, int toY) {
	    TouchAction touchAction = new TouchAction((PerformsTouchActions) getDriver());
	    touchAction.longPress(PointOption.point(fromX, fromY)).moveTo(PointOption.point(toX, toY)).release().perform();
	    }
	
	/**
	 * This method is used to get text from the element 
	 * 
	 * @param by
	 * 
	 * @return Text of the element
	 */
	public String getTextOfElement(WebElement element){
		try {
			return element.getText();
		} catch (Exception  e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * This method is used to press keys from Android keyborad
	 * 
	 * @param key
	 */
	public void keyEvent(AndroidKey key) {
    	((AndroidDriver) getDriver()).pressKey(new KeyEvent(key));
    }
	
	/**
	 * This method is used tap on given point 
	 * 
	 * @param x
	 * @param y
	 */
	public void tapOnGivenPoint(int x, int y){
		TouchAction touchAction = new TouchAction((PerformsTouchActions) getDriver());
		touchAction.tap(PointOption.point(x,y));
		touchAction.perform();
	}
	
	/**
	 * This method is used to get all the elements from a parent element
	 * 
	 * @param by
	 * 
	 * @return list of elements
	 */
	public List<WebElement> findAllElementsFromWithin(By parentby,By by) {
		try {
			Log.info("Find all the elements " + by + " from within the parent element " + parentby);
			return getDriver().findElement(parentby).findElements(by);
		} catch (Exception  e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * This method is used to tap on Android Device back button
	 * @throws InterruptedException 
	 */
	public void tapDeviceBackButton() throws InterruptedException {
		((AndroidDriver) getDriver()).pressKey(new KeyEvent(AndroidKey.BACK));
		Thread.sleep(2000);
	}
	
	/**
	 * This method is used to scroll the screen to up
	 */
	public void scrollUp() {
	    //if pressX was zero it didn't work for me
	    int pressX = getDriver().manage().window().getSize().width / 2;
	    // 4/5 of the screen as the bottom finger-press point
	    int bottomY = getDriver().manage().window().getSize().height * 4/5;
	    // just non zero point, as it didn't scroll to zero normally
	    int topY = getDriver().manage().window().getSize().height / 8;
	    //scroll with TouchAction by itself
	    Log.info("Scroll the screen to upward direction");
	    scroll(pressX, bottomY, pressX, topY);
	}
	
	/**
	 * This method is used to swipe in given direction
	 * 
	 * @param swipingDirection
	 */
	public void swipe(String swipingDirection) {
		//Find startx point which is at right side of screen.
		int startx = (int) (getDriver().manage().window().getSize().width * 0.70);

		//Find endx point which is at left side of screen.
		int endx = (int) (getDriver().manage().window().getSize().width * 0.01);

		//Find vertical point where you wants to swipe. It is in middle of screen height.
		int starty = getDriver().manage().window().getSize().height / 2 ;

		//Swipe from Right to Left.
		if(swipingDirection.equalsIgnoreCase("Right")){
		Log.info("Swipe the screen to right direction");
		this.scroll(startx, starty, endx, starty);}

		//Swipe from Left to Right.
		if(swipingDirection.equalsIgnoreCase("Left")){
			Log.info("Swipe the screen to left direction");
			this.scroll(endx, starty, startx, starty);
		}
	}
	
	/**
	 * This method is used to scroll to top of the screen
	 */
	public void scrollTop() {
	    //if pressX was zero it didn't work for me
	    int pressX = getDriver().manage().window().getSize().width / 3;
	    // 4/5 of the screen as the bottom finger-press point
	    int bottomY = getDriver().manage().window().getSize().height * 4/5;
	    // just non zero point, as it didn't scroll to zero normally
	    int topY = getDriver().manage().window().getSize().height / 5;
	    //scroll with TouchAction by itself
	    scroll(pressX, topY, pressX, bottomY);
	}
	
	/**
	 * This method is used to swipe given element into the given direction
	 * 
	 * @param swipingDirection : direction to swipe
	 * 
	 * @param element : given element
	 */
	public void swipeFromGivenElement(String swipingDirection, WebElement element) {
	//Find startx point which is at right side of screen.
	int startx = (int) (getDriver().manage().window().getSize().width * 0.70);

	//Find endx point which is at left side of screen.
	int endx = (int) (getDriver().manage().window().getSize().width * 0.01);

	//Find vertical point where you wants to swipe. It is in middle of screen height.
	int locationY = element.getLocation().getY();
	int elementHeight = element.getSize().height;
	int starty = (int) (locationY + elementHeight / 2) ;

	System.out.println("startx = " + startx + " ,endx = " + endx + " , starty = " + starty);

	//Swipe from Right to Left.
	if(swipingDirection.equalsIgnoreCase("Right")){
		Log.info("Swipe the screen to right direction");
		this.scroll(startx, starty, endx, starty);}

	//Swipe from Left to Right.
	if(swipingDirection.equalsIgnoreCase("Left")){
		Log.info("Swipe the screen to left direction");
		this.scroll(endx, starty, startx, starty);
		}
	}

	/**
	 * This method is used to scroll the given element to top
	 * 
	 * @param element
	 */
	public void scrollElementToTop(WebElement element) {
	    //if pressX was zero it didn't work for me
	    int pressX = getDriver().manage().window().getSize().width / 2;
	    // 4/5 of the screen as the bottom finger-press point
	    int bottomY = element.getLocation().getY();
	    // just non zero point, as it didn't scroll to zero normally
	    int topY = getDriver().manage().window().getSize().height / 7;
	    //scroll with TouchAction by itself
	    Log.info("Scroll the given element to top");
	    scroll(pressX, bottomY, pressX, topY);
	}
}
