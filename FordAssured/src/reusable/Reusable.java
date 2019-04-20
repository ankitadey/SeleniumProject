package reusable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
//import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Select;


public class Reusable {
	
	public String url=null;
	public String browser1=null;
	public String propertyFilePath=null;
	public WebDriver driver;
	FileInputStream fileInput=null;
	
	/*************************Invoke the browser ******************/
	
	  public WebDriver invokeBrowser() throws IOException
	  {
		
		    File file = new File("C:\\Users\\Ankita Dey\\eclipse-workspace\\FordAssured\\src\\Data\\Config.properties");
	        //Declare a properties object
	        Properties prop = new Properties();
	        fileInput=new FileInputStream(file);
	        prop.load(fileInput);
	        
	        browser1=prop.getProperty("browser");
	        
	        if(browser1.equalsIgnoreCase("chrome"))
			{
				propertyFilePath="D:\\Setup files\\Chrome_Driver\\chromedriver.exe";
				System.setProperty("webdriver.chrome.driver", propertyFilePath);
				/*ChromeOptions opt = new ChromeOptions();
				opt.addArguments("disable-infobars");
				opt.addArguments("--start-maximized");
				opt.addArguments("--disable-extensions");*/
				driver=new ChromeDriver();
			}
			else if(browser1.equalsIgnoreCase("mozilla"))
			{
				propertyFilePath="D:\\Setup files\\Mozilla_Driver\\geckodriver.exe";
				System.setProperty("webdriver.gecko.driver", propertyFilePath);
				driver=new FirefoxDriver();
			}
			else
			{
				propertyFilePath="D:\\Setup files\\IE_Driver_64_bit\\IEDriverServer.exe";
				System.setProperty("webdriver.ie.driver", propertyFilePath);
				driver=new InternetExplorerDriver();
			}
            
	 
	        //Get properties from configuration.properties
	        url=prop.getProperty("url");
	        driver.manage().window().maximize();
	        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);// applicable for every test case
	          
	        driver.get(url);
	        
	        return driver;
	        
	  }
	
	 
	  /*************************Select values from dropdown ******************/
	
	   public void selectFromDropdown(String xPath, String data) {
		   WebElement ele = driver.findElement(By.xpath(xPath));
		   Select sell=new Select(ele);
		   sell.selectByVisibleText(data);
		   
	   }
	   
	   
	   /*************************Select values from dropdown using id ******************/
		
	   public void selectFromDropdownById(String Id, String data) {
		   WebElement ele = driver.findElement(By.id(Id));
		   Select sell=new Select(ele);
		   sell.selectByVisibleText(data);
		   
	   }
	   /**************** Get Data from each page and return rowcount of each page ***********************/
	   public  int getData()
		{
			WebElement table=driver.findElement(By.xpath("//div[@class='content white']"));
			List<WebElement> rows=table.findElements(By.xpath("//div[@class='row-item']/div"));
			int rowcount=rows.size();//3
			System.out.println("Total count "+ rowcount);
			int j;
			String text1="";
			
			for(int i=0;i<rowcount;i++)
			{
				List<WebElement>columns1=rows.get(i).findElements(By.xpath("//div[@class='col-lg-3 col-md-4 col-sm-4']/div/div/a/img"));
				//System.out.println(columns1.size());
				
				for(j=0;j<=i;j++)
				{
			
					text1=columns1.get(j).getAttribute("alt");
					
				}	
				System.out.println(text1);
				
				
			}
			
			return rowcount;
					
		}
	   
	   /*********************** Pagination ********************/
	   
	   public  int pagination(WebDriver driver) {
			int totrowcount=0;
			int pagenumber=1;
			
			// get the pagination links
			List<WebElement> paginationlinks=driver.findElements(By.xpath("//*[@id='Pagination']/span"));
			
			//get the pagination links count in a page
			int countlinks=paginationlinks.size();
			
			// get the last page number
			WebElement lastpage = driver.findElement(By.xpath("//span[@class='pagingnextclass']/preceding::span[1]"));
			
			
			int LastPageNumber=Integer.parseInt(lastpage.getText());
			
			//System.out.println("Total Links: "+countlinks);
			//System.out.println("Last Page Number: "+LastPageNumber);
			
			//if pagination exists
			if(paginationlinks.size()>0)
			{
			
				// Loop iterates until the current page number is less than  lastpagenumber
				while(pagenumber<=LastPageNumber) {
					
					 totrowcount=totrowcount+getData();
					 WebElement nextbutton=driver.findElement(By.xpath("//span[@class='pagingnextclass']"));
					 nextbutton.click();
					 paginationlinks=driver.findElements(By.xpath("//*[@id='Pagination']/span"));
					 countlinks=paginationlinks.size();
					 LastPageNumber=countlinks-2;
					 System.out.println("Total Links: "+countlinks);
				     System.out.println("Last Page Number: "+LastPageNumber);
					 pagenumber++;
					
				}
			}
			else
			{
				System.out.println("pagination doesnot exist");
				totrowcount=getData();
				
			}
			return totrowcount;
		}


	  //Fill the data in the textboxes
		
	   public void FillTextboxes(String xPath, String data) {
		   WebElement ele = driver.findElement(By.xpath(xPath));
		   ele.sendKeys(data);
		   
		   
	   }
	   
	   public boolean isAttributePresent(WebElement element, String attribute) {
		    Boolean result = false;
		    try {
		        String value = element.getAttribute(attribute);
		        if (value != null){
		            result = true;
		        }
		    } catch (Exception e) {}

		    return result;
		}
	   
	  public void closeBrowser() {
		
		driver.close();
		
	}	
	  
	  // remove quotes from start and end of string
	 /* private static String removeQuotesFromStartAndEndOfString(String inputStr) {
		    String result = inputStr;
		    int firstQuote = inputStr.indexOf('\"');
		    int lastQuote = result.lastIndexOf('\"');
		    int strLength = inputStr.length();
		    if (firstQuote == 0 && lastQuote == strLength - 1) {
		        result = result.substring(1, strLength - 1);
		    }
		    return result;
		}*/
	 
	}


