package pl.kwi.intg;

import static org.junit.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;

public abstract class AbstrIntgTestPage {
	
	protected WebDriver driver;
	protected Wait<WebDriver> wait;
	protected String text;
	protected String title;
	
	
	public AbstrIntgTestPage(WebDriver driver, Wait<WebDriver> wait) {
		this.driver = driver;
		this.wait = wait;
	}
	
	/**
	 * Method checks if current page is loaded.
	 */
	public abstract void checkIfPageLoaded();
	
	/**
	 * Method inits browser with specified url.
	 * 
	 * @param url object String with url which starts browser
	 */
	public void initBrowserByUrl(String url) {
		driver.get(url);
	}
	
	/**
	 * Method closes browser.
	 */
	public void closeBrowser() {
		driver.quit();
	}
	
	/**
	 * Method types text into field with specified id.
	 * 
	 * @param id object String with field id
	 * @param text object String with typed text
	 */
	public void typeTextInFieldById(String id, String text) {
		driver.findElement(By.id(id)).sendKeys(text);
	}
	
	/**
	 * Method presses button with specified id
	 * 
	 * @param id object String with id of button
	 */
	public void pressButtonById(String id) {
		driver.findElement(By.id(id)).click();
	}
	
	/**
	 * Method clicks link with specified id.
	 * 
	 * @param id object <code>String</code> with link specified id
	 */
	public void clickLinkById(String id) {
		driver.findElement(By.id(id)).click();
	}
	
	/**
	 * Method clicks link with specified text.
	 * 
	 * @param linkText object <code>String</code> with link specified text
	 */
	public void clickLinkByText(String linkText) {
		driver.findElement(By.linkText(linkText)).click();
	}
	
	/**
	 * Method clears text id field with specified id.
	 * 
	 * @param id object <code>String</code> with link specified id
	 */
	public void clearTextInFieldById(String id) {
		driver.findElement(By.id(id)).clear();
	}
	
	// **************************************************************** //
	// ********************* CHECK METHODS **************************** //
	// **************************************************************** //
	
	/**
	 * Method checks if text is placed into field with specified id.
	 * 
	 * @param id object <code>String<code> with field id
	 * @param expectedText object <code>String<code> with expected text
	 */
	public void checkTextInFieldById(String id, String expectedText) {
		text = driver.findElement(By.id(id)).getText();
        assertEquals(expectedText, text);
	}
	
	/**
	 * Method checks if element with specified xpath has specified body.
	 * 
	 * @param xPath object <code>String<code> with element xPath
	 * @param expectedText object <code>String<code> with expected text
	 */
	public void checkBodyInElementByXPath(String xPath, String expectedText) {
		text = driver.findElement(By.xpath(xPath)).getText();
        assertEquals(expectedText, text);
	}
	
	/**
	 * Method checks if element with specified id has specified attribute with specified value.
	 * 
	 * @param id object <code>String<code> with field id
	 * @param attribute object <code>String<code> with specified attribute
	 * @param expectedText object <code>String<code> with expected value of attribute
	 */
	public void checkAttributeInElementdById(String id, String attribute, String expectedText) {
		text = driver.findElement(By.id(id)).getAttribute(attribute);
        assertEquals(expectedText, text);
	}
	
	/**
	 * Method checks if element with specified xpath does not exist on page.
	 * 
	 * @param xPath object <code>String<code> with element xPath
	 */
	public void checkElementNotExistsByXPath(String xPath) {
		boolean result = driver.findElements(By.xpath(xPath)).size() < 1;
		assertTrue(result);
	}

}
