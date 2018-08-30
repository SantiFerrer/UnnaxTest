import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

class UnnaxLinksTest_old {

	public static WebDriver driver;
	public static String mainpage = "https://www.unnax.com/";
	
	@BeforeAll
	public static void setUp() {
		System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\Drivers\\chromedriver.exe");
    	driver = new ChromeDriver();
    	driver.manage().window().maximize();
	}
	
	@Test
	void test() {
				
		List<String> testButtons = new ArrayList<String>();

		testButtons.add("//a[@href='/en/lending-companies']/button");
		testButtons.add("//a[@href='/en/shared-economy-sector']/button");
		testButtons.add("//a[@href='/en/banking-sector']/button");
		testButtons.add("//a[@href='/en/crowdfunding-sector']/button");
		
        linksTest(testButtons, mainpage);    
        
	}
	
	@AfterAll
	public static void tearDown() {
		driver.close();
	}
	
	void linksTest(List<String> xpathLinks, String mainpage) {
		
		int linkNum = 1;
		for (Iterator<String> i = xpathLinks.iterator(); i.hasNext();) {
        	
        	String value = i.next();
        	driver.get(mainpage);
        		
        	if (driver.findElements(By.xpath(value)).size() > 0) {
        		WebElement element = driver.findElement(By.xpath(value));
        		
        		int counter = 0;
        		do {element.click(); counter++;} while (driver.getCurrentUrl().equals(mainpage) && counter < 5);
        		        		
        		if (driver.findElements(By.xpath("//*[contains(text(), 'Page not found')]")).size() > 0) {
        			System.out.println("Link " + linkNum + " has an error");
        		}
        		else {
        			System.out.println("Link "+ linkNum + " is working - Page url is: " + driver.getCurrentUrl());    
        		}
        	}
        	else {
        		System.out.println("Link " + linkNum + " xpath element (" + value + ") not found"); 
        	}
        	linkNum++;
        }
		return;
	}
}
