import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UnnaxPage {
	
	WebDriver driver = new ChromeDriver();
	WebDriverWait wait = new WebDriverWait(driver,10);
	
	By LendCompButton = By.xpath("//a[@href='/en/lending-companies']/button");
	By SharedEconSectorButton = By.xpath("//a[@href='/en/shared-economy-sector']/button");
	By BankingSectorButton = By.xpath("//a[@href='/en/banking-sector']/button");
	By CrowdfundingSectorButton = By.xpath("//a[@href='/en/crowdfunding-sector']/button");
	By eCommerceSectorButton = By.xpath("//a[@href='/en/e-commerce-sector']/button");
	By InsurtechSectorButton = By.xpath("//a[@href='/en/insurtech-sector']/button");
	By P2PSectorButton = By.xpath("//a[@href='/en/p2p-economy']/button");
	By RetailSectorButton = By.xpath("//a[@href='/en/retail-sector']/button");
	By CarrousselPag0Button = By.xpath("//button[@data-page='0']");
	By CarrousselPag1Button = By.xpath("//button[@data-page='1']");
	
	String mainpage = "https://www.unnax.com/";

	
	public UnnaxPage() {		
	   	driver.manage().window().maximize();
	   	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	   	driver.get(mainpage);	
	}
		
	public boolean testAllLinks() {
		
		return (
				testLink(LendCompButton,0) &&
				testLink(SharedEconSectorButton,0) &&
				testLink(BankingSectorButton,0) &&
				testLink(CrowdfundingSectorButton,0) &&
				testLink(eCommerceSectorButton,1) &&
				testLink(InsurtechSectorButton,1) &&
				testLink(P2PSectorButton,1) &&
				testLink(RetailSectorButton,1)
				);	
	}
		
	public boolean testLink(By by, int page) {
		changeCarrousel(page);
		clickElement(by);
			
		if (driver.findElements(By.xpath("//*[contains(text(), 'Page not found')]")).size() > 0) {
    		System.out.println("Link has an error");
    		driver.get(mainpage);
    		return false;
    	}
    	else {
    		System.out.println("Link is working - Page url is: " + driver.getCurrentUrl());
    		driver.get(mainpage);
    		return true;
    	}
	}
		
	public UnnaxPage clickElement(By by) {
		driver.findElement(by).sendKeys(Keys.RETURN);
		return this;
	}
		
	public UnnaxPage changeCarrousel(int page) {
		if (page==0) clickElement(CarrousselPag0Button);
		if (page==1) clickElement(CarrousselPag1Button);
		return this;
	}
		
	public void close() {
		driver.close();
	}
}

