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
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.Demo.Automation.Utils.ConfigManager;

public class TestCase1{

WebDriver wd;

@Test
public void createDriverForAndroidNativeApp() throws MalformedURLException
{
	File appDir=new File("D:/Softwares");
	File appPath=new File(appDir, "/com.whatsapp-v2.12.61-450408-Android-2.1.apk");
	DesiredCapabilities cap=new DesiredCapabilities();
	cap.setCapability(CapabilityType.BROWSER_NAME, "");
	cap.setCapability("platformName","Android");
	cap.setCapability("platformVersion", "5.1");
	//cap.setCapability("deviceName", "4df1ad83692c7fef");
	cap.setCapability("deviceName", "AndroidEmulator");
	cap.setCapability("app", appPath.getAbsolutePath());
	cap.setCapability("appPackage", "com.whatsapp.Main");
	cap.setCapability("appActivity", "com.whatsapp");
	cap.setCapability("autoLaunch", true);
	cap.setCapability("orientation", "LANDSCAPE");
	cap.setCapability("noReset", false);
	cap.setCapability("takesScreenshot", true);
	cap.setCapability("handlesAlerts", true);
	wd = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),cap);
    wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
}

@Test
public void createDriverForAndroid_Device_Web_WithAppium() throws MalformedURLException
{
	DesiredCapabilities capabilities = new DesiredCapabilities();
	ChromeOptions options=new ChromeOptions();
	options.setExperimentalOption("androidPackage", "com.android.chrome");
	capabilities.setCapability(ChromeOptions.CAPABILITY, options);
    capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
    capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,"Android");
    capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,"YT910QEQ6K");
    capabilities.setCapability(MobileCapabilityType.VERSION, "5.0");
    try 
    {
        wd = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        wd.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        wd.get("http://google.com");
        try {
			Thread.sleep(Long.parseLong("10"));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//        wd.close();
//        wd.quit();
    }
    catch (MalformedURLException e) 
    {
        System.out.println("URL init error");
    }
 }
	
	@Test
	public void createDriverForAndroid_Device_Web_WithoutAppium()
	{
		File file=new File("D:/DPLAT_Automation/dplat/Tool");
		File file1=new File(file, "chromedriver.exe");
		DesiredCapabilities cap=new DesiredCapabilities();
		ChromeOptions options=new ChromeOptions();
		options.setExperimentalOption("androidPackage", "com.android.chrome");
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		cap.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
		cap.setCapability(MobileCapabilityType.PLATFORM_NAME,"Android");
		cap.setCapability(MobileCapabilityType.DEVICE_NAME,"YT910QEQ6K");
		cap.setCapability(MobileCapabilityType.VERSION, "5.0");
		System.setProperty("webdriver.chrome.driver", file1.getAbsolutePath());
		wd = new ChromeDriver(cap);
		wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		wd.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		wd.get("http://yahoo.com");
	    try {
				Thread.sleep(Long.parseLong("10"));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//	        wd.close();
//	        wd.quit();
}

	@Test
	public void createDriverForAndroid_Emulator_nativeBrowser() throws MalformedURLException
	{
		DesiredCapabilities capabilities = new DesiredCapabilities();        
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, MobileBrowserType.BROWSER);        
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");        
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "AndroidEmulator");        
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "5.1");        
        wd = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        wd.get("http://www.yahoo.com");
	 }
	
	@Test
	public void createDriverForAndroid_Device_nativeBrowser() throws MalformedURLException
	{
		File file=new File("D:/DPLAT_Automation/dplat/Tool");
		File file1=new File(file, "chromedriver.exe");
		DesiredCapabilities capabilities = new DesiredCapabilities();        
		ChromeOptions options=new ChromeOptions();
		options.setExperimentalOption("androidPackage", "com.android.chrome");
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, MobileBrowserType.BROWSER);        
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");        
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "4df1ad83692c7fef");        
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "4.3");        
        System.setProperty("webdriver.chrome.driver", file1.getAbsolutePath());
		wd = new ChromeDriver(capabilities);
		wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		wd.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		wd.get("http://yahoo.com");
	    try {
				Thread.sleep(Long.parseLong("10"));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//	        wd.close();
//	        wd.quit();
	 }

	
	@Test
	public void DownloadFile_Device_Chrome() throws MalformedURLException
	{
		File file=new File("D:/DPLAT_Automation/dplat/Tool");
		File file1=new File(file, "chromedriver.exe");
		DesiredCapabilities cap=new DesiredCapabilities();
		ChromeOptions options=new ChromeOptions();
		options.setExperimentalOption("androidPackage", "com.android.chrome");
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		cap.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
		cap.setCapability(MobileCapabilityType.PLATFORM_NAME,"Android");
		cap.setCapability(MobileCapabilityType.DEVICE_NAME,"YT910QEQ6K");
		cap.setCapability(MobileCapabilityType.VERSION, "5.0");
		System.setProperty("webdriver.chrome.driver", file1.getAbsolutePath());
		wd = new ChromeDriver(cap);
		wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		wd.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		wd.get("http://filehippo.com/download_ted_notepad/download/b5170d0cbfa092908cb8664513f5ed6b");
	    try {
				Thread.sleep(Long.parseLong("10"));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    wd.findElement(By.xpath("//span[contains(text(),'now downloading')]/preceding-sibling::a")).click();
	    try {
			Thread.sleep(Long.parseLong("5"));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	
	
	@Test
	public void DownloadFile_Emulator_NativeBrowser() throws MalformedURLException
	{
		DesiredCapabilities capabilities = new DesiredCapabilities();        
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, MobileBrowserType.BROWSER);        
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");        
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "AndroidEmulator");        
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "5.1");        
        wd = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        wd.get("http://filehippo.com/download_ted_notepad/download/b5170d0cbfa092908cb8664513f5ed6b");
        try {
			Thread.sleep(Long.parseLong("10"));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    wd.findElement(By.xpath("//span[contains(text(),'now downloading')]/preceding-sibling::a")).click();
    try {
		Thread.sleep(Long.parseLong("5"));
		wd.quit();
	} catch (NumberFormatException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	 }
}