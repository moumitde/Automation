

import static org.testng.Assert.assertEquals;
import java.time.Duration;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;


public class EbayAutomation 
{
	WebDriver driver;
	WebDriverWait wait;
	
	@BeforeTest
	public void setUp()
	{
		//Need to use bonigarcia.wdm.WebDriverManager in POM.xml
		WebDriverManager.chromedriver().setup();
		//Open the browser
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		//Navigate to Ebay
		driver.get("https://www.ebay.com/");
		
		//Maximise the window
		driver.manage().window().maximize();
	}

	@Test
	public void verifyItemCanBeAddedToCart()
	{
		//Search for "book" in the search bar
		WebElement searchBox = driver.findElement(By.xpath("//input[@aria-label='Search for anything']"));
		searchBox.sendKeys("book");
		WebElement searchButton = driver.findElement(By.xpath("//input[@type='submit']"));
		searchButton.click();
		
		//Click on the first book
		By bookpath = By.xpath("(//ul[@class='srp-results srp-list clearfix']//div[@class='s-item__image'])[1]");
		
		//wait for the book to load on page
		wait.until(ExpectedConditions.presenceOfElementLocated(bookpath));
		WebElement book = driver.findElement(bookpath);
		String currentWindow = driver.getWindowHandle();
		
		try 
		{
			book.click();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		    e.printStackTrace();
		}
			
		//Navigate to another tab as another webpage is displayed
		Set<String> windowHandles = driver.getWindowHandles();
		for(String window : windowHandles) 
		{
			if(window != currentWindow)
		       driver.switchTo().window(window);
		}
		
		//Click on the add to cart button
		WebElement addToCartButton = driver.findElement(By.xpath("//div[@data-testid='x-atc-action']//a"));
		addToCartButton.click();
		
		//Validate that 1 item was added to the cart
		WebElement itemInCart = driver.findElement(By.xpath("//i[@id='gh-cart-n']"));
		String value= itemInCart.getText();
		assertEquals(value, "1");
			
	}
	
	@AfterTest
	public void cleanUp()
	{
		driver.quit();
	}
	

}
