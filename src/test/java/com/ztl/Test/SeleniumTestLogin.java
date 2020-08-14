
package com.ztl.Test;

import java.util.concurrent.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
		
		String inappserverUrl = System.getProperty("app.server.url");
		String appServerUrl = "http://www.google.com";
		if (inappserverUrl != null) {
			appServerUrl = inappserverUrl;
		}

		if (bGrid) {
			// DesiredCapabilities capability = null;

			System.out.println("On Grid...");
			String serverUrl = System.getProperty("grid.server.url");
			String gridServerUrl = "http://seleniumhub:4444/wd/hub";
			if (serverUrl != null) {
				gridServerUrl = serverUrl;
			}
			URL gridUrl = new URL(gridServerUrl);

			if ("firefox".equals(sBrowser)){
				System.out.println("firefox...");
				// driver = new RemoteWebDriver(gridUrl, DesiredCapabilities.firefox());
				DesiredCapabilities capability = DesiredCapabilities.firefox();
				driver = new RemoteWebDriver(gridUrl,capability);
				// capability.setBrowserName(“firefox” );
				// capability.setPlatform(“WINDOWS”);
				// capability.setVersion(“4”);
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(1, TimeUnit.MINUTES);
			}
			else
			{
				System.out.println("chrome...");
				driver = new RemoteWebDriver(gridUrl, DesiredCapabilities.chrome());
			}
		  } 
		  else {
			System.out.println("Local driver...");
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
			driver.get(appServerUrl);
		  }
	}

	@After
	public void tearDownWebDriver() {
		driver.quit();
	}

	@Test
	public void pageTitleIsNotNull() throws MalformedURLException {
		assertTrue(driver.getTitle() != null);
	}

	@Test
	public void pageTitleContainsGoogle() throws MalformedURLException {
		// WebElement element = driver.findElement(By.name("q"));
		// element.sendKeys("Cheese!");
		// element.submit();
		assertFalse(driver.getTitle().contains("Google"));
	}

	@Test
	public void pageTitleContainsAppName() throws MalformedURLException {
		WebDriverWait wait = new WebDriverWait(driver, 1);
		wait.until(ExpectedConditions.titleIs("Java Simple Login Web App"));
		assertTrue(driver.getTitle().contains("Java Simple Login Web App"));
	}

	@Test
	public void pageTitleContainsRegister() throws MalformedURLException {
		WebElement element = driver.findElement(By.id("register"));
		element.click();
		assertTrue(driver.getTitle().contains("Registration"));
	}

	@Test
	public void pageTitleContainsRegisterbacktoLogin() throws MalformedURLException {
		WebElement element = driver.findElement(By.id("register"));
		element.click();
		assertTrue(driver.getTitle().contains("Registration"));
		driver.findElement(By.xpath("//input[@name='firstName']")).sendKeys("userfirstname");
        driver.findElement(By.xpath("//input[@name='lastName']")).sendKeys("userlastname");
		WebElement element1 = driver.findElement(By.id("login"));
		element1.click();
		assertTrue(driver.getTitle().contains("Java Simple Login Web App"));
	}
}