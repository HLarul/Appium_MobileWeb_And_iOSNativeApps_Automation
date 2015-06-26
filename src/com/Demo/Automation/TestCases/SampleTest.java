package com.Demo.Automation.TestCases;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobileCapabilityType;
import java.awt.Component;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;



public class SampleTest {

	WebDriver ad;
	IOSDriver iosd;

	@AfterClass
	public void afterClass()
	{
//		ad.quit();
	}

	@Test(priority=1)
	public void AppiumAndroidDeviceChromeTest() throws MalformedURLException, InterruptedException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, MobileBrowserType.CHROME);
		//    capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,"Android");
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,"4d00408661cda0b5");
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "4.4.2");
		capabilities.setCapability(MobileCapabilityType.LAUNCH_TIMEOUT,"300000");
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 300);
		try 
		{
			ad = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
			ad.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			ad.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			ad.get("http://google.com");
			ad.findElement(By.name("q")).sendKeys("Appium Meetup Noida");
			ad.findElement(By.xpath("//button[@type='submit']")).click();
			try{
				ad.findElement(By.xpath("//li[1]/div/h3/a")).click();
			}catch(NoSuchElementException ne){
				ad.findElement(By.xpath("//article/section[1]/div/a")).click();
			}
			String meetupTitle = ad.findElement(By.xpath("//div[@class='doc-content ']/h1")).getText();
			Assert.assertEquals(meetupTitle,"Appium: Mobile Automation Made Awesome");


			String Screenshotpath = System.getProperty("user.dir") +  "/Screenshots/";
			File scrFile = ((TakesScreenshot)ad).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(Screenshotpath + "chrome_ss.png"));

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally{
			ad.close();
			ad.quit();
		}
	}

	@Test(priority=2)
	public void AppiumAndroidNativeBrowserTest() throws MalformedURLException, InterruptedException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, MobileBrowserType.BROWSER);
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,"Android Emulator");
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "5.1.1");
		capabilities.setCapability(MobileCapabilityType.LAUNCH_TIMEOUT,"300000");
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT,"300");
		try
		{
			ad = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
			ad.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			ad.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			ad.get("http://google.com");
			ad.findElement(By.name("q")).sendKeys("Appium Meetup Noida");
			ad.findElement(By.xpath("//button[@type='submit']")).click();
			try{
				ad.findElement(By.xpath("//li[1]/div/h3/a")).click();
			}catch(NoSuchElementException ne){
				ad.findElement(By.xpath("//article/section[1]/div/a")).click();
			}

			String meetupTitle = ad.findElement(By.xpath("//div[@class='doc-content ']/h1")).getText();
			Assert.assertEquals(meetupTitle, "Appium: Mobile Automation Made Awesome");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally{
//			ad.close();
			ad.quit();
		}
	}

	@Test(priority=3)
	public void AndroidChromeTestUsingChromeDriver()
	{
		String ChromeBinaryPath = System.getProperty("user.dir") +  "/Tool/chromedriver";
		File file=new File(ChromeBinaryPath);
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());

		DesiredCapabilities capabilities = new DesiredCapabilities();
		ChromeOptions options=new ChromeOptions();
		options.setExperimentalOption("androidPackage", "com.android.chrome");
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);

		try
		{
			ad = new ChromeDriver(capabilities);
			ad.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			ad.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			ad.get("http://google.com");
			ad.findElement(By.name("q")).sendKeys("Appium Meetup Noida");
			ad.findElement(By.xpath("//button[@type='submit']")).click();
			try{
				ad.findElement(By.xpath("//li[1]/div/h3/a")).click();
			}catch(NoSuchElementException ne){
				ad.findElement(By.xpath("//article/section[1]/div/a")).click();
			}
			String meetupTitle = ad.findElement(By.xpath("//div[@class='doc-content ']/h1")).getText();
			Assert.assertEquals(meetupTitle,"Appium: Mobile Automation Made Awesome");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally{
			ad.close();
			ad.quit();
		}
	}

	@Test(priority=4)
	public void iOSWebTestingUsingiPhoneSimulator()
	{
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS"); 
		capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, MobileBrowserType.SAFARI);
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "8.2");
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 6");
		capabilities.setCapability(MobileCapabilityType.LAUNCH_TIMEOUT, "300000");  //ms
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "300"); //sec
//		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,"iPhone 6 Plus");

		capabilities.setCapability("safariIgnoreFraudWarning", true);
		capabilities.setCapability("safariAllowPopups", true);
		capabilities.setCapability("fullReset", false);  // for iOS only
//		capabilities.setCapability("noReset", true);
		capabilities.setCapability("autoAcceptAlerts", true);
