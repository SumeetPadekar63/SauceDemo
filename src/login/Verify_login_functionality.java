package login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.annotations.Test;

public class Verify_login_functionality {
	public class SwagLabsTests {
		private WebDriver driver;
		private void login(String username, String password) {
			WebElement usernameField = driver.findElement(By.id("user-name"));
			WebElement passwordField = driver.findElement(By.id("password"));
			WebElement loginButton = driver.findElement(By.id("login-button"));
			usernameField.sendKeys(username);
			passwordField.sendKeys(password);
			loginButton.click();
		}
		
		@BeforeMethod
		public void setUp() {
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\Sumeet Padekar\\git\\SauceDemo\\ChromeDriver\\chromedriver.exe"); 
			driver = new ChromeDriver();
			driver.get("https://www.saucedemo.com/");
		}

		@AfterMethod
		public void tearDown() {
			// Close the browser after each test
			if (driver != null) {
				driver.quit();
			}
		}

		@Test(description = "TC01-Login with valid username and password")
		public void testLoginWithValidCredentials() {
			String username = "standard_user";
			String password = "secret_sauce";
			login(username, password);

			// Assertion for successful login and redirection to product page
			Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"));
		}

		@Test(description = "TC02-Login with invalid username")
		public void testLoginWithInvalidUsername() {
			String invalidUsername = "invalid_user";
			String password = "secret_sauce";
			login(invalidUsername, password);

			// Assertion for error message with invalid username
			WebElement errorElement = driver.findElement(By.cssSelector(".error-message-container"));
			Assert.assertTrue(errorElement.isDisplayed());
		}

		@Test(description = "TC03-Login with invalid password")
		public void testLoginWithInvalidPassword() {
			String username = "standard_user";
			String invalidPassword = "invalid_pass";
			login(username, invalidPassword);

			// Assertion for error message with invalid password
			WebElement errorElement = driver.findElement(By.cssSelector(".error-message-container"));
			Assert.assertTrue(errorElement.isDisplayed());
		}

		@Test(description = "TC04-Login with locked account")
		public void testLoginWithLockedAccount() {
			String lockedUsername = "locked_out_user";
			String password = "secret_sauce";
			login(lockedUsername, password);

			// Assertion for error message with locked account
			WebElement errorElement = driver.findElement(By.cssSelector(".error-message-container"));
			Assert.assertTrue(errorElement.isDisplayed());
		}

		@Test(description = "Login with empty username")
		public void testLoginWithEmptyUsername() {
			String emptyUsername = "";
			String password = "secret_sauce";
			login(emptyUsername, password);

			// Assertion for error message with empty username
			WebElement errorElement = driver.findElement(By.cssSelector(".error-message-container"));
			Assert.assertTrue(errorElement.isDisplayed());
		}

		@Test(description = "Login with empty password")
		public void testLoginWithEmptyPassword() {
			String username = "standard_user";
			String emptyPassword = "";
			login(username, emptyPassword);

			// Assertion for error message with empty password
			WebElement errorElement = driver.findElement(By.cssSelector(".error-message-container"));
			Assert.assertTrue(errorElement.isDisplayed());
		}


	}

}