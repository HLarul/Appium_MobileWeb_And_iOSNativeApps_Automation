/**
 *
 */
package com.Demo.Automation.util;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.Executor;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

/**
 * @author Divyanshu Garg
 */
public class CommonFunctionLib {
	public AppiumDriver driver;

	RemoteWebDriver rwd;
	WebDriver wd;
	String iOSAppPath;
	public boolean DriverNoResetFlag;

	WebDriverWait wait;
	// Properties properties;
	DesiredCapabilities objCapabilities;
	/**
	 * These objects are made non-static. The objects of this class are
	 * re-created at some point of time.
	 */
	FileWriter fw;
	PrintWriter pw;

	// Constructor with WebDriver argument
	public CommonFunctionLib(AppiumDriver appiumDriver) {
		this.driver = appiumDriver;
	}

	// Constructor with no argument
	public CommonFunctionLib() {

	}

	// private void initPropertiesFile(){
	//
	// properties = new Properties();
	// try {
	// FileReader reader = new FileReader("qa_config.properties");
	// properties.load(reader);
	// }
	// catch (IOException e) {
	// System.out.println("Failed to load Properties file");
	// }
	// }
	public void WebdriverWaitForPage(String time) {
		driver.manage().timeouts()
				.implicitlyWait(Long.parseLong(time), TimeUnit.SECONDS);
	}

	public void WebdriverWaitForPage() {
		WebdriverWaitForPage("180");
	}

	public AppiumDriver StartAppiumDriver(String browserType) {
		try {
			ShutDownDriver();
			switch (browserType.trim()) {
			case "windows-androidsimulator":
				// createStartAndroidEmulator();
/*				TO Run Appium on Window from command line. Use the below process.
 * 			cd C:\Program Files (x86)\Appium
				node.exe "C:/Program Files (x86)/Appium/node_modules/appium/bin/Appium.js" --address 192.168.1.5 -p 4723 --session-override --no-reset
					DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
					Executor executor = new DefaultExecutor();
					executor.setExitValue(1);
					String comand = "cmd /c \"C:/Program Files (x86)/Appium/node.exe\" \"C:/Program Files (x86)/Appium/node_modules/appium/bin/Appium.js\" --address 127.0.0.1 --chromedriver-port 9516 --bootstrap-port 4725 --selendroid-port 8082 --no-reset --local-timezone";
					executor.execute(new CommandLine(comand), resultHandler);
*/				
				iOSAppPath = "demo.apk";
				File appDir = new File(System.getProperty("user.dir")+ "\\tool");
				File androidAppPath = new File(appDir, iOSAppPath.trim());
				objCapabilities = new DesiredCapabilities();
				// objCapabilities.setCapability(MobileCapabilityType.VERSION,
				// properties.getProperty("AndroidVersion")); //18
				objCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,MobilePlatform.ANDROID);
				objCapabilities.setCapability("app", androidAppPath);
				//objCapabilities.setCapability("deviceName","4df1ad83692c7fef");
				objCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME,"4df1ad83692c7fef"); // Android Emulator
				objCapabilities.setCapability("automationName", "Appium");
				objCapabilities.setCapability(
						MobileCapabilityType.BROWSER_NAME, "android");
				// objCapabilities.setCapability("session-override", true);
				objCapabilities.setCapability("autoLaunch", true);
				//objCapabilities.setCapability("language", "en");
				// objCapabilities.setCapability("fullReset", "true"); // for
				// iOS only
				objCapabilities.setCapability("noReset", DriverNoResetFlag);
				//objCapabilities.setCapability("locale", "US");
				//objCapabilities.setCapability("takesScreenshot", true);
				objCapabilities.setCapability("deviceReadyTimeout", "300"); 
				//objCapabilities.setCapability("androidDeviceReadyTimeout","420");
				objCapabilities.setCapability("avdLaunchTimeout", "300000"); 
				objCapabilities.setCapability("newCommandTimeout", "600000");
				//objCapabilities.setCapability("session-override", true);
				// objCapabilities.setCapability("device", "@default");
				// objCapabilities.setCapability("avd", "myAndroidEmulator");
				// //Name of avd to launch
				// objCapabilities.setCapability("appPackage",
				// "com.facebook.katana");
				// objCapabilities.setCapability("appActivity",
				// "com.facebook.katana.DeviceBasedLoginActivity");
				// objCapabilities.setCapability("appWaitActivity", "");
				// objCapabilities.setCapability("appWaitPackage",
				// "com.facebook.katana");
				// objCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS,
				// true);
				try {
					driver = new AndroidDriver(new URL("http://192.168.1.5:4723/wd/hub"), objCapabilities);
					Thread.sleep(1500);
					break;
				} catch (org.openqa.selenium.SessionNotCreatedException ex) {
					try {
						Thread.sleep(3000);
						driver = new AndroidDriver(new URL(
								"http://192.168.1.5:4723/wd/hub"), objCapabilities);
						Thread.sleep(1500);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (UnreachableBrowserException e3) {
					try {
						Thread.sleep(2000);
						driver = new AndroidDriver(new URL("http://192.168.1.5:4723/wd/hub"),
								objCapabilities);
						Thread.sleep(4000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					Assert.fail("failed to start Driver. Skipping further steps.");

				}
				break;
			default:
				System.out.println("None of the DeviceType Case Matched");
				return null;
			}

			return driver;
		} catch (Exception e) {
			return null;
		}
	}

	public void ShutDownDriver() {
		if (driver != null) {
			try {
				try {
					Thread.sleep(2500);
				} catch (InterruptedException e1) {
				}
				driver.closeApp();
				driver.quit();
			} catch (WebDriverException e) {
				try {
					Thread.sleep(2500);
				} catch (InterruptedException e1) {
				}
			}
		}

	}

	public WebElement FindElement(WebElement parentelement, By mobBy,
			int timeoutInSeconds) {
		WebElement webElement;
		try {
			webElement = parentelement.findElement(mobBy);
			return webElement;
		} catch (Exception e) {
			return null;
		}
	}

	public WebElement FindElement(WebElement parentelement,
			MobileLocator LocatorType, String LocatorString) {

		WebElement webElement;
		try {
			switch (LocatorType) {
			case ByAccessibilityId:
				webElement = ((AppiumDriver) parentelement)
						.findElementByAccessibilityId(LocatorString);
				break;
			case ByXPath:
				webElement = parentelement.findElement(MobileBy
						.xpath(LocatorString));
				break;
			case ByClassName:
				webElement = parentelement.findElement(MobileBy
						.className(LocatorString));
				break;
			case ByIosUIAutomation:
				webElement = parentelement.findElement(MobileBy
						.IosUIAutomation(LocatorString));
				break;
			case ByName:
				webElement = parentelement.findElement(By.name(LocatorString));
				break;
			default:
				return null;

			}
			return webElement;
		} catch (Exception e) {
			return null;
		}
	}

	public WebElement FindElement(By mobBy, int timeoutInSeconds) {
		WebElement webElement;
		WebDriverWait wait;
		try {

		} catch (Exception e) {
			return null;
		}
		if (timeoutInSeconds > 0) {
			wait = new WebDriverWait(driver, timeoutInSeconds);
			webElement = wait.until(ExpectedConditions
					.presenceOfElementLocated(mobBy));
		} else {
			webElement = driver.findElement(mobBy);
		}
		return webElement;
	}

	public WebElement FindElement(MobileLocator LocatorType,
			String LocatorString, int timeoutInSeconds) {
		WebElement webElement;
		WebDriverWait wait;
		try {
			switch (LocatorType) {
			case ByAccessibilityId:
				if (timeoutInSeconds > 0) {
					wait = new WebDriverWait(driver, timeoutInSeconds);
					webElement = wait.until(ExpectedConditions
							.presenceOfElementLocated(MobileBy
									.AccessibilityId(LocatorString)));
					// webElement =
					// driver.findElementByAccessibilityId(LocatorString);
					// new WebDriverWait(driver,
					// timeoutInSeconds).until(ExpectedConditions.visibilityOf(webElement));
				} else {
					webElement = driver.findElement(MobileBy
							.AccessibilityId(LocatorString));
				}
				break;
			case ByXPath:
				if (timeoutInSeconds > 0) {
					wait = new WebDriverWait(driver, timeoutInSeconds);
					webElement = wait.until(ExpectedConditions
							.presenceOfElementLocated(MobileBy
									.xpath(LocatorString)));

					// webElement = driver.findElement(By.xpath(LocatorString));
					// new WebDriverWait(driver,
					// timeoutInSeconds).until(ExpectedConditions.visibilityOf(webElement));
				} else {
					webElement = driver.findElement(MobileBy
							.xpath(LocatorString));
				}
				break;
			case ByClassName:
				if (timeoutInSeconds > 0) {
					wait = new WebDriverWait(driver, timeoutInSeconds);
					webElement = wait.until(ExpectedConditions
							.presenceOfElementLocated(MobileBy
									.className(LocatorString)));
					// webElement =
					// driver.findElementByClassName(LocatorString);
					// new WebDriverWait(driver,
					// timeoutInSeconds).until(ExpectedConditions.visibilityOf(webElement));
				} else {
					webElement = driver.findElement(MobileBy
							.className(LocatorString));
				}
				break;
			case ByIosUIAutomation:
				if (timeoutInSeconds > 0) {
					wait = new WebDriverWait(driver, timeoutInSeconds);
					webElement = wait.until(ExpectedConditions
							.presenceOfElementLocated(MobileBy
									.IosUIAutomation(LocatorString)));
					// webElement =
					// driver.findElementByIosUIAutomation(LocatorString);
					// new WebDriverWait(driver,
					// timeoutInSeconds).until(ExpectedConditions.visibilityOf(webElement));
				} else {
					webElement = driver.findElement(MobileBy
							.IosUIAutomation(LocatorString));
				}
				break;
			case ByName:
				if (timeoutInSeconds > 0) {
					wait = new WebDriverWait(driver, timeoutInSeconds);
					webElement = wait.until(ExpectedConditions
							.presenceOfElementLocated(MobileBy
									.name(LocatorString)));
					// webElement =
					// driver.findElementByIosUIAutomation(LocatorString);
					// new WebDriverWait(driver,
					// timeoutInSeconds).until(ExpectedConditions.visibilityOf(webElement));
				} else {
					webElement = driver.findElement(MobileBy
							.name(LocatorString));
				}
			default:
				return null;

			}
			return webElement;
		} catch (Exception e) {
			return null;
		}
	}

	public WebElement getElement(MobileLocator LocatorType,
			String LocatorString, int timeoutInSeconds) {
		WebElement webElement;
		WebDriverWait wait;
		try {
			switch (LocatorType) {
			case ByAccessibilityId:
				if (timeoutInSeconds > 0) {
					wait = new WebDriverWait(driver, timeoutInSeconds);
					webElement = wait.until(ExpectedConditions
							.presenceOfElementLocated(MobileBy
									.AccessibilityId(LocatorString)));
				} else {
					webElement = driver.findElement(MobileBy
							.AccessibilityId(LocatorString));
				}
				break;
			case ByXPath:
				if (timeoutInSeconds > 0) {
					wait = new WebDriverWait(driver, timeoutInSeconds);
					webElement = wait.until(ExpectedConditions
							.visibilityOfElementLocated(MobileBy
									.xpath(LocatorString)));

					// webElement = driver.findElement(By.xpath(LocatorString));
					// new WebDriverWait(driver,
					// timeoutInSeconds).until(ExpectedConditions.visibilityOf(webElement));
				} else {
					webElement = driver.findElement(MobileBy
							.xpath(LocatorString));
				}
				break;
			case ByClassName:
				if (timeoutInSeconds > 0) {
					wait = new WebDriverWait(driver, timeoutInSeconds);
					webElement = wait.until(ExpectedConditions
							.visibilityOfElementLocated(MobileBy
									.className(LocatorString)));
					// webElement =
					// driver.findElementByClassName(LocatorString);
					// new WebDriverWait(driver,
					// timeoutInSeconds).until(ExpectedConditions.visibilityOf(webElement));
				} else {
					webElement = driver.findElement(MobileBy
							.className(LocatorString));
				}
				break;
			case ByIosUIAutomation:
				if (timeoutInSeconds > 0) {
					wait = new WebDriverWait(driver, timeoutInSeconds);
					webElement = wait.until(ExpectedConditions
							.visibilityOfElementLocated(MobileBy
									.IosUIAutomation(LocatorString)));
					// webElement =
					// driver.findElementByIosUIAutomation(LocatorString);
					// new WebDriverWait(driver,
					// timeoutInSeconds).until(ExpectedConditions.visibilityOf(webElement));
				} else {
					webElement = driver.findElement(MobileBy
							.IosUIAutomation(LocatorString));
				}
				break;
			case ByName:
				if (timeoutInSeconds > 0) {
					wait = new WebDriverWait(driver, timeoutInSeconds);
					webElement = wait.until(ExpectedConditions
							.visibilityOfElementLocated(MobileBy
									.name(LocatorString)));
					// webElement =
					// driver.findElementByIosUIAutomation(LocatorString);
					// new WebDriverWait(driver,
					// timeoutInSeconds).until(ExpectedConditions.visibilityOf(webElement));
				} else {
					webElement = driver.findElement(MobileBy
							.name(LocatorString));
				}
			default:
				return null;

			}
			return webElement;
		} catch (Exception e) {
			return null;
		}
	}

	// Writing overloaded getElement() method
	public WebElement getElement(By mobBy, Integer timeoutInSeconds) {
		WebElement webElement;
		WebDriverWait wait;

		try {

			if (timeoutInSeconds > 0) {
				wait = new WebDriverWait(driver, timeoutInSeconds);
				webElement = wait.until(ExpectedConditions
						.presenceOfElementLocated(mobBy));

			} else {

				webElement = driver.findElement(mobBy);

			}

			return webElement;

		} catch (Exception e) {

			return null;

		}

	}

	public boolean ClearInputField(WebElement webElement) {
		try {
			IOSElement mobileWebElement = (IOSElement) webElement;
			mobileWebElement.clear();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean SendKeys_Web(WebElement webElement, String value) {
		boolean state = false;
		webElement.clear();
		try {
			webElement.sendKeys(value);
			state = true;
		} catch (Exception e) {
			state = false;
		}
		return state;
	}

	public boolean SendKeys(WebElement webElement, String value) {
		IOSElement mobileWebElement = (IOSElement) webElement;
		mobileWebElement.setValue(value);
		return true;
	}

	public boolean IOSTap(WebElement webElement) {

		try {
			IOSElement elemIOS = (IOSElement) webElement;

			elemIOS.tap(1, 10);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public boolean WebDriverClick(WebElement webElement) {
		boolean state = false;
		try {
			webElement.click();
			try {
				Thread.sleep(2000);

				// AddToLog("CurrentTestCaseLog", "info",
				// "Passed. Successfully clicked.");
				state = true;
			} catch (InterruptedException e) {
			}
		} catch (Exception e) {

		}
		return state;
	}

	public String GetElementAttributeValue(WebElement objWebElement,
			String attribute) {
		return objWebElement.getAttribute(attribute);
	}

	public boolean AcceptAlert() {
		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean declineAlert() {
		try {
			Alert alert = driver.switchTo().alert();
			alert.dismiss();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void SwipeRight(WebElement element) {
		// Executing swipe on in the case of iOS simulators. Skipping it for
		// Android Chrome as this swipe will not yet implemented on it.
		try {
			if ((!GetDriverInfo().get("DriverName").toLowerCase()
					.equals("androidchrome"))
					&& (!GetDriverInfo().get("DriverName").toLowerCase()
							.equals("chrome"))
					&& (!GetDriverInfo().get("DriverName").toLowerCase()
							.equals("chrome-mac"))
					&& (!GetDriverInfo().get("DriverName").toLowerCase()
							.equals("safari"))) {
				double browser_top_offset = 0.0;
				if (GetDriverInfo().get("DriverType").trim()
						.equalsIgnoreCase("mobile")) {
					browser_top_offset = 0;
				} else if (GetDriverInfo().get("DriverType").trim()
						.equalsIgnoreCase("tablet")) {
					browser_top_offset = 80;
				}
				RemoteWebElement remoteelem = ((RemoteWebElement) element);
				JavascriptExecutor js = (JavascriptExecutor) driver;
				String script = "return Math.max(document.documentElement.clientHeight, window.innerHeight || 0)";
				Long pageheight1 = (Long) js.executeScript(script);
				Long pagewidth1 = (Long) js
						.executeScript("return Math.max(document.documentElement.clientWidth, window.innerWidth || 0)");
				// Long
				// pageheight2=(Long)js.executeScript("return window.innerHeight");
				Point eloc = remoteelem.getLocation();
				double yloc = eloc.getY();
				double xstartloc = eloc.getX();
				double xendloc = eloc.getX() + remoteelem.getSize().width;
				double swipe_startxratio = xstartloc / pagewidth1;
				double swipe_endxratio = xendloc / pagewidth1;
				double elemheight = remoteelem.getSize().getHeight() / 2;
				double yratio = (yloc + elemheight / 2 + browser_top_offset)
						/ pageheight1;
				if (swipe_startxratio < 0.1) {
					swipe_startxratio = 0.1;
				}
				if (swipe_endxratio > 0.9) {
					swipe_endxratio = 0.9;
				}
				HashMap<String, Double> swipeObject = new HashMap<String, Double>();
				swipeObject.put("startX", swipe_endxratio);
				swipeObject.put("startY", yratio);
				swipeObject.put("endX", swipe_startxratio);
				swipeObject.put("endY", yratio);
				swipeObject.put("duration", 0.8);
				js.executeScript("mobile: swipe", swipeObject);
			}
			if (GetDriverInfo().get("DriverName").toLowerCase()
					.equals("chrome")
					| GetDriverInfo().get("DriverName").toLowerCase()
							.equals("chrome-mac")) {
				Actions builder = new Actions(driver);
				element.getSize();

				Action dragAndDrop = builder
						.clickAndHold(element)
						.moveToElement(element, element.getLocation().x + 90,
								element.getLocation().y).release().build();
				dragAndDrop.perform();
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
				}
			}
			if (GetDriverInfo().get("DriverName").toLowerCase()
					.equals("safari")) {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("$('.owl-wrapper').trigger('owl.next')");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void SwipeBottom(WebElement element) {
		if (!GetDriverInfo().get("DriverName").equals("androidchrome")
				&& !GetDriverInfo().get("DriverType").toLowerCase()
						.equals("desktop")) {
			double browser_top_offset = 0.0;
			if (GetDriverInfo().get("DriverType").trim()
					.equalsIgnoreCase("mobile")) {
				browser_top_offset = 0;
			} else if (GetDriverInfo().get("DriverType").trim()
					.equalsIgnoreCase("tablet")) {
				browser_top_offset = 240;
			}
			RemoteWebElement remoteelem = ((RemoteWebElement) element);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			Point eloc = remoteelem.getLocation();
			double yloc = eloc.getY();
			double xloc = eloc.getX() + remoteelem.getSize().width / 2;
			double swipe_xratio = xloc;
			double elemheight = remoteelem.getSize().getHeight();
			double yStartRatio = (yloc + elemheight + browser_top_offset) / 2;
			double yEndRatio = (eloc.getY() + browser_top_offset);
			if (swipe_xratio < 10.0) {
				swipe_xratio = 10.0;
			}
			if (yEndRatio < 50.0) {
				yEndRatio = 50.0;
			}
			HashMap<String, Double> swipeObject = new HashMap<String, Double>();
			swipeObject.put("startX", swipe_xratio);
			swipeObject.put("startY", yStartRatio);
			swipeObject.put("endX", swipe_xratio);
			swipeObject.put("endY", yEndRatio);
			swipeObject.put("duration", 1.0);
			js.executeScript("mobile: swipe", swipeObject);
		}

	}

	public void SwipeLeft(WebElement element) {
		// Executing swipe on in the case of iOS simulators. Skipping it for
		// Android Chrome as this swipe will not yet implemented on it.
		if ((!GetDriverInfo().get("DriverName").toLowerCase()
				.equals("androidchrome"))
				&& (!GetDriverInfo().get("DriverName").toLowerCase()
						.equals("chrome"))
				&& (!GetDriverInfo().get("DriverName").toLowerCase()
						.equals("chrome-mac"))
				&& (!GetDriverInfo().get("DriverName").toLowerCase()
						.equals("safari"))) {
			double browser_top_offset = 0.0;
			if (GetDriverInfo().get("DriverType").trim()
					.equalsIgnoreCase("mobile")) {
				browser_top_offset = 0;
			} else if (GetDriverInfo().get("DriverType").trim()
					.equalsIgnoreCase("tablet")) {
				browser_top_offset = 80;
			}
			RemoteWebElement remoteelem = ((RemoteWebElement) element);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			String script = "return Math.max(document.documentElement.clientHeight, window.innerHeight || 0)";
			Long pageheight1 = (Long) js.executeScript(script);
			Long pagewidth1 = (Long) js
					.executeScript("return Math.max(document.documentElement.clientWidth, window.innerWidth || 0)");
			// Long
			// pageheight2=(Long)js.executeScript("return window.innerHeight");
			Point eloc = remoteelem.getLocation();
			double yloc = eloc.getY();
			double xstartloc = eloc.getX();
			double xendloc = eloc.getX() + remoteelem.getSize().width;
			double swipe_startxratio = xstartloc / pagewidth1;
			double swipe_endxratio = xendloc / pagewidth1;
			double elemheight = remoteelem.getSize().getHeight() / 2;
			double yratio = (yloc + elemheight / 2 + browser_top_offset)
					/ pageheight1;
			if (swipe_startxratio < 0.05) {
				swipe_startxratio = 0.05;
			}
			if (swipe_endxratio > .95) {
				swipe_endxratio = 0.95;
			}

			HashMap<String, Double> swipeObject = new HashMap<String, Double>();
			swipeObject.put("startX", swipe_startxratio);
			swipeObject.put("startY", yratio);
			swipeObject.put("endX", swipe_endxratio);
			swipeObject.put("endY", yratio);
			swipeObject.put("duration", 0.8);
			js.executeScript("mobile: swipe", swipeObject);
		}
		if (GetDriverInfo().get("DriverName").toLowerCase().equals("chrome")
				| GetDriverInfo().get("DriverName").toLowerCase()
						.equals("chrome-mac")) {
			Actions builder = new Actions(driver);
			Action dragAndDrop = builder
					.clickAndHold(element)
					.moveToElement(element, element.getLocation().x + 800,
							element.getLocation().y + 10).release().build();
			dragAndDrop.perform();
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
			}
		}
		if (GetDriverInfo().get("DriverName").toLowerCase().equals("safari")) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("$('.owl-wrapper').trigger('owl.prev')");
		}
	}

	public void PinchOpen() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		HashMap<String, Double> swipeObject = new HashMap<String, Double>();
		swipeObject.put("startX", (double) 114);
		swipeObject.put("startY", (double) 198);
		swipeObject.put("endX", (double) 257);
		swipeObject.put("endY", (double) 256);
		swipeObject.put("duration", 1.8);
		js.executeScript("mobile: pinchOpen", swipeObject);
	}

	public void PinchClose() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		HashMap<String, Double> swipeObject = new HashMap<String, Double>();
		swipeObject.put("startX", (double) 150);
		swipeObject.put("startY", (double) 230);
		swipeObject.put("endX", (double) 200);
		swipeObject.put("endY", (double) 260);
		swipeObject.put("duration", 1.8);
		js.executeScript("mobile: pinchOpen", swipeObject);
	}

	public void Scroll(String Direction) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("direction", Direction);
		js.executeScript("mobile: scroll", scrollObject);
	}

	public void ChangeOrientation(String Orientation) { // Valid values are:
														// "LANDSCAPELEFT" ,
														// "LANDSCAPERIGHT" ,
														// "PORTRAIT"
		JavascriptExecutor js = (JavascriptExecutor) driver;
		// String script = "target.setDeviceOrientation(UIA_DEVICE_ORIENTATION_"
		// + Orientation + ");";
		// js.executeScript(script);
		js.executeScript("target.setDeviceOrientation(UIA_DEVICE_ORIENTATION_LANDSCAPERIGHT);");
	}

	public boolean saveScreenshot(String ImgPath) {
		boolean flag = true;
		try {
			File file = null;
			file = driver.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(file, new File(ImgPath));
			// org.openqa.selenium.WebDriver augmentedDriver = new
			// Augmenter().augment(driver);
			// WebDriver augmentedDriver = (IOSDriver)(new
			// Augmenter().augment(driver));
			// screenshot = ((TakesScreenshot)
			// augmentedDriver).getScreenshotAs(OutputType.FILE);
			//
			// File screenshotfile = new File(ImgPath);
			// FileUtils.copyFile(screenshot, screenshotfile);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	public void saveFullScreenShot(String ImgPath) {
		CaptureBrowserScreenShot fullscreenshot = new CaptureBrowserScreenShot();
		try {
			fullscreenshot.seleniumCaptureBrowserScreenShot(driver, ImgPath);
		} catch (InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean IsElementVisible(final By by) {
		try {
			wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			if ((driver.findElement(by).getSize().height == 0)
					&& (driver.findElement(by).getSize().width == 0)) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e1) {
			return false;
		}
	}

	// Function will return an hashmap with the driverinfo.
	// @author: Divyanshu Garg
	// Usage: System.out.println(GetDriverInfo().get("DriverType") +
	// System.out.println(GetDriverInfo().get("DriverName"));
	public Map<String, String> GetDriverInfo() {
		Map<String, String> DriverInfo = new HashMap<String, String>();
		WebDriver driver = null;
		if (this.wd != null) {
			driver = this.wd;
		} else {
			driver = this.rwd;
		}

		try {
			String DriverType = "";
			String DriverName = "";
			if (driver.getClass().toString().toLowerCase()
					.contains("remotewebdriver")) {
				Capabilities caps = ((RemoteWebDriver) driver)
						.getCapabilities();
				try {
					DriverName = caps.getCapability("device").toString();
				} catch (NullPointerException e) { // to handle Android Chrome
													// case
					String browsername = caps.getCapability("browserName")
							.toString();
					if (browsername.equals("chrome")) {
						DriverName = "androidchrome";
					}
				}

				if (DriverName.toLowerCase().contains("chrome")) {
					DriverType = "Mobile";
				} else if (DriverName.toLowerCase().contains("ipad")) {
					DriverType = "Tablet";
				} else {
					DriverType = "Mobile";
				}
			} else if (driver.getClass().toString().toLowerCase()
					.contains("chrome")) {
				DriverType = "Desktop";
				DriverName = "Chrome";
			} else if (driver.getClass().toString().toLowerCase()
					.contains("safari")) {
				DriverType = "Desktop";
				DriverName = "Safari";
			} else if (driver.getClass().toString().toLowerCase()
					.contains("firefox")) {
				DriverType = "Desktop";
				DriverName = "Firefox";
			}
			DriverInfo.put("DriverType", DriverType);
			DriverInfo.put("DriverName", DriverName);
			return DriverInfo;
		} catch (Exception e) {
			String DriverType = "Mobile";
			String DriverName = "Android";
			DriverInfo.put("DriverType", DriverType);
			DriverInfo.put("DriverName", DriverName);
			return DriverInfo;
			// return null;
		}
	}

	public void ScrollToTop() {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollTo(0,0);");
		} catch (Exception e) {

		}

	}

	public void ScrollToBottom() {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollTo(0,document.documentElement.scrollHeight);");
		} catch (Exception e) {

		}
	}

	public void SwipeTop(WebElement element) {
		try {
			double browser_top_offset = 0.0;
			if (GetDriverInfo().get("DriverType").trim()
					.equalsIgnoreCase("mobile")) {
				browser_top_offset = 0;
			} else if (GetDriverInfo().get("DriverType").trim()
					.equalsIgnoreCase("tablet")) {
				browser_top_offset = 250;
			}
			RemoteWebElement remoteelem = ((RemoteWebElement) element);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			Point eloc = remoteelem.getLocation();
			double yloc = eloc.getY();
			double xloc = eloc.getX() / 2 + remoteelem.getSize().width / 2;
			double swipe_xratio = xloc;
			double elemheight = remoteelem.getSize().getHeight();
			double yStartRatio = (yloc + elemheight / 2 + browser_top_offset) / 2;
			double yEndRatio = (eloc.getY() + browser_top_offset);
			if (swipe_xratio < 10.0) {
				swipe_xratio = 10.0;
			}
			if (yEndRatio < 250.0) {
				yEndRatio = 250.0;
			}
			HashMap<String, Double> swipeObject = new HashMap<String, Double>();
			swipeObject.put("startX", swipe_xratio);
			swipeObject.put("startY", yEndRatio);
			swipeObject.put("endX", swipe_xratio);
			swipeObject.put("endY", yStartRatio);
			swipeObject.put("duration", 1.0);
			js.executeScript("mobile: swipe", swipeObject);
		} catch (Exception e1) {

		}
	}

	public void setGeoLocation(double latitude, double longitude) {
		Location loc = new Location(latitude, longitude, 0);
		driver.setLocation(loc);
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
		}
	}

	public void setGeoLocation() {
		setGeoLocation(
				Double.valueOf(42.346886),
				Double.valueOf(-71.075529));
	}

	public boolean assertLocationAlertPresent(boolean alertShouldBeThereOrNot,
			Long waitingTime) {
		WebDriverWait wait = new WebDriverWait(driver, waitingTime);
		Alert alert = null;
		try {
			alert = wait.until(ExpectedConditions.alertIsPresent());
		} catch (TimeoutException e) {

		}
		Set<String> windowHandles = driver.getWindowHandles();
		if (windowHandles.size() > 1 && alertShouldBeThereOrNot == true) {
			alert = driver.switchTo().alert();
			/**
			 * The alert text should be the one that is shown on IPhone/IPad. It
			 * should have Allow and Dont Allow buttons.
			 */
			String alertText = alert.getText();
			if (!alertText
					.equals("\"Safari\" Would Like to Use Your Current Location")) {
				alert = null;
			}
		}

		if (alert != null) {
			System.out.println("alert is present");
		} else {
			System.out.println("alert is not present");
		}

		if (alertShouldBeThereOrNot == true && alert != null) {
			return true;
		} else if (alertShouldBeThereOrNot == true && alert == null) {
			return false;
		}
		if (alertShouldBeThereOrNot == false && alert != null) {
			return false;
		} else if (alertShouldBeThereOrNot == false && alert == null) {
			return true;
		} else
			return false;
	}

	public void KillWindowProcess(String processName) throws Exception {
		Platform p = Platform.getCurrent();
		if (!p.is(Platform.MAC)) {
			String TASKLIST = "tasklist";
			String KILL = "taskkill /F /IM ";
			Process proc = Runtime.getRuntime().exec(TASKLIST);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					proc.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.contains(processName)) {
					Runtime.getRuntime().exec(KILL + processName);
				}
			}
		}
	}

	public String getElementText(By by, String description) {
		return driver.findElement(by).getText();
	}

	public String getElementAttribute(By by, String attribute,
			String description) {
		return driver.findElement(by).getAttribute(attribute).toString();
	}

	public void SetDriver(AppiumDriver appiumDriver) {
		this.driver = appiumDriver;
	}

	public AppiumDriver SwitchToWebView(AppiumDriver driver2) {
		boolean webviewFound = false;
		try {
			Set<String> contextNames = driver2.getContextHandles();
			int contexts = contextNames.size();
			for (int i = 1; i < 7; i++) {
				if (contexts == 1) {
					Thread.sleep(2000);
					contexts = driver2.getContextHandles().size();
					if (contexts > 1) {
						System.out.println("Found the WebView in " + i
								+ " Iterations");
						webviewFound = true;
						break;
					}
				} else {
					System.out.println("Found the WebView in " + i
							+ " Iterations");
					webviewFound = true;
					break;
				}
			}
			if (!webviewFound) {
				System.out.println("WebView not found");
			}
			driver2.context((String) contextNames.toArray()[1]); // set context
																	// to
																	// WEBVIEW_1
			this.driver = driver2;
			return driver2;
		} catch (Exception e) {
			return null;
		}
	}

	public AppiumDriver SwichToNativeView(AppiumDriver driver2) {
		try {
			driver2.context("NATIVE_APP");
			this.driver = driver2;
			return driver2;
		} catch (Exception e) {
			return null;
		}
	}

	public List<WebElement> FindElements(MobileLocator LocatorType,
			String LocatorString, int timeoutInSeconds) {
		List<WebElement> webElement;
		WebDriverWait wait;
		try {
			switch (LocatorType) {
			case ByAccessibilityId:
				if (timeoutInSeconds > 0) {
					wait = new WebDriverWait(driver, timeoutInSeconds);
					webElement = wait.until(ExpectedConditions
							.visibilityOfAllElementsLocatedBy(MobileBy
									.AccessibilityId(LocatorString)));

				} else {
					webElement = driver.findElements(MobileBy
							.AccessibilityId(LocatorString));
				}
				break;
			case ByXPath:
				if (timeoutInSeconds > 0) {
					wait = new WebDriverWait(driver, timeoutInSeconds);
					webElement = driver.findElements(MobileBy
							.xpath(LocatorString));
					// webElement = wait.until(
					// ExpectedConditions.visibilityOfAllElementsLocatedBy(MobileBy.xpath(LocatorString)));

				} else {
					webElement = driver.findElements(MobileBy
							.xpath(LocatorString));
				}
				break;
			case ByClassName:
				if (timeoutInSeconds > 0) {
					wait = new WebDriverWait(driver, timeoutInSeconds);
					webElement = driver.findElements(MobileBy
							.className(LocatorString));
					// webElement = wait.until(
					// ExpectedConditions.visibilityOfAllElementsLocatedBy(MobileBy.className(LocatorString)));

				} else {
					webElement = driver.findElements(MobileBy
							.className(LocatorString));
				}
				break;
			case ByIosUIAutomation:
				if (timeoutInSeconds > 0) {
					wait = new WebDriverWait(driver, timeoutInSeconds);
					webElement = wait.until(ExpectedConditions
							.visibilityOfAllElementsLocatedBy(MobileBy
									.IosUIAutomation(LocatorString)));

				} else {
					webElement = driver.findElements(MobileBy
							.IosUIAutomation(LocatorString));
				}
				break;
			default:
				return null;

			}
			return webElement;
		} catch (Exception e) {
			return null;
		}
	}

	public List<WebElement> getchildElements(WebElement parentElement,
			String childElement) {
		try {
			return parentElement.findElements(MobileBy.className(childElement));
		} catch (Exception e) {
			return null;
		}
	}

	public boolean isElementPresent(MobileLocator LocatorType,
			String LocatorString, Integer timeoutInSeconds) {
		if (getElement(LocatorType, LocatorString, timeoutInSeconds) != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isElementPresent(By mobBy, Integer timeoutInSeconds) {
		if (getElement(mobBy, timeoutInSeconds) != null) {

			return true;

		} else {

			return false;
		}
	}

	public String getStackTraceAsString(Object e) {
		Exception ex = null;
		try {
			ex = (Exception) e;
			StringBuilder sb = new StringBuilder();
			for (StackTraceElement element : ex.getStackTrace()) {
				sb.append(element.toString());
				sb.append("][");
			}
			return "<b>" + ex.getClass().toString().toUpperCase()
					+ "</b> Here is the stacktrace: " + sb.toString();
		} catch (Exception e2) {
			return "Stack trace not available.";
		}
	}

	public void ScrollToUIATableViewUsingCellIndex(WebElement element, int index) {

		try {

			((IOSElement) element).findElementByIosUIAutomation(".cells()["
					+ index + "].scrollToVisible();");
		} catch (Exception e) {
		}

	}
}
