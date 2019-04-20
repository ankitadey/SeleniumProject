package tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Data.ValuationConstraints;
import reusable.Reusable;
import xpath.xpath;


public class CarValuation extends Reusable{
	
	Properties prop;
	String errmsg;
	String ExpectedErr;
	
	Boolean isNoError=false;
	
	@BeforeClass
	public void invokeDriver() throws IOException
	{
		/*************************Invoke the browser ******************/
		   driver=invokeBrowser();
		
		/*************************Invoke the browser ******************/
	}
	

	@Test
	public void getDetailsValuation() throws IOException, InterruptedException
	{
	   
	   driver.get("https://fordassured.in/car_valuation.php");
	   
	  // Thread.sleep(3000);
	   
	 //  driver.findElement(By.id("ibbyearDetail")).click();
	   
	   Thread.sleep(2000);
	   
	   File file = new File("C:\\Users\\Ankita Dey\\eclipse-workspace\\FordAssured\\src\\Data\\valuation.properties");
       //Declare a properties object
       prop = new Properties();
       FileInputStream fileInput=new FileInputStream(file);
       prop.load(fileInput);
       
       String year= prop.getProperty("Year");
       String make=prop.getProperty("Make");
       String model=prop.getProperty("Model");
       String variant=prop.getProperty("Variant");
       String city=prop.getProperty("City");
       
       if(year.isEmpty())
       {
    	   selectFromDropdown(xpath.XPATH_VALUATION_YEAR,"--Select Year--");
    	   
    	   driver.findElement(By.xpath("//input[@value='Check Valuation']")).click();
    	   
    	   errmsg=driver.findElement(By.id("yearErrorDetail")).getText();//actual result
    	   ExpectedErr=ValuationConstraints.Errors[0];
    	   Assert.assertEquals(errmsg, ExpectedErr);
    	   System.out.println("Please select year");
       }
       else
       {
    	   selectFromDropdown(xpath.XPATH_VALUATION_YEAR,year);
    	   
    	   if(make.isEmpty())
           {
        	   selectFromDropdown(xpath.XPATH_VALUATION_MAKE,"--Select Make--");
        	   
               driver.findElement(By.xpath("//input[@value='Check Valuation']")).click();
        	   
               errmsg=driver.findElement(By.id("makeErrorDetail")).getText();//actual result
        	   ExpectedErr=ValuationConstraints.Errors[1];
        	   Assert.assertEquals(errmsg, ExpectedErr);
        	   System.out.println("Please select make");
           }
           else
           {
        	   selectFromDropdown(xpath.XPATH_VALUATION_MAKE,make);
        	   
        	   if(model.isEmpty())
        	   {
        		   selectFromDropdown(xpath.XPATH_VALUATION_MODEL,"--Select Model--");
            	   
                   driver.findElement(By.xpath("//input[@value='Check Valuation']")).click();
            	   
                   errmsg=driver.findElement(By.id("modelErrorDetail")).getText();//actual result
            	   ExpectedErr=ValuationConstraints.Errors[2];
            	   Assert.assertEquals(errmsg, ExpectedErr);
            	   
            	   System.out.println("Please select model");
        	   }
        	   else
        	   {
        		   selectFromDropdown(xpath.XPATH_VALUATION_MODEL,model);
        		   
        		   if(variant.isEmpty())
        		   {
        			   selectFromDropdown(xpath.XPATH_VALUATION_VARIATION,"--Select variant--");
                	   
                       driver.findElement(By.xpath("//input[@value='Check Valuation']")).click();
                	   
                       errmsg=driver.findElement(By.id("variantErrorDetail")).getText();//actual result
                	   ExpectedErr=ValuationConstraints.Errors[3];
                	   Assert.assertEquals(errmsg, ExpectedErr);
                	   
                	   System.out.println("Please select variant");
        		   }
        		   else
        		   {
        			   selectFromDropdown(xpath.XPATH_VALUATION_VARIATION,variant);
        			   
        			   if(city.isEmpty())
        			   {
        				   selectFromDropdown(xpath.XPATH_VALUATION_CITY,"--Select City--");
                    	   
                           driver.findElement(By.xpath("//input[@value='Check Valuation']")).click();
                    	   
                           errmsg=driver.findElement(By.id("cityErrorDetail")).getText();//actual result
                    	   ExpectedErr=ValuationConstraints.Errors[4];
                    	   Assert.assertEquals(errmsg, ExpectedErr);
                    	   
                    	   System.out.println("Please select city");
        				   
        			   }
        			   else
        			   {
        				   selectFromDropdown(xpath.XPATH_VALUATION_CITY,city);
        				   isNoError=true;
        				   
        			   }
        		   }
        	   }
           }
    	   
    	   
       }
       
     
       
       
       
       WebElement BuyerId=driver.findElement(By.xpath("//div[@class='col-md-12 mrg-T20 mrg-B20']//label[2]"));
       WebElement SellerId=driver.findElement(By.xpath("//div[@class='col-md-12 mrg-T20 mrg-B20']//label[3]"));
       
       //System.out.println(BuyerId.getText());
       
       String radioselectedvalue=prop.getProperty("Selection");
       
       if(radioselectedvalue.equalsIgnoreCase("Buyer"))
       {
    	   BuyerId.click();
       }
       else
       {
    	   SellerId.click();
       }
       
       if(isNoError)
       {
    	   driver.findElement(By.xpath("//input[@value='Check Valuation']")).click();
    	   Thread.sleep(3000);
    	   
    	   if(driver.findElements(By.xpath("//table[@id='TableIbb']")).size()>0)
    	   {
    		   System.out.println("Search Result Exists");
    	   }
    	   else
    	   {
    		   System.out.println("Search Result does not Exist");
    	   }
       }
       
       
	   
	}
	
	
	@AfterClass
	public void closeDriver() throws IOException
	{
		/*************************Invoke the browser ******************/
		  driver.close();
		
		/*************************Invoke the browser ******************/
	}

}
