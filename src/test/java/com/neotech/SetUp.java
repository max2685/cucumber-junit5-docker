package com.neotech;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Screenshots;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.commands.TakeScreenshot;
import com.codeborne.selenide.impl.Screenshot;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import lombok.extern.java.Log;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import static com.codeborne.selenide.Configuration.headless;
import static com.codeborne.selenide.Selenide.sleep;

@Log
public class SetUp {
    private final static String HUB_URL = "http://localhost:4444/wd/hub";

    @Before
    public void setupDriver() {
        Configuration.browserSize = "1024x768";
        headless = true;
        Configuration.timeout = 5000;
        try {
            RemoteWebDriver driver = new RemoteWebDriver(new URL(HUB_URL), DesiredCapabilities.chrome());
            WebDriverRunner.setWebDriver(driver);
            sleep(5000);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @After
    public void quitDriver(Scenario scenario) {
        log.info("DRIVER QUIT");
        if (scenario.isFailed()) {
            try {
                File screenshot = Screenshots.takeScreenShotAsFile();
                InputStream targetStream = new FileInputStream(Objects.requireNonNull(screenshot));
                Allure.addAttachment("FailingCase", "image/png", targetStream, "png");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        WebDriverRunner.getWebDriver().quit();
    }

    private DesiredCapabilities desiredCapabilities() {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        String browser = System.getProperty("browser.name");
        if (browser != null) {
            switch (browser) {
                case "chrome" -> desiredCapabilities = DesiredCapabilities.chrome();
                case "firefox" -> desiredCapabilities = DesiredCapabilities.firefox();
                case "edge" -> desiredCapabilities = DesiredCapabilities.edge();
                case "safari" -> desiredCapabilities = DesiredCapabilities.safari();
                default -> throw new IllegalArgumentException("Invalid browser specified");
            }
        }
        return desiredCapabilities;
    }

//        private static RemoteWebDriver getChrome(final Boolean headless){
//            WebDriverManager.chromedriver().setup();
//            ChromeOptions options = new ChromeOptions();
//            options.setHeadless(headless);
//            options.setAcceptInsecureCerts(true);
//            RemoteWebDriver driver = new ChromeDriver(options);
//            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//            return driver;
//        }
//
//        private static RemoteWebDriver getFirefox(final Boolean headless){
//            WebDriverManager.firefoxdriver().setup();
//            FirefoxOptions options = new FirefoxOptions();
//            options.setHeadless(headless);
//            options.setAcceptInsecureCerts(true);
//            RemoteWebDriver driver =  new FirefoxDriver(options);
//            driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
//            return driver;
//        }
//
//        private static RemoteWebDriver getEdge(final Boolean headless){
//            List<String> osNames = Arrays.asList("Windows 10", "Mac OS X");
//            if (!osNames.contains(osName)){
//                throw new UnreachableBrowserException("Edge browser not available on this platform");
//            }
//            WebDriverManager.edgedriver().setup();
//            EdgeOptions options = new EdgeOptions();
//            options.setAcceptInsecureCerts(true);
//            //if (headless.equals(true)){ // necessary for Selenium 3
//            //    throw new UnsupportedOperationException("Edge does not support headless execution yet");
//            //}
//            //options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
//            //** the following will only work once Selenium 4 is ready.
//            if (headless){
//                options.addArguments("headless");
//            }
//            RemoteWebDriver driver = new EdgeDriver(options);
//            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//            return driver;
//        }
//
//        private static RemoteWebDriver getSafari(final Boolean headless){
//            String osName = System.getProperty("os.name");
//            if (!osName.contentEquals("Mac OS X")){
//                throw new UnreachableBrowserException("Safari browser not available on this platform");
//            }
//            if (headless.equals(true)){
//                throw new UnsupportedOperationException("Safari does not support headless execution yet");
//            }
//            SafariOptions options = new SafariOptions();
//            //options.setAcceptInsecureCerts(true);
//            DesiredCapabilities capabilities = new DesiredCapabilities();
//            capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
//            RemoteWebDriver driver = new SafariDriver(options);
//            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//            return driver;
//        }
}
