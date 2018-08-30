import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UnnaxWidget {
	
	WebDriver driver = new ChromeDriver();
	WebDriverWait wait = new WebDriverWait(driver,20);
	
	By generateButton = By.cssSelector("button.btn.btn-secondary");
	By environmentDropdown = By.id("env");
	By apiCredentialsDropdown = By.id("api_credentials");
	By startButton = By.cssSelector("input.btn.btn-default");
	By closeButton = By.xpath("//div[@id='exampleModal']/div/div/div[3]/button");
	By callbackOk = By.id("callback_ok");
	By languageDropdown = By.name("lang");
	By singleBank = By.id("single_bank");
	By CustomTermsAndCond = By.id("custom_terms_and_conditions_url");
	By showLogo = By.id("has_logo");
	By urlLogo = By.id("url_logo");
	By readerFrame = By.id("reader_frame");
	By unnaxSelector = By.className("unnax-selector");
	By linkTermsAndCond = By.linkText("terms and conditions");
	By formUsername = By.xpath("//form[@id='unnax-bank-login-form']/div/input");
	By formPassword = By.xpath("//form[@id='unnax-bank-login-form']/div[2]/input");
	By formDocument = By.xpath("//form[@id='unnax-bank-login-form']/div[3]/input");
	By formAccessButton = By.className("unnax-form-action");
	By checkmark = By.className("checkmark");
	By finishButton = By.className("unnax-btn");
	By addAnotherButton = By.xpath("//button[@class='unnax-btn cancel']");
	By panelTitle = By.xpath("//div[@class='panel-title']/span");
	By logoImage = By.className("m-t-xs");
	By errorMessage = By.cssSelector("code");
	
	public UnnaxWidget() {		
	    driver.manage().window().maximize();
	    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	    driver.get("http://test.unnax.com/");	
	}
		
	public UnnaxWidget generateCredentials() {
		wait.until(ExpectedConditions.invisibilityOfElementLocated(closeButton));
	    Select env = new Select(driver.findElement(environmentDropdown));
	    Select api = new Select(driver.findElement(apiCredentialsDropdown));
	    env.selectByVisibleText("demo");  	
	    api.selectByVisibleText("DEMO demo");
	    driver.findElement(generateButton).click();
	    return this;

	}
		
	public UnnaxWidget pressStart() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(startButton));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(closeButton));
		driver.findElement(startButton).click();
		return this;
	}
		
	public UnnaxWidget pressClose() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(closeButton));
		driver.findElement(closeButton).click();
		return this;
	}
		
	public boolean checkWidgetReady() {

		switchToFrame();
		if(
			driver.findElement(unnaxSelector).isDisplayed() &&
			driver.findElement(linkTermsAndCond).isDisplayed()
		) return true;
		else return false;			
	}
		
	public boolean isPresent (By by) {
		Boolean isPresent = driver.findElements(by).size() > 0;
		return isPresent;
	}
		
	public void close() {
		driver.close();
	}
		
	public UnnaxWidget startWidget() {
		generateCredentials();
		pressStart();	//For some reason the first start return a 'session is not valid' error
		pressClose();	//Closing the popup and opening it again solves it
		pressStart();   //A more elegant solution should be implemented...
		return this;
	}
		
	public void switchToFrame() {
		wait.until(ExpectedConditions.presenceOfElementLocated(readerFrame));
		driver.switchTo().frame(driver.findElement(readerFrame));
	}
		
	public boolean checkDropdownFirstValueIs(String value) {
		switchToFrame();
		driver.findElement(unnaxSelector).click();
		if (isPresent(By.xpath("//ul[@class='chosen-results']/li[contains(text(), '" + value + "')]"))) return true;
		else return false;
	}
		
	public UnnaxWidget selectBank(String bankName) {
		switchToFrame();
		driver.findElement(unnaxSelector).click();
		driver.findElement(By.xpath("//ul[@class='chosen-results']/li[contains(text(), '" + bankName + "')]")).click();			
		driver.switchTo().parentFrame();
		return this;
	}
		
	public UnnaxWidget doLogin(String username, String password, String document) {
		switchToFrame();
		driver.findElement(formUsername).sendKeys(username);
		driver.findElement(formPassword).sendKeys(password);	
		if (isPresent(formDocument)) driver.findElement(formDocument).sendKeys(document);
		driver.findElement(formAccessButton).click();
		driver.switchTo().parentFrame();
		return this;
		}
		
	public UnnaxWidget doLogin(String username, String password) {
		return doLogin(username, password, "");
	}
		
	public boolean checkAutoClose(int seconds) {
		switchToFrame();
		wait.until(ExpectedConditions.visibilityOfElementLocated(finishButton));
		long start = System.currentTimeMillis();
		WebDriverWait wait = (new WebDriverWait(driver, seconds));
		wait.until(ExpectedConditions.visibilityOfElementLocated(checkmark));
		long finish = System.currentTimeMillis();
		if((int)((finish - start)/1000) == seconds) return true;
		else return false;
	}
		
	public UnnaxWidget changeCallbackOK(String url) {
		driver.findElement(callbackOk).sendKeys(url);
		return this;
	}
		
	public UnnaxWidget finishProcess() {
		switchToFrame();
		wait.until(ExpectedConditions.visibilityOfElementLocated(finishButton)).click();
		driver.switchTo().parentFrame();
		return this;
	}
		
	public boolean checkCallbackOK(String url) {			
		wait.until(ExpectedConditions.urlContains(url));
		return true;
	}
		
	public UnnaxWidget changeLanguage(String lang) {
		Select language = new Select(driver.findElement(languageDropdown));
		language.selectByVisibleText(lang); 	    	
		return this;
	}
		
	public boolean checkLanguage(String lang) {
		switchToFrame();
		String text = driver.findElement(panelTitle).getText();
		String correctText;
			
		switch (lang) {
			case "ES": correctText = "SELECCIONE SU BANCO"; break;
			case "PT": correctText = "SELECIONE O SEU BANCO"; break;
			case "PL": correctText = "WYBIERZ SWÓJ BANK"; break;
			default: correctText = "SELECT YOUR BANK";
		}
			
		if (text.equals(correctText)) return true;
		else return false;
	}
		
	public UnnaxWidget changeSingleBank(boolean bool) {
		driver.findElement(singleBank).clear();
		if (bool == true) driver.findElement(singleBank).sendKeys("true");
		if (bool == false) driver.findElement(singleBank).sendKeys("false");
		return this;
	}
		
	public boolean checkSingleBank(boolean bool) {
		switchToFrame();
		return isPresent(addAnotherButton) != bool;
	}
		
	public UnnaxWidget changeCustomTermsAndCond(String url) {
		driver.findElement(CustomTermsAndCond).sendKeys(url);
		return this;
	}
		
	public boolean checkTermsAndCondButton(String url) {
		switchToFrame();
		driver.findElement(linkTermsAndCond).click();
		List<String> browserTabs = new ArrayList<String> (driver.getWindowHandles());
		driver.switchTo().window(browserTabs.get(1));
		wait.until(ExpectedConditions.urlContains(url));
		driver.close();
		driver.switchTo().window(browserTabs.get(0));
		return true;
	}
		
	public boolean checkTermsAndCondButton() {
		return checkTermsAndCondButton("https://www.unnax.com/terms");
	}
	
	public UnnaxWidget changeShowLogo(boolean bool) {
		driver.findElement(showLogo).clear();
		if (bool == true) driver.findElement(showLogo).sendKeys("true");
		if (bool == false) driver.findElement(showLogo).sendKeys("false");
		return this;
	}
		
	public boolean checkLogoAppearance(boolean bool) {
		switchToFrame();
		return (isPresent(logoImage) == bool);
	}
		
	public UnnaxWidget changeUrlLogo(String url) {
		driver.findElement(urlLogo).clear();
		driver.findElement(urlLogo).sendKeys(url);
		return this;
	}
		
	public boolean checkLogoUrl(String url) {
		switchToFrame();
		if (isPresent(logoImage)) {
			WebElement logo = driver.findElement(logoImage);
			return logo.getAttribute("src").equals(url);
		}
		else return true;
	}
		
	public boolean checkRequestError(String error_msg) {
		switchToFrame();
		WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
		return error.getText().contains(error_msg);
	}
}