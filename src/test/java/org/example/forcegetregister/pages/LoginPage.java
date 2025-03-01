package org.example.forcegetregister.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.By;
import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//input[@placeholder='E-mail']")
    private WebElement emailInput;

    @FindBy(xpath = "//input[@placeholder='Password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[@class='ant-btn ant-btn-primary']")
    private WebElement loginButton;

    @FindBy(xpath = "//div[@class='dashboard-container']")
    private WebElement dashboardContainer;

    @FindBy(xpath = "//div[text()='Welcome, Ultra Yzci']")
    private WebElement welcomeMessage;

    @FindBy(xpath = "//div[text()='Customer']")
    private WebElement customerText;

    @FindBy(xpath = "//div[text()='Dashboard']")
    private WebElement dashboardTitle;

    @FindBy(xpath = "//div[text()='Transportation by Method']")
    private WebElement transportationTitle;

    @FindBy(xpath = "//div[text()='Active shipments (0)']")
    private WebElement activeShipmentsTitle;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        PageFactory.initElements(driver, this);
    }

    public void login(String email, String password) {
        try {
            System.out.println("Filling login form...");
            wait.until(ExpectedConditions.elementToBeClickable(emailInput));
            emailInput.clear();
            emailInput.sendKeys(email);
            Thread.sleep(1000);

            wait.until(ExpectedConditions.elementToBeClickable(passwordInput));
            passwordInput.clear();
            passwordInput.sendKeys(password);
            Thread.sleep(1000);

            // Login butonu için JavaScript executor kullan
            System.out.println("Clicking login button...");
            WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@class='ant-btn ant-btn-primary']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", loginBtn);
            Thread.sleep(1000);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginBtn);
            
            Thread.sleep(3000);
            System.out.println("Login button clicked successfully");
        } catch (Exception e) {
            System.err.println("Error during login: " + e.getMessage());
            throw new RuntimeException("Login failed");
        }
    }

    public boolean isDashboardLoaded() {
        try {
            // Dashboard elementlerini kontrol et
            boolean isWelcomeDisplayed = wait.until(ExpectedConditions.visibilityOf(welcomeMessage)).isDisplayed();
            boolean isCustomerDisplayed = wait.until(ExpectedConditions.visibilityOf(customerText)).isDisplayed();
            boolean isDashboardDisplayed = wait.until(ExpectedConditions.visibilityOf(dashboardTitle)).isDisplayed();
            boolean isTransportationDisplayed = wait.until(ExpectedConditions.visibilityOf(transportationTitle)).isDisplayed();
            boolean isShipmentsDisplayed = wait.until(ExpectedConditions.visibilityOf(activeShipmentsTitle)).isDisplayed();

            if (isWelcomeDisplayed && isCustomerDisplayed && isDashboardDisplayed && 
                isTransportationDisplayed && isShipmentsDisplayed) {
                System.out.println("Dashboard verification successful:");
                System.out.println("- Welcome message verified: " + welcomeMessage.getText());
                System.out.println("- Customer role verified");
                System.out.println("- Dashboard title verified");
                System.out.println("- Transportation section verified");
                System.out.println("- Active shipments section verified");
                return true;
            } else {
                System.err.println("Some dashboard elements are missing!");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error verifying dashboard: " + e.getMessage());
            return false;
        }
    }
} 