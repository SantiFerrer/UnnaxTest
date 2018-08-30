import static org.junit.Assert.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class UnnaxTests {

	
	@BeforeAll
	public static void setUp() {
		//Change or remove to use your own chromedriver
		System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\Drivers\\chromedriver.exe");
	}
	
	@Test
	void test_links() {
        //Check that all 8 links from the "Sectors we work in" section are not showing a "Page not found" message
		
    	UnnaxPage page = new UnnaxPage();
    	
    	if(page.testAllLinks()) page.close();
    	
    	else {
    		page.close();
    		fail("Error found testing urls");
    	}
	}
	
	@Test
	void test_case_01() {
        //Check that the widget correctly loads after selecting the environment, credentials, and generating the request code
		
    	UnnaxWidget widget = new UnnaxWidget();
    	
    	if(widget
    			.startWidget()
    			.checkWidgetReady()
    	) widget.close();
    	
    	else {
    		widget.close();
    		fail("Case 1 failed, widget not starting properly");
    	}
	}
	
	@Test
	void test_case_02() {
        //Check that the specified element (Customers) appears as the first element on the bank dropdown menu 
		
		UnnaxWidget widget = new UnnaxWidget();
    	String value = "Customers";
    	
    	if(widget
    			.startWidget()
    			.checkDropdownFirstValueIs(value)
    	) widget.close();
    		
    	else {
    		widget.close();
    		fail("Case 2 failed, " + value + "not showing properly");
    	}
	}
	
    @Test
    void test_case_03() {
    	//Check that the process stops after the specified amount of time (10 seconds) if no buttons are pressed
    	
    	UnnaxWidget widget = new UnnaxWidget();
    	int seconds = 10;
    	
    	if(widget
    		.startWidget()
    		.selectBank("Banco Pueyo")
    		.doLogin("user","pass")
    		.checkAutoClose(seconds)
    	) widget.close();
    	
    	else {
    		widget.close();
    		fail("Case 3 failed, autoclose not working properly");
    	}
	}
    
    @Test
    void test_case_04() {
    	//Check that adding a url on CallbackOK opens that url when the process finishes
    	
    	UnnaxWidget widget = new UnnaxWidget();
    	String callback_url = "https://www.google.es";
    	
    	if(widget
    		.changeCallbackOK(callback_url)
			.startWidget()
			.selectBank("Caja Rural de Extremadura")
			.doLogin("user","pass","document")
			.finishProcess()
    		.checkCallbackOK(callback_url)
    	) widget.close();
    	
    	else {
    		widget.close();
    		fail("Case 4 failed, CallbackOK field not working properly");
    	}
    }
    
    @Test
    void test_case_05() {
    	//Check that changing the language changes the widget language
    	
    	UnnaxWidget widget = new UnnaxWidget();
    	String lang = "PT";
    	
    	if(widget
    		.changeLanguage(lang)
			.startWidget()
			.checkLanguage(lang)
    	) widget.close();
    	
    	else {
    		widget.close();
    		fail("Case 5 failed, Language field not working properly");
    	}
    }
    
    @Test
    void test_case_06() {
    	//Check that specifying Single Bank as true doesn't show the 'Add Another Bank' button (it shows otherwise)
    	
    	UnnaxWidget widget = new UnnaxWidget();
    	boolean singleBank = true;
    	
    	if(widget
    		.changeSingleBank(singleBank)
    		.startWidget()
    		.selectBank("Triodos")
			.doLogin("user","pass")
			.checkSingleBank(singleBank)
    	) widget.close();
    	
    	else {
    	    widget.close();
    	    fail("Case 6 failed, Single Bank field not working properly");
    	}
    }
    
    @Test
    void test_case_07() {
    	//Check that specifying Custom T&C URL opens that url on a new tab when clicking 'terms and conditions' button
    	
    	UnnaxWidget widget = new UnnaxWidget();
    	String custom_url = "https://www.amazon.es";
    	
    	if(widget
    		.changeCustomTermsAndCond(custom_url)
    		.startWidget()
    		.checkTermsAndCondButton(custom_url)
    	) widget.close();
    	
    	else {
    	    widget.close();
    	    fail("Case 7 failed, Custom T&C URL not working properly");
    	} 	
    }
    
    @Test
    void test_case_08() {
    	//Check that the logo can be shown or hidden using the Show Logo field
    	
    	UnnaxWidget widget = new UnnaxWidget();
    	boolean showLogo = false;
    	
    	if(widget
        		.changeShowLogo(showLogo)
        		.startWidget()
        		.checkLogoAppearance(showLogo)
        	) widget.close();
        
    	else {
    		widget.close();
        	fail("Case 8 failed, Show Logo not working properly");
        } 	
    }
    
    @Test
    void test_case_09() {
    	//Check that the logo is the one specified on the url_logo field
    	
    	UnnaxWidget widget = new UnnaxWidget();
    	String urlLogo = "https://goodlogo.com/images/logos/batman_logo_2574.gif";
    	
    	if(widget
        		.changeUrlLogo(urlLogo)
        		.changeShowLogo(true)
        		.startWidget()
        		.checkLogoUrl(urlLogo)
        	) widget.close();
       
    	else {
        	widget.close();
        	fail("Case 9 failed, URL Logo not working properly");
        } 	
    }
    
    @Test
    void test_case_10() {
    	//Check that an error message is shown if a new Request Code has not been generated
    	
    	UnnaxWidget widget = new UnnaxWidget();
    	String error_msg = "Order already exists";
    	
    	if(widget
        		.startWidget()
        		.selectBank("Caixa Geral")
        		.doLogin("user","pass")
        		.finishProcess()
        		.pressClose()
        		.pressStart()
        		.checkRequestError(error_msg)
        	) widget.close();
        
    	else {
        	widget.close();
        	fail("Case 10 failed, '" + error_msg + "' error not showing properly");
        } 	
    }
}
