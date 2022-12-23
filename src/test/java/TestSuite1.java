import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestSuite1 {
    ChromeDriver driver;
    @BeforeTest
    public void OpenBrowser(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/login");
        driver.manage().window().maximize();
    }

    @DataProvider(name = "Test Data")
    public static Object [][] Data() {
        return new Object[][] {
                {"tomsmith","SuperSecretPassword!"}, //ture
                {"tomsmit","SuperSecretPassword!"}, //invalid user
                {"tomsmith","SuperSecretPassword"}, //invalid password
        };

    }

    @Test(priority = 1 , dataProvider = "Test Data")
    public void Login( String user , String pass) throws InterruptedException {
        Thread.sleep(500);
        WebElement username = driver.findElement(By.cssSelector("#username"));
        username.clear();
        username.sendKeys(user);
        WebElement password = driver.findElement(By.cssSelector("#password"));
        password.clear();
        password.sendKeys(pass);
        WebElement login_btn = driver.findElement(By.cssSelector(".fa"));
        login_btn.click();

        String expected = "You logged into a secure area!";
        String actual = driver.findElement(By.cssSelector("#flash")).getText();
        //Assert.assertEquals(actual,expected);
        Assert.assertTrue(actual.contains(expected));
    }
    @Test(priority = 2)
    public void logout() throws InterruptedException {
        Thread.sleep(500);
        WebElement logout_btn = driver.findElement(By.cssSelector(".icon-2x"));
        logout_btn.click();
    }

    @AfterTest
    public void closeBroswer() throws InterruptedException {
        Thread.sleep(500);
        driver.quit();
    }

}
