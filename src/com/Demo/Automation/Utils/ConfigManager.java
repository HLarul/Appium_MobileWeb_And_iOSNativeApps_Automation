package com.Demo.Automation.Utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import org.openqa.selenium.WebDriver;


public class ConfigManager {
	
	public static ThreadLocal<Properties> commonProp = new ThreadLocal<Properties>();
	public static ThreadLocal<Boolean> ArePropertiesSet = new ThreadLocal<Boolean>();
	public static ThreadLocal<WebDriver> CommonDriver = new ThreadLocal<WebDriver>();
	
	public static void setProperties(){
		try{
				commonProp.set(new Properties());
				FileReader reader=null;
				reader=new FileReader("config.properties");
				commonProp.get().load(reader);
				ArePropertiesSet.set(true);
		}catch(IOException e){
			e.printStackTrace();
			ArePropertiesSet.set(false);
		}		 
	}
	public static Properties getProperties(){
		setProperties();
		return commonProp.get();
	}
	
	public static void getDriver(WebDriver driver){
		CommonDriver.set(driver);
	}

	
}
