package com.crossover.e2e;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import junit.framework.TestCase;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;


public class GMailTest extends TestCase {
    private WebDriver driver;
    private Properties properties = new Properties();

    public void setUp() throws Exception {
        
        properties.load(new FileReader(new File("src/test/resources/test.properties")));
        //Dont Change below line. Set this value in test.properties file incase you need to change it..
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + properties.getProperty("webdriver.chrome.driver"));
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    public void tearDown() throws Exception {
        driver.quit();
    }

    /*
     * Please focus on completing the task
     * 
     */
    @Test
    public void testSendEmail() throws Exception {
        try {
			driver.get("https://mail.google.com/");
      
			
			WebElement userElement = driver.findElement(By.id("identifierId"));
			userElement.sendKeys(properties.getProperty("username"));

			driver.findElement(By.id("identifierNext")).click();

//			Thread.sleep(5000);

			WebElement passwordElement = driver.findElement(By.name("password"));
			passwordElement.sendKeys(properties.getProperty("password"));
			driver.findElement(By.id("passwordNext")).click();

//			Thread.sleep(5000);

			WebElement composeElement = driver.findElement(By.xpath("//*[@role='button' and text()='Compose']"));
			composeElement.click();

			driver.findElement(By.name("to")).clear();
			driver.findElement(By.name("to")).sendKeys(String.format("%s@gmail.com", properties.getProperty("username")));
			
			// emailSubject and emailbody to be used in this unit test.
			String emailSubject = properties.getProperty("email.subject");
			String emailBody = properties.getProperty("email.body");
			
			driver.findElement(By.cssSelector("input[name=subjectbox]")).sendKeys(emailSubject);
			driver.findElement(By.xpath("//div[@aria-label='Message Body']")).sendKeys(emailBody);
			

			driver.findElement(By.xpath("//*[@role='button' and text()='Send']")).click();

//			Message sent validation point
			boolean flag = driver.findElement(By.xpath("//span[text()='Message sent.']")).isDisplayed();
			
			Assert.assertTrue(flag);
		} catch (Exception e) {
			e.printStackTrace();
		} 
        
    }
}
