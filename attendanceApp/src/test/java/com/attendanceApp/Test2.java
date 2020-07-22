package com.attendanceApp;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.attendanceApp.common.SetUp;
import com.attendanceApp.utils.getData;

public class Test2 extends SetUp{
	
  @Test(groups="Regression", dataProvider="SearchProvider",dataProviderClass=getData.class)
  public void secondTestCase(HashMap<String,String> testData) throws InterruptedException {
	  
		StudentappPage studentappPage = new StudentappPage(SetUp.getDriver());

		studentappPage.tapOnStartButton();

		assertThat(studentappPage.getPageHeader()).isEqualTo(testData.get("headerText"));

		studentappPage.selectOptionsFromLoginAs(testData.get("userName"));

		studentappPage.enterUsername(testData.get("userName"));

		studentappPage.tapDeviceBackButton();

		studentappPage.enterPassword(testData.get("password"));

		studentappPage.tapOnLoginButton();

		studentappPage.tapOnGivenButton(testData.get("addStudentBtnName"));

		Map<String, String> detailsValue = new HashMap<String, String>();
		
		List<String> details = Arrays.asList(testData.get("addMemberDetails").split(";"));
		
		for(String detail : details){
			detailsValue.put(detail.split(",")[0], detail.split(",")[1]);
		}
		
		for (HashMap.Entry<String, String> abc : detailsValue.entrySet()) {
			studentappPage.enterValueForGivenField(abc.getKey(), abc.getValue());
		}

		studentappPage.tapOnRegisterButton();

		studentappPage.tapOnGivenButton(testData.get("viewStudentBtnName"));

		studentappPage.tapOnSubmitButton();

		List<String> expectedList1 = Arrays.asList(testData.get("expectedValues").split("; "));

		assertThat(studentappPage.getListOfAllValuesDisplayed()).isEqualTo(expectedList1);

		studentappPage.tapDeviceBackButton();

		studentappPage.tapDeviceBackButton();

		studentappPage.tapOnGivenButton(testData.get("logoutButtonName"));

	}
}
