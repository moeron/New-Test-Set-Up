package com.Framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class Base {
    public static AppiumDriverLocalService service;
    public static AndroidDriver<AndroidElement>  driver;
    public static AssertionLogging softAssert = new AssertionLogging();

    public AppiumDriverLocalService startServer()
    {
        //
        boolean flag=	checkIfServerIsRunnning(4723);
        if(!flag)
        {

            service=AppiumDriverLocalService.buildDefaultService();
            service.start();
        }
        return service;

    }

    public static boolean checkIfServerIsRunnning(int port) {

        boolean isServerRunning = false;
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);

            serverSocket.close();
        } catch (IOException e) {
            isServerRunning = true;
        } finally {
            serverSocket = null;
        }
        return isServerRunning;
    }

    public static void startEmulator() throws IOException, InterruptedException
    {
        Runtime.getRuntime().exec(System.getProperty("user.dir")+"/src/main/resources/startEmulator.bat");
        Thread.sleep(6000);
    }

    public static  AndroidDriver<AndroidElement> capabilities(String appName) throws IOException, InterruptedException
    {
        FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"/src/main/java/com/Framework/global.properties");
        Properties prop=new Properties();
        prop.load(fis);
        File appDir = new File("/Users/madison.vincent/IdeaProjects/DemoFramework/src/main/resources/");
        File app = new File(appDir, (String) prop.get(appName));
        DesiredCapabilities capabilities = new DesiredCapabilities();
        String device=(String) prop.get("device");

        if(device.contains("emulator"))
        {
            startEmulator();
        }

        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, device);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,"uiautomator2");
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT,30);
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());

        driver = new AndroidDriver<>(new URL("http://192.168.50.58:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

    public static void getScreenshot(String s) throws IOException
    {
        File scrfile=	((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrfile,new File(System.getProperty("user.dir")+"\\"+s+".png"));
    }

}