//		capabilities.setCapability("orientation", "PORTRAIT");
//		capabilities.setCapability("showIOSLog", true);

		try {
			iosd=new IOSDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
			iosd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			iosd.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			iosd.get("http://google.com");
			iosd.findElement(By.name("q")).sendKeys("Appium Meetup Noida");
			iosd.findElement(By.xpath("//button[@type='submit']")).click();
			try{
				iosd.findElement(By.xpath("//li[1]/div/h3/a")).click();
			}catch(NoSuchElementException ne){
				iosd.findElement(By.xpath("//article/section[1]/div/a")).click();
			}
			String meetupTitle = iosd.findElement(By.xpath("//div[@class='doc-content ']/h1")).getText();
			Assert.assertEquals(meetupTitle,"Appium: Mobile Automation Made Awesome");

			String Screenshotpath = System.getProperty("user.dir") +  "/Screenshots/";
			File scrFile = ((TakesScreenshot)iosd).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(Screenshotpath + "safari_ss.png"));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally{
			iosd.close();
			iosd.quit();
		}
	}

	@Test(priority=5)
	 public void iOSWebTestingUsingiPadMiniDevice()
	{
		String build_location = System.getProperty("user.dir") +  "/Tool/iPadMini_Builds/SafariLauncher.zip";
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
//		capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, MobileBrowserType.SAFARI);
		capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "7.1.1");
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "garvit's iPad");
		capabilities.setCapability("udid","dcdbbe4b6d4f76ec109349143f6cdf7941c461aa");
		capabilities.setCapability(MobileCapabilityType.LAUNCH_TIMEOUT, "300000");  //ms
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "300"); //sec
		capabilities.setCapability(MobileCapabilityType.APP, "/Users/amit.rawat/Desktop/Amit/Projects/Automation_MeetUP/Code/SafariLauncher_Builds/Device/iPadMini/SafariLauncher.app");
		capabilities.setCapability("noReset", true);
		capabilities.setCapability("autoAcceptAlerts", true);


		try {
			iosd=new IOSDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
			iosd.findElement(MobileBy.AccessibilityId("launch safari")).click();
			Thread.sleep(5000);
			Set<String> handles = iosd.getContextHandles();
			iosd.context((String) handles.toArray()[1]);

			iosd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			iosd.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			iosd.get("http://google.com");
			iosd.findElement(By.name("q")).sendKeys("Appium Meetup Noida");
			iosd.findElement(By.xpath("//button[@type='submit']")).click();
			try{
				iosd.findElement(By.xpath("//li[1]/div/h3/a")).click();
			}catch(NoSuchElementException ne){
				iosd.findElement(By.xpath("//article/section[1]/div/a")).click();
			}
			String meetupTitle = iosd.findElement(By.xpath("//div[@id='event-title']/h1")).getText();
			Assert.assertEquals(meetupTitle,"Appium: Mobile Automation Made Awesome");

			String Screenshotpath = System.getProperty("user.dir") +  "/Screenshots/";
			File scrFile = ((TakesScreenshot)iosd).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(Screenshotpath + "safari_ss.png"));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally{
			iosd.close();
			iosd.quit();
		}
	}
	@Test(priority=6)
	public void iOSNativeAppTestingUsingiPadMiniDevice()
	{
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
		capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");
//		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "7.1.1");  //iPadMini
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "8.1.2");  //iPhone6
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "garvit's iPhone (2)");
//		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "garvit's iPad");
//		capabilities.setCapability("udid","dcdbbe4b6d4f76ec109349143f6cdf7941c461aa");  //iPadMini
		capabilities.setCapability("udid","16202d44bfaa64925a4cd6b4ae0a0fd267b1b097");  //iPhone 6 Plus
		capabilities.setCapability(MobileCapabilityType.LAUNCH_TIMEOUT, "300000");  //ms
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "300"); //sec
//		capabilities.setCapability(MobileCapabilityType.APP, "/Users/amit.rawat/Desktop/Amit/Projects/Automation_MeetUP/Code/SafariLauncher_Builds/Sim/SafariLauncher.app");
		capabilities.setCapability(MobileCapabilityType.APP, "/Users/ruchi.bajpai/Desktop/MeetUp/iPhone5_Builds/Equinox.app");
//		capabilities.setCapability("fullReset", true);  // for iOS only
		capabilities.setCapability("noReset", true);
		capabilities.setCapability("autoAcceptAlerts", true);
		capabilities.setCapability("locationServicesAuthorized", true);
		capabilities.setCapability("bundleId","com.equinoxautomation.EquinoxDev");


		try {
			iosd=new IOSDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
			iosd.findElement(MobileBy.AccessibilityId("launch safari")).click();

			String Screenshotpath = System.getProperty("user.dir") +  "/Screenshots/";
			File scrFile = ((TakesScreenshot)iosd).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(Screenshotpath + "safari_ss.png"));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally{
			iosd.close();
			iosd.quit();
		}
	}
}