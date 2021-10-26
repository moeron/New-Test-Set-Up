package com.Framework;

import com.Framework.Listeners.AssertionLogging;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import pageObjects.Browser.LandingPageObjects;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class BrowserBase extends Base {

    public static RemoteWebDriver driver;
    public static AssertionLogging softAssert = new AssertionLogging();
    public static LandingPageObjects chrome = new LandingPageObjects();

    public static WebDriver capabilities() throws IOException {
        // Creates a FileInputStream by opening a connection to an actual file, the file named by the path name in the file system
        FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"/src/main/java/com/Framework/global.properties");
        // Creates an empty property list with no default values.
        Properties prop=new Properties();
        //Reads a property list (key and element pairs) from the input stream.
        prop.load(fis);

        // Initializing Desired Capabilities, which is what we utilize to set properties for our testing.
        DesiredCapabilities capabilities = new DesiredCapabilities();

        // Setting all the properties below
        // Retrieving the device name from Global Properties and storing it as a property
        String device = (String) prop.get("browserDevice");
        //Telling Appium which device to use
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, device);
        //Telling Appium that we are using Android Studio UI Automator to access the browser and run test automation on the app
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,"uiautomator2");
        //Retrieving the browser name from the Global Properties and storing it
        String browserName =(String) prop.get("browserName");
        //Telling Appium which browser we are using for testing
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, browserName);
        //TODO refactor all global properties and variables so they are the same
        //Providing the capability name we are setting (Chrome Driver Executable ) as well as the file path to the chrome driver
        capabilities.setCapability("chromedriverExecutable","/Users/madison.vincent/IdeaProjects/DemoFramework/src/main/resources/chromedriver");
        //TODO - Using other browsers??
        //Providing platform name and version (Maybe not needed?)
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        //TODO Creating a framework to ensure this works on iOS as well - android and iOS specific browser classes may be needed

        //Setting up the driver
        String address=(String) prop.get("IP");
        driver = new RemoteWebDriver(new URL(address), capabilities);
        return driver;
    }
}
