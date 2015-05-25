package com.Demo.Automation.Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class CommonFuncLib {
	
	RemoteWebDriver rwd;
	WebDriver wd;
	DesiredCapabilities capability;
	
	public CommonFuncLib()
	{
		ConfigManager.setProperties();
		ConfigManager.getProperties();
	}

	public WebDriver StartDriver(String browserType) throws MalformedURLException
	{
		browserType=ConfigManager.getProperties().getProperty("browserType");
		if(browserType.equalsIgnoreCase("firefox"))
		{
			wd=new FirefoxDriver();
		}
		else if (browserType.equalsIgnoreCase("chrome"))
		{
			File file=new File("D:/DPLAT_Automation/dplat/Tool");
			if(ConfigManager.getProperties().getProperty("grid").equals(true))
			{
				capability=new DesiredCapabilities();
				capability.setBrowserName(ConfigManager.getProperties().getProperty("browserType"));
				capability.setCapability("Platform", ConfigManager.getProperties().getProperty("platform"));
				capability.setVersion("35");
				wd=new RemoteWebDriver(new URL("httP://localhost:4444/wd/hub"),capability);
			}
			else
			{
				System.setProperty("webdriver.chrome.driver", file.getAbsolutePath()+"/chromedriver.exe");
				wd=new ChromeDriver(capability);
			}
			
			wd.manage().window().maximize();
			wd.manage().deleteAllCookies();
			wd.close();
		}
		return wd;
	}

}
