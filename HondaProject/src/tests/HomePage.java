package tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
//import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import reusable.Reusable;
import xpath.xpath;

public class HomePage extends Reusable {

	int count;
	int countnextitems;
	String name;
	String newName;
	File file=null;
	FileInputStream fileInput=null;
	WebElement SearchId;
	String cityselected;
	Properties prop;
	
	@BeforeClass
	public void invokeDriver() throws IOException, InterruptedException
	{
		/*************************Invoke the browser ******************/
		   driver=invokeBrowser();
		
		/*************************Invoke the browser ******************/
		   
		   
		  // select your city
		   
		    file = new File("C:\\Users\\Ankita Dey\\eclipse-workspace\\HondaProject\\src\\Data\\Config.properties");
	        //Declare a properties object
	         prop = new Properties();
	        fileInput=new FileInputStream(file);
	        prop.load(fileInput);
	        
	        String YourCity=prop.getProperty("YourCity");
	        
	        if(YourCity.contains("Bangalore"))
	        {
	        	driver.findElement(By.xpath("(//div[@class='city_img'])[1]")).click();
	        }
	        else
	        {
	        	driver.findElement(By.xpath("(//div[@class='city_img'])[2]")).click();
	        }
	        
	        cityselected=driver.findElement(By.xpath("//span[@class='input-group-addon city-popup']//span/i/parent::span")).getText();
	        Assert.assertEquals(cityselected, YourCity);
	        System.out.println("You have selected the city "+YourCity);
	        Thread.sleep(5000);
	}
	
	@Test(priority = 0)
	public void validateTitleHomePage()
	{
		
		// verifying the title of the page
		String actualTitle=driver.getTitle();
		String expectedTitle="Honda Auto Terrace | Honda’s one-stop facility for Buying, Selling and Exchange of Used Cars";
		if(actualTitle.equalsIgnoreCase(expectedTitle))
		{
			Assert.assertEquals(actualTitle, expectedTitle);
			System.out.println("Title Matched");
		}
	}
	
	@Test(priority = 1)
	public void validateNavigation() throws IOException
	{
		
		   
		/* WebElement carousalnextlink=driver.findElement(By.xpath(xpath.XPATH_CAROUSALNEXT_BUTTON));
		  carousalnextlink.click();
			
		  driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		  Assert.assertTrue(true);
		  System.out.println("Carousal Navigated");*/
			   
		
	}
	
	@Test(priority = 2,dataProvider="getDataForSearch",alwaysRun = true)
	public void validateSearch(String CarName) throws InterruptedException
	{
		SearchId=driver.findElement(By.id("serach_used_car"));
		SearchId.click();
		SearchId.clear();
		SearchId.sendKeys(CarName);
		Thread.sleep(2000);
		SearchId.sendKeys(Keys.ARROW_DOWN);
		SearchId.sendKeys(Keys.ENTER);
		Thread.sleep(5000);
		String SearchText=driver.findElement(By.xpath("//h1[@class='filter-head']")).getText();
		if(SearchText.contains(CarName))
		{
			System.out.println("Searched Result Found "+SearchText);
			Assert.assertTrue(true);
		}
		else
		{
			System.out.println("Searched Result Not Found");
			Assert.assertFalse(true);
		}
		
		JavascriptExecutor js = (JavascriptExecutor) driver; 
		js.executeScript("window.history.go(-1)");
		
	}
	
	    // verifying the footer links properly navigating or not
		@Test(priority = 3)
		public void validateFooterLinks() throws InterruptedException
		{
			
			
			WebElement footerDriver=driver.findElement(By.xpath(xpath.XPATH_FOOTER_LINK));
			
			String clickonlinktab=Keys.chord(Keys.CONTROL,Keys.ENTER);
			
			int countlinks=footerDriver.findElements(By.tagName("a")).size();
			//System.out.println(countlinks);
			
			if(countlinks>0)
			{
				System.out.println("Validating: "+driver.findElement(By.className("heading")).getText()+" "+"Links.");
				for(int i=0;i<countlinks;i++)
				{
					footerDriver.findElements(By.tagName("a")).get(i).sendKeys(clickonlinktab);
					Thread.sleep(3000);
					
				}
				
				// Get current window handle
		        String parentWinHandle = driver.getWindowHandle();
		        
		        Set<String> winHandles=driver.getWindowHandles();//get all the currently opened windows and store in set
				
				for(String handle: winHandles){
					
		            if(!handle.equals(parentWinHandle))
		            {
						
					  driver.switchTo().window(handle);
					  
					  Thread.sleep(2000);
					
					  String currentURL=driver.getCurrentUrl();
					//System.out.println(currentURL);
				
						if(currentURL.contains("Check_Points"))
						{
							System.out.println("Navigated to check points page");
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
		
	
	
	

	
	
	
	
	
	@DataProvider
	public static Object[][] getDataForSearch()
	{
		Object[][] data=new Object[1][1];
		data[0][0]="Honda Accord";
		
		return data;
		
		
	}
	
	
	
	
	
	
}
	




