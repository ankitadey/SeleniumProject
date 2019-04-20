package tests;

import java.io.IOException;
//import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
//import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import reusable.Reusable;
import xpath.xpath;

public class HomePage extends Reusable {

	int count;
	int countnextitems;
	String name;
	String newName;
	//ArrayList<String> list1 = new ArrayList<String>();// arraylist list1 which contains string value
	//ArrayList<String> list2 = new ArrayList<String>();
	
	@BeforeClass
	public void invokeDriver() throws IOException
	{
		/*************************Invoke the browser ******************/
		   driver=invokeBrowser();
		
		/*************************Invoke the browser ******************/
	}
	
	@Test(priority = 0)
	public void validateTitleHomePage()
	{
		
		
		
		// click on Home link
		driver.findElement(By.xpath(xpath.XPATH_HOME_LINK)).click();	
		
		// verifying the title of the page
		String actualTitle=driver.getTitle();
		String expectedTitle="Ford Assured";
		if(actualTitle.equalsIgnoreCase(expectedTitle))
		{
			Assert.assertEquals(actualTitle, expectedTitle);
			System.out.println("Title Matched");
		}
		
	
	}
	

	@Test(priority = 1)
	public void validateNavigation() throws IOException
	{
		
		   
		  WebElement carousalnextlink=driver.findElement(By.xpath(xpath.XPATH_CAROUSALNEXT_BUTTON));
		  carousalnextlink.click();
			
		  driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		  Assert.assertTrue(true);
		  System.out.println("Carousal Navigated");
			   
		
	}
	
	// verifying the footer links properly navigating or not
	@Test(priority = 2)
	public void validateFooterLinks() throws InterruptedException
	{
		WebElement footerDriver=driver.findElement(By.xpath(xpath.XPATH_FOOTER_LINK));
		
		//System.out.println(footerDriver.findElements(By.tagName("a")).size());
		String clickonlinktab=Keys.chord(Keys.CONTROL,Keys.ENTER);
		
		int countlinks=footerDriver.findElements(By.tagName("a")).size();
		//System.out.println(countlinks);
		
		if(countlinks>0)
		{
			for(int i=0;i<countlinks;i++)
			{
				footerDriver.findElements(By.tagName("a")).get(i).sendKeys(clickonlinktab);
				
			}
			
			// Get current window handle
	        String parentWinHandle = driver.getWindowHandle();
	        
	        Set<String> winHandles=driver.getWindowHandles();//get all the currently opened windows and store in set
			
			for(String handle: winHandles){
				
	            if(!handle.equals(parentWinHandle))
	            {
					
				  driver.switchTo().window(handle);
				  
				  Thread.sleep(1000);
				
				  String currentURL=driver.getCurrentUrl();
				//System.out.println(currentURL);
			
					if(currentURL.contains("sell_exchange"))
					{
						System.out.println("Navigated to sell exchange page");
						Assert.assertTrue(true);
					}
					else if(currentURL.contains("car_available"))
					{
						System.out.println("Navigated to Buy Certified Cars page");
						Assert.assertTrue(true);
					}
					else if(currentURL.contains("ford_dealer"))
					{
						System.out.println("Navigated to Ford Dealers page");
						Assert.assertTrue(true);
					}
					else if(currentURL.contains("car_valuation"))
					{
						System.out.println("Navigated to car valuation page");
						Assert.assertTrue(true);
					}
					else
					{
						Assert.assertTrue(false);
					}
					
					System.out.println("Closing the new window...");
		            driver.close();
			  }
			
			}
		}
		
		else
		{
			System.out.println("Links do not exist in footer");
			
		}
		
	}
	
	
	@AfterClass
	public void closeDriver() throws IOException
	{
		/*************************Invoke the browser ******************/
		  driver.quit();
		
		/*************************Invoke the browser ******************/
	}
	
	
	
}
	




