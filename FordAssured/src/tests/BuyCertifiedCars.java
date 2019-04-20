package tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
//import org.testng.Assert;
import org.testng.annotations.Test;
import reusable.Reusable;
import xpath.xpath;

public class BuyCertifiedCars extends Reusable {

	//Reusable obj=new Reusable();
	WebDriver driver;
	int totcount=0;
	int totrowcount = 0;
	int totdefaultrowcount=0;
	String Make;
	String Model;
	String City;
	String Price;
	String Kms;
	String Year;
	String Fueltype;
	Properties prop;
	
	
	@BeforeClass
	public void invokeDriver() throws IOException
	{
		/*************************Invoke the browser ******************/
		   driver=invokeBrowser();
		
		/*************************Invoke the browser ******************/
	}
	
	@Test(priority = 0)
	public void FillDropdown() throws IOException
	{
		
		
		 
		// Click on the second menu option
		
       driver.findElement(By.xpath(xpath.MENU_XPATH_CERTIFIED_CARS)).click();	
	   WebElement ele=driver.findElement(By.xpath(xpath.XPATH_RESET_BUTTON));
	   WebDriverWait wait=new WebDriverWait(driver, 12);
	   wait.until(ExpectedConditions.visibilityOf(ele));
		
		/*************************Select values from dropdown ******************/
		
		/*Make=CarsData.data[0];
		Model=CarsData.data[1];
		obj.selectFromDropdown(xpath.XPATH_MAKE_DROPDOWN_CITY,Make);
		obj.selectFromDropdown(xpath.XPATH_MAKE_DROPDOWN_MODEL,Model);
		obj.selectFromDropdown(xpath.XPATH_MAKE_DROPDOWN_LOCATION,CarsData.data[2]);
		obj.selectFromDropdown(xpath.XPATH_MAKE_DROPDOWN_PRICE,CarsData.data[3]);
		obj.selectFromDropdown(xpath.XPATH_MAKE_DROPDOWN_KMRANGE,CarsData.data[4]);
		obj.selectFromDropdown(xpath.XPATH_MAKE_DROPDOWN_YRRANGE,CarsData.data[5]);
		obj.selectFromDropdown(xpath.XPATH_MAKE_DROPDOWN_FUEL,CarsData.data[6]);*/
	   
	   
	   File file = new File("C:\\Users\\Ankita Dey\\eclipse-workspace\\FordAssured\\src\\Data\\data.properties");
       //Declare a properties object
       prop = new Properties();
       FileInputStream fileInput=new FileInputStream(file);
       prop.load(fileInput);
       
       Make=prop.getProperty("Make");
       Model=prop.getProperty("Model");
       City=prop.getProperty("City");
       Price=prop.getProperty("Price");
       Kms=prop.getProperty("Kms");
       Year=prop.getProperty("Year");
       Fueltype=prop.getProperty("Fueltype");
       
       selectFromDropdown(xpath.XPATH_MAKE_DROPDOWN_CITY,Make);
		selectFromDropdown(xpath.XPATH_MAKE_DROPDOWN_MODEL,Model);
		selectFromDropdown(xpath.XPATH_MAKE_DROPDOWN_LOCATION,City);
		selectFromDropdown(xpath.XPATH_MAKE_DROPDOWN_PRICE,Price);
		selectFromDropdown(xpath.XPATH_MAKE_DROPDOWN_KMRANGE,Kms);
		selectFromDropdown(xpath.XPATH_MAKE_DROPDOWN_YRRANGE,Year);
		selectFromDropdown(xpath.XPATH_MAKE_DROPDOWN_FUEL,Fueltype);
		
		/*************************Select values from dropdown ******************/
		
	}

