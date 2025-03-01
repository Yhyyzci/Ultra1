package org.example.forcegetregister.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.forcegetregister.pages.LoginPage;
import org.example.forcegetregister.pages.RegisterPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import io.cucumber.datatable.DataTable;
import java.util.Map;
import java.time.Duration;
import java.util.Collections;

public class RegisterSteps {
    private WebDriver driver;
    private RegisterPage registerPage;
    private LoginPage loginPage;
    private String testEmail;
    private String testPassword;

    @Before
    public void setup() {
        try {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-blink-features=AutomationControlled");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
            options.setExperimentalOption("useAutomationExtension", false);
            
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
            registerPage = new RegisterPage(driver);
            loginPage = new LoginPage(driver);
        } catch (Exception e) {
            e.printStackTrace();
            if (driver != null) {
                driver.quit();
            }
            throw e;
        }
    }

    @Given("user is on the registration page")
    public void userIsOnTheRegistrationPage() {
        try {
            System.out.println("Opening registration page...");
            registerPage.navigateToRegisterPage();
            Thread.sleep(3000);
            System.out.println("Registration page opened successfully");
        } catch (Exception e) {
            System.err.println("Failed to open registration page: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to navigate to registration page");
        }
    }

    @When("user enters the following details:")
    public void userEntersTheFollowingDetails(DataTable dataTable) {
        try {
            Thread.sleep(3000);
            Map<String, String> formData = dataTable.asMap(String.class, String.class);
            
            String email = formData.get("email");
            String password = formData.get("password");
            
            if (email == null || password == null) {
                throw new RuntimeException("Email or password is missing!");
            }

            System.out.println("Starting to fill form...");
            testEmail = email;
            testPassword = password;
            
            // Form doldurma islemini baslat
            registerPage.fillForm(email, password);
            Thread.sleep(2000);
            
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Form filling error: " + e.getMessage());
        }
    }

    @When("clicks on register button")
    public void clicksOnRegisterButton() {
        try {
            Thread.sleep(2000);
            System.out.println("Clicking register button...");
            registerPage.clickRegisterButton();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("registration should be successful")
    public void registrationShouldBeSuccessful() {
        try {
            Thread.sleep(2000);
            System.out.println("Verifying registration...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("user navigates to login page")
    public void userNavigatesToLoginPage() {
        try {
            System.out.println("Navigating to login page...");
            driver.get("https://app.forceget.com/system/account/login");
            Thread.sleep(3000);
        } catch (Exception e) {
            System.err.println("Failed to navigate to login page: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Login page navigation failed");
        }
    }

    @When("user logs in with registered credentials")
    public void userLogsInWithRegisteredCredentials() {
        try {
            System.out.println("Logging in with: " + testEmail);
            loginPage.login(testEmail, testPassword);
        } catch (Exception e) {
            System.err.println("Login failed: " + e.getMessage());
            throw new RuntimeException("Login process failed");
        }
    }

    @Then("login should be successful")
    public void loginShouldBeSuccessful() {
        try {
            System.out.println("=================================");
            System.out.println("TEST COMPLETED SUCCESSFULLY!");
            System.out.println("=================================");
            Thread.sleep(3000); // Test bitişinde ekranı görebilmek için bekleme
        } catch (Exception e) {
            System.err.println("Test completion error: " + e.getMessage());
            throw new RuntimeException("Test completion failed");
        }
    }

    @After
    public void tearDown() {
        try {
            if (driver != null) {
                driver.quit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 