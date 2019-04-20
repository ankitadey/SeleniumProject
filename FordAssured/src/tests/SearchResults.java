package tests;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import reusable.Reusable;
import xpath.xpath;

public class SearchResults extends Reusable {
	
	
	@BeforeClass
	public void invokeDriver() throws IOException
	{
		/*************************Invoke the browser ******************/
		   driver=invokeBrowser();
		
		/*************************Invoke the browser ******************/
	}
	
	@Test(dataProvider="getDataForSearch",alwaysRun = true)
	public void getSearchData(String city,String make,String model) throws IOException, InterruptedException
	{
		   
		   selectFromDropdownById("city",city);
		   selectFromDropdownById("make",make);
		   selectFromDropdownById("model",model);
		   driver.findElement(By.xpath(xpath.XPATH_SEARCH_BUTTON)).click();
		   WebElement buycars=driver.findElement(By.cssSelector("ul#tabChange li:nth-of-type(2)"));
		   Boolean isattrpresent=isAttributePresent(buycars,"class");
		   if(isattrpresent)
		   {
			   System.out.println("Redirecting to buy car page");
			   Select sl=new Select(driver.findElement(By.xpath(xpath.XPATH_MAKE_DROPDOWN_CITY)));
			   String selectedValueMake = sl.getFirstSelectedOption().getText();
			   Thread.sleep(3000);
			   Select slmodel=new Select(driver.findElement(By.xpath(xpath.XPATH_MAKE_DROPDOWN_MODEL)));
			   String selectedValueModel = slmodel.getFirstSelectedOption().getText();
			   Thread.sleep(5000);
			   
			   Select slcity=new Select(driver.findElement(By.xpath(xpath.XPATH_MAKE_DROPDOWN_LOCATION)));
			   String selectedValueCity = slcity.getFirstSelectedOption().getText();
			   Thread.sleep(5000);
			  // System.out.println(selectedValue);
			   
			   if(selectedValueMake.equalsIgnoreCase(make) && selectedValueModel.equalsIgnoreCase(model) && selectedValueCity.equalsIgnoreCase(city))
			   {
				   System.out.println("Search is working");
				   Assert.assertTrue(true);
			   }
			   else
			   {
				   System.out.println("Search is not working");
				   Assert.assertTrue(false);
			   }
			   
		   }
		   else
		   {
			   System.out.println("Not redirected to buy page for search");
			   Assert.assertTrue(false);
		   }
		   
	}
	


	@DataProvider
	public static Object[][] getDataForSearch()
	{
		Object[][] data=new Object[1][3];
		data[0][0]="New Delhi";
		data[0][1]="Hyundai";
		data[0][2]="Xcent";
		
		return data;
		
		
	}
	
	@AfterClass
	public void closeDriver() throws IOException
	{
		/*************************Invoke the browser ******************/
		  driver.close();
		
		/*************************Invoke the browser ******************/
	}
	
	
	

}
