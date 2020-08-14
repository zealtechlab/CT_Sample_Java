
package com.ztl.Test;

import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SeleniumTestLogin {

	private WebDriver driver;

	@Before
	public void setUp() throws MalformedURLException {
		String sBrowser = System.getProperty("browsertype");
		System.out.println(sBrowser);
		Boolean bHeadless = Boolean.getBoolean("headless");
		System.out.println(bHeadless);
		Boolean bGrid = Boolean.getBoolean("grid");
		System.out.println(bGrid);
		DesiredCapabilities capability = null;

		if (bGrid) {
			System.out.println("On Grid...");
			String serverUrl = System.getProperty("grid.server.url");
			String gridServerUrl = "http://seleniumhub:4444/wd/hub";
			if (serverUrl != null) {
				gridServerUrl = serverUrl;
			}

			if ("firefox".equals(sBrowser)){
				System.out.println("firefox...");
				// DesiredCapabilities capability = DesiredCapabilities.firefox();
				FirefoxOptions options = new FirefoxOptions();
				options.setHeadless(bHeadless);
				capability.merge(options);
			}
			else
			{
				System.out.println("chrome...");
				// DesiredCapabilities capability = DesiredCapabilities.chrome();
				ChromeOptions options = new ChromeOptions();
				options.setHeadless(bHeadless);
				options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
				capability.merge(options);
			}

			URL gridUrl = new URL(gridServerUrl);
			driver = new RemoteWebDriver(gridUrl, capability);
		  } 
		  else {
			System.out.println("Local driver...");
			// System.setProperty("webdriver.gecko.driver", "../resources/geckodriver");
			if ("firefox".equals(sBrowser)){
				System.out.println("firefox...");
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
			}
			else {
				System.out.println("chrome...");
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
			}
		  }

		String inappserverUrl = System.getProperty("app.server.url");
		String appServerUrl = "http://www.google.com";
		if (inappserverUrl != null) {
			appServerUrl = inappserverUrl;
		}
		driver.get(appServerUrl);
	}

	@After
	public void tearDownWebDriver() {
		driver.quit();
	}

	@Test
	public void pageTitleIsNotNull() throws MalformedURLException {
		WebElement element = driver.findElement(By.name("q"));
		element.sendKeys("Cheese!");
		element.submit();
		assertTrue(driver.getTitle() != null);
	}

	@Test
	public void pageTitleContainsGoogle() throws MalformedURLException {
		WebElement element = driver.findElement(By.name("q"));
		element.sendKeys("Cheese!");
		element.submit();
		assertTrue(driver.getTitle().contains("Google"));
	}

	@Test
	public void pageTitleContainsAppName() throws MalformedURLException {
		assertTrue(driver.getTitle().contains("Java Simple Login Web App"));
	}

	@Test
	public void pageTitleContainsRegister() throws MalformedURLException {
		WebElement element = driver.findElement(By.name("register"));
		element.submit();
		assertTrue(driver.getTitle().contains("Registration"));
	}

}