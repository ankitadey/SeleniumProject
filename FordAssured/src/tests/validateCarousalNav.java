package tests;

import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import reusable.Reusable;


public class validateCarousalNav extends Reusable {

	int count;
	int countnextitems;
	String name;
	String newName;
	String xpath = "//h3[@class='recent-list-heading']";
	ArrayList<String> list1 = new ArrayList<String>();// arraylist list1 which contains string value
	ArrayList<String> list2 = new ArrayList<String>();
	
	@Test
	public void validateNavigation() throws IOException
	{
		// invoke the driver
		   driver=invokeBrowser();
		   
		  count=driver.findElements(By.xpath("//h3[@class='recent-list-heading']")).size();
		  System.out.println(count);
		   
		   for(int i=0;i<count;i++)
		   {
			   //int index = i+1;
			   name=driver.findElements(By.xpath(xpath)).get(i).getText();
			   list1.add(name);
		   }
		   
		   System.out.println(list1);
		   
		   JavascriptExecutor jse = (JavascriptExecutor)driver;
			WebElement carousalnextlink=driver.findElement(By.xpath("//a[@class='next next-mrg']"));
			jse.executeScript("arguments[0].click();", carousalnextlink);
			
			  countnextitems=driver.findElements(By.xpath("//h3[@class='recent-list-heading']")).size();
			 // System.out.println(countnextitems);
			  
			  
			  for(int i=0;i<countnextitems;i++)
			   {
				  // int index = i+1;
				   newName=driver.findElements(By.xpath(xpath)).get(i).getText();
				   list2.add(newName);
			   }
			   
			   System.out.println(list2);
			   
			 //Comparing the Two Array Lists

			   boolean commonList = list1.retainAll(list2);
               
			   if(commonList)
			   {
				 System.out.println("Carousal Navigated");
			     Assert.assertTrue(commonList);
			   }
		
	}
}