	@Test(priority = 1)
	private void validateTotRowCount() 
	{
		
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		WebElement carswithwarranty=driver.findElement(By.xpath(xpath.XPATH_BUY_CAR_LINKK));
		jse.executeScript("arguments[0].click();", carswithwarranty);
		
		
		// check the count object exists or not in the page
	      // System.out.println(driver.findElements(By.xpath("//span[@id='count']")).size());
	 	boolean countcars = (driver.findElements(By.xpath("//span[@id='count']"))).size() > 0 ;
	//	boolean barexists = (driver.findElements(By.xpath("//*[@id='all_cars']/div/div/ul/div/img"))).size() > 0 ;
		
		//if count object exists
		   if(countcars==true)
		    {

				WebElement countdisplayed=driver.findElement(By.xpath("//span[@id='count']"));
				//System.out.println(countdisplayed.getText().toString());
				String stroriginal=countdisplayed.getText().toString();
				
				String strreplaced = stroriginal.replace("(", "").replace(")", "");
				System.out.println("Totcount"+strreplaced);
				totcount=Integer.parseInt(strreplaced);
			
				if(Make.equalsIgnoreCase("Make") && Model.equalsIgnoreCase("Model"))
				{
					totdefaultrowcount = pagination(driver);
					System.out.println(totdefaultrowcount);
					totrowcount=totdefaultrowcount;
				}
				else  // For other values
				{
					totrowcount = pagination(driver);
					System.out.println(totrowcount);
				}
			

				/*************************Calling pagination function and getting total row count ******************/
			
			
			/*************** Validating the total row count with the displayed total results **********************/
			
			
			
				System.out.println(totcount);
				System.out.println(totdefaultrowcount);
				
				if(totcount>=totdefaultrowcount)
				{
					//totrowcount=obj.pagination(driver);
					if(totrowcount==totcount)
					{
						Assert.assertTrue(true);
						System.out.println("Total Count is matched");
					}
					else
					{
						System.out.println("Total Count is not matched");
						Assert.assertFalse(true);
					
					}	
					
					
				}
				else
				{
					System.out.println("Filtering is not working");
					Assert.assertFalse(true);
				}
		    
		   }    
		   else // if count object does not exist in the page
		   {
			 
		    	System.out.println("No cars Found");
				Assert.assertFalse(true);
		   }
		   
		 
	}
	
	@Test(priority = 2)
	public void buyCarClick()
	{
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		//jse.executeScript("window.scrollBy(0,-250)", "");
		WebElement buyout=driver.findElement(By.xpath("//li[@id='checkTab']/following-sibling::li[1]/a"));
		jse.executeScript("arguments[0].click();", buyout);
		
		
		// check the count object exists or not in the page
	      // System.out.println(driver.findElements(By.xpath("//span[@id='count']")).size());
		boolean countcars = (driver.findElements(By.xpath("//span[@id='count']"))).size() > 0 ;
	//	boolean barexists = (driver.findElements(By.xpath("//*[@id='all_cars']/div/div/ul/div/img"))).size() > 0 ;
		
		//if count object exists
		   if(countcars==true)
		    {

				WebElement countdisplayed=driver.findElement(By.xpath("//span[@id='count']"));
				//System.out.println(countdisplayed.getText().toString());
				String stroriginal=countdisplayed.getText().toString();
				
				String strreplaced = stroriginal.replace("(", "").replace(")", "");
				System.out.println("Totcount"+strreplaced);
				totcount=Integer.parseInt(strreplaced);
			
				if(Make.equalsIgnoreCase("Make") && Model.equalsIgnoreCase("Model"))
				{
					totdefaultrowcount = pagination(driver);
					System.out.println(totdefaultrowcount);
					totrowcount=totdefaultrowcount;
				}
				else  // For other values
				{
					totrowcount = pagination(driver);
					System.out.println(totrowcount);
				}
			

				/*************************Calling pagination function and getting total row count ******************/
			
			
			/*************** Validating the total row count with the displayed total results **********************/
			
			
			
				System.out.println(totcount);
				System.out.println(totdefaultrowcount);
				
				if(totcount>=totdefaultrowcount)
				{
					//totrowcount=obj.pagination(driver);
					if(totrowcount==totcount)
					{
						Assert.assertTrue(true);
						System.out.println("Total Count is matched");
					}
					else
					{
						System.out.println("Total Count is not matched");
						Assert.assertFalse(true);
					
					}	
					
					
				}
				else
				{
					System.out.println("Filtering is not working");
					Assert.assertFalse(true);
				}
		    
		   }    
		   else // if count object does not exist in the page
		   {
			 
		    	System.out.println("No cars Found");
				Assert.assertFalse(true);
		   }
		   
		
			
	}
	
	@Test(priority = 3)
	public void SortBy()
	{
		String sortByText=prop.getProperty("SortingBy");
		String xpath="//select[@id='shortByPrice']";
		selectFromDropdown(xpath,sortByText);
	}
	
	
	@AfterClass
	public void closeDriver() throws IOException
	{
		/*************************Invoke the browser ******************/
		  driver.close();
		
		/*************************Invoke the browser ******************/
	}
	
	

}	


