package com.attendanceApp;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.testng.annotations.Test;

import com.attendanceApp.common.SetUp;
import com.attendanceApp.utils.getData;


public class Test1 extends SetUp{
	
  @Test(groups="Smoke", dataProvider="SearchProvider",dataProviderClass=getData.class)
  public void firstTestCase(HashMap<String,String> testData) throws InterruptedException {
	  
		StudentappPage studentappPage = new StudentappPage(SetUp.getDriver());
		
		studentappPage.tapOnStartButton();

		assertThat(studentappPage.getPageHeader()).isEqualTo(testData.get("headerText"));

		studentappPage.selectOptionsFromLoginAs(testData.get("userName"));

		studentappPage.enterUsername(testData.get("userName"));
		
		studentappPage.tapDeviceBackButton();
		
		studentappPage.enterPassword(testData.get("password"));

		studentappPage.tapOnLoginButton();

		assertThat(studentappPage.getListOfAllButtonsDisplayed()).isEqualTo(Arrays.asList(testData.get("expectedButtonsList").split(", ")));

		studentappPage.tapOnGivenButton(testData.get("logoutButtonName"));
  }
}
