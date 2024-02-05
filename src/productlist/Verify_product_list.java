package productlist;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Verify_product_list {

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
		driver.get("https://www.saucedemo.com/");    }

    @AfterMethod
    public void tearDown() {
        // Close the browser after each test
        if (driver != null) {
            driver.quit();
        }
    }

    @Test(description = "TC06-Sort products by name (ascending and descending)")
    public void testSortProductsByName() {
        // Login with valid credentials (Assuming a successful login is required for this action)
        String username = "standard_user";
        String password = "secret_sauce";
        login(username, password);

        // Get the product names before sorting
        List<String> originalProductNames = getProductNames();

        // Sort products in ascending order
        sortProductsByName(true);
        List<String> ascendingSortedProductNames = getProductNames();

        // Assertion for correct order in ascending sort
        Assert.assertTrue(isSorted(ascendingSortedProductNames, true), "Products are not sorted in ascending order");

        // Sort products in descending order
        sortProductsByName(false);
        List<String> descendingSortedProductNames = getProductNames();

        // Assertion for correct order in descending sort
        Assert.assertTrue(isSorted(descendingSortedProductNames, false), "Products are not sorted in descending order");
    }

    private List<String> getProductNames() {
        List<String> productNames = new ArrayList<>();
        List<WebElement> productNameElements = driver.findElements(By.cssSelector(".inventory_item_name"));

        for (WebElement element : productNameElements) {
            productNames.add(element.getText());
        }

        return productNames;
    }

    private void sortProductsByName(boolean ascending) {
        WebElement sortDropdown = driver.findElement(By.cssSelector(".product_sort_container"));
        sortDropdown.click();

        if (ascending) {
            driver.findElement(By.cssSelector("[value='az']")).click();
        } else {
            driver.findElement(By.cssSelector("[value='za']")).click();
        }
    }

    private boolean isSorted(List<String> list, boolean ascending) {
        List<String> sortedList = new ArrayList<>(list);
        if (ascending) {
            Collections.sort(sortedList);
        } else {
            Collections.sort(sortedList, Collections.reverseOrder());
        }
        return list.equals(sortedList);
    }
    

        @Test(description = "TC07-Filter products by category")
        public void testFilterProductsByCategory() {
          
            // Select a category to filter
            String selectedCategory = "Sauce Labs Backpack"; 
            filterProductsByCategory(selectedCategory);

            // Get the product names after filtering
            List<String> filteredProductNames = getProductNames();

            // Assertion for only products within the selected category displayed
            Assert.assertTrue(filteredProductNames.stream().allMatch(name -> name.contains(selectedCategory)),
                    "Not all products are in the selected category");
        }

        private void filterProductsByCategory(String category) {
            WebElement filterDropdown = driver.findElement(By.className("product_sort_container"));
            filterDropdown.click();

            WebElement categoryOption = driver.findElement(By.xpath("//option[text()='" + category + "']"));
            categoryOption.click();
        }

        private List<String> getProductName() {
            List<String> productNames = new ArrayList<>();
            List<WebElement> productNameElements = driver.findElements(By.cssSelector(".inventory_item_name"));

            for (WebElement element : productNameElements) {
                productNames.add(element.getText());
            }

            return productNames;
        }

         
}