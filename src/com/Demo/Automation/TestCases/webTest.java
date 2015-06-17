package com.Demo.Automation.TestCases;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobileCapabilityType;

import java.awt.Component;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


public class webTest{

WebDriver ad;

@AfterClass
public void afterClass()
{
	ad.quit();
}

@Test(priority=1)
public void AppiumAndroidChromeTest() throws MalformedURLException, InterruptedException {
	DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, MobileBrowserType.CHROME);
//    capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,"Android");
    capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,"4d00408661cda0b5");
    capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "4.4.2");
	capabilities.setCapability(MobileCapabilityType.LAUNCH_TIMEOUT,"300000");
	capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT,300);
    try 
    {
		ad = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		ad.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		ad.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		ad.get("http://google.com");
		ad.findElement(By.name("q")).sendKeys("Appium Meetup Noida");
		ad.findElement(By.xpath("//button[@type='submit']")).click();
//		ad.findElement(By.xpath("//article/section[1]/div/a")).click();
		ad.findElement(By.xpath("//li[1]/div/h3/a")).click();
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
	}
 }

	@Test(priority=1)
	public void AppiumAndroidNativeBrowserTest() throws MalformedURLException, InterruptedException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, MobileBrowserType.BROWSER);
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,"Android Emulator");
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "5.0.1");
		capabilities.setCapability(MobileCapabilityType.LAUNCH_TIMEOUT,"300000");
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT,300);
		try
		{
			ad = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
			ad.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			ad.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			ad.get("http://google.com");
			ad.findElement(By.name("q")).sendKeys("Appium Meetup Noida");
			ad.findElement(By.xpath("//button[@type='submit']")).click();
//		ad.findElement(By.xpath("//article/section[1]/div/a")).click();
			ad.findElement(By.xpath("//li[1]/div/h3/a")).click();
			String meetupTitle = ad.findElement(By.xpath("//div[@class='doc-content ']/h1")).getText();
			Assert.assertEquals(meetupTitle,"Appium: Mobile Automation Made Awesome");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally{
			ad.close();
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
//		ad.findElement(By.xpath("//article/section[1]/div/a")).click();
			ad.findElement(By.xpath("//li[1]/div/h3/a")).click();
			String meetupTitle = ad.findElement(By.xpath("//div[@class='doc-content ']/h1")).getText();
			Assert.assertEquals(meetupTitle,"Appium: Mobile Automation Made Awesome");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally{
			ad.close();
		}
}
}