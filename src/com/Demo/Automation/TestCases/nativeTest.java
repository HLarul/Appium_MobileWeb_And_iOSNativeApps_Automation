package com.Demo.Automation.TestCases;

import io.appium.java_client.AppiumDriver;
import com.Demo.Automation.util.CommonFunctionLib;

import org.junit.Assert;

public class nativeTest {
	AppiumDriver driver;
	CommonFunctionLib commonFunctionLib;
	
	@org.testng.annotations.AfterMethod
	public void afterMethod() {
		commonFunctionLib.ShutDownDriver();
	}

	@org.testng.annotations.BeforeMethod
	public void beforeMethod() {
		commonFunctionLib = new CommonFunctionLib(driver);
		commonFunctionLib.DriverNoResetFlag = true;
		commonFunctionLib.StartAppiumDriver("windows-androidsimulator");
	}
	@org.testng.annotations.Test(enabled=false)
	public void VerifyPlusIconFunctionality() {
		boolean passflag = true;
		try {
System.out.println("Inside Test method");
		} catch (Exception e) {
			passflag = false;
		}
		if (passflag == false) {
			Assert.fail();
		}
	}

}
