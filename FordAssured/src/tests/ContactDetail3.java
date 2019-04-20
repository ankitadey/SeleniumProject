package tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Data.DataConstraints;
import reusable.Reusable;
import xpath.xpath;

public class ContactDetail3 extends Reusable{
	
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
	String Name;
	String Email;
	String Phone;
	String nameValue;
	String emailValue;
	String mobileValue;
	boolean clickedbutton;
	int sentenceCount = 0; 
	WebElement ele;
	WebDriverWait wait;
	String sCurrentLine;
	int j=0;
	int linecount=0;
	int i=0;

	
	@BeforeClass
	public void invokeDriver() throws IOException
	{
		/*************************Invoke the browser ******************/
		   driver=invokeBrowser();
		
		/*************************Invoke the browser ******************/
	}
	
	@Test(priority = 0)
	public void contactDetail() throws IOException, InterruptedException
	{
		
		 
		// Click on the second menu option
		
        driver.findElement(By.xpath(xpath.MENU_XPATH_CERTIFIED_CARS)).click();	
	    ele=driver.findElement(By.xpath(xpath.XPATH_RESET_BUTTON));
	    wait=new WebDriverWait(driver, 12);
	    wait.until(ExpectedConditions.visibilityOf(ele));
	    
	    List<String> lines = Files.readAllLines(Paths.get("C:\\Users\\Ankita Dey\\eclipse-workspace\\FordAssured\\src\\Data\\temp.txt"));
	   // Here you can easily obtain the list size
	    int linecount=lines.size();
		
		
		
		//read data from notepad and fill the contact form
		 BufferedReader br = null;

	        try {


	            br = new BufferedReader(new FileReader("C:\\Users\\Ankita Dey\\eclipse-workspace\\FordAssured\\src\\Data\\temp.txt"));
	           
	            
	            for(i=0;i<linecount;i++)
	            {
	            	
	            	sCurrentLine = br.readLine();
	            	driver.findElement(By.xpath(xpath.XPATH_CONTACT_DETAIL)).click();
		       	    Thread.sleep(2000);
	            	String[] data=sCurrentLine.split("\\s");
	            	if(data.length>=1)
	            	{
	            		
	            		Name=data[0];
	            		Phone=data[1];
	            		Email=data[2];
	            		
	            		System.out.println("Name : " + Name);
	            		System.out.println("Phone : " + Phone);
	            		System.out.println("Email : " + Email);
	            		
	            		
	            		driver.findElement(By.xpath(xpath.XPATH_NAME)).click(); 
	            		driver.findElement(By.xpath(xpath.XPATH_NAME)).sendKeys(Name);
	            		driver.findElement(By.xpath(xpath.XPATH_EMAIL)).click();
	            		driver.findElement(By.xpath(xpath.XPATH_EMAIL)).sendKeys(Email);
	            		driver.findElement(By.xpath(xpath.XPATH_PHONE)).click();
	            		driver.findElement(By.xpath(xpath.XPATH_PHONE)).sendKeys(Phone);
	            		Thread.sleep(2000);
	            		WebElement btn=driver.findElement(By.xpath("//button[contains(text(),'Get Detail')]"));
            			btn.click();
            			Thread.sleep(3000);
            			
            			boolean errsize=driver.findElements(By.xpath("//div[@class='errorDiv']")).size()>0;
            			
            			if(errsize)
            			{
            				WebElement inlineErr=driver.findElement(By.xpath("//div[@class='errorDiv']"));
            				Thread.sleep(3000);
            				String ErrMsg=inlineErr.getText();
            				
            				Assert.assertEquals(ErrMsg, DataConstraints.Errors[j]);
            				System.out.println(ErrMsg);
            				driver.findElement(By.xpath("//button[@class='close']")).click();
            				Thread.sleep(2000);
            				
            			}
            			else
            			{
            				Assert.assertTrue(true);
            				
            				Thread.sleep(2000);
            				
            				driver.findElement(By.id("sms_code_verify")).click();
            				
            				File file = new File("C:\\Users\\Ankita Dey\\eclipse-workspace\\FordAssured\\src\\Data\\data.properties");
            			       //Declare a properties object
            			       prop = new Properties();
            			       FileInputStream fileInput=new FileInputStream(file);
            			       prop.load(fileInput);
            			       
            			     String Otp=prop.getProperty("otp");
            				driver.findElement(By.id("sms_code_verify")).sendKeys(Otp);
            	 
            				Thread.sleep(2000);
            				driver.findElement(By.xpath(xpath.XPATH_SUBMIT_BUTTON)).click();
            				
            				Thread.sleep(3000);
            				
            				boolean otperrsize=driver.findElements(By.cssSelector(".verfication-span3.otperrordiv")).size()>0;
            				
            				if(otperrsize)
            				{
            					
            					String ErrMsg1=driver.findElement(By.cssSelector(".verfication-span3.otperrordiv")).getText();
            					Thread.sleep(3000);
            					//System.out.println(ErrMsg1);
            					if(ErrMsg1.contains("Invalid"))
            					{
            						Assert.assertEquals(ErrMsg1, DataConstraints.Errors[j+1]);
            						Thread.sleep(2000);
            					}
            					else
            					{
            					    Assert.assertEquals(ErrMsg1, DataConstraints.Errors[j]);
            					    Thread.sleep(2000);
            					}
                				System.out.println(ErrMsg1);
            				}
            				else
            				{
            					Assert.assertTrue(true);
            					Thread.sleep(3000);
            				}
            				
            			}
            			
         
	            	}
	            	
	            	j++;
	            	Thread.sleep(3000);
	            }
	            
	            
	          
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        finally {
	            try {
	                if (br != null)br.close();
	            } catch (IOException ex) {
	                ex.printStackTrace();
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

