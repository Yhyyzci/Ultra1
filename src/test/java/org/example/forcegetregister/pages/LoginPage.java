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

    @FindBy(xpath = "//div[contains(@class, 'ant-layout-content')]//div[text()='Dashboard']")
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
            // Email
            wait.until(ExpectedConditions.elementToBeClickable(emailInput)).clear();
            emailInput.sendKeys(email);
            
            // Password
            wait.until(ExpectedConditions.elementToBeClickable(passwordInput)).clear();
            passwordInput.sendKeys(password);

            // Login button
            System.out.println("Clicking login button...");
            WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@class='ant-btn ant-btn-primary']")));
            loginBtn.click();
            
            System.out.println("Login form submitted successfully");
            Thread.sleep(3000); // Login sonrası bekleme
        } catch (Exception e) {
            System.err.println("Error during login: " + e.getMessage());
            throw new RuntimeException("Login failed");
        }
    }

    public boolean isDashboardLoaded() {
        try {
            // URL kontrolü
            if (!driver.getCurrentUrl().contains("/dashboard")) {
                System.err.println("Not on dashboard page. Current URL: " + driver.getCurrentUrl());
                return false;
            }

            // Sayfa yüklenmesi için biraz bekle
            Thread.sleep(2000);

            // Dashboard başlığını farklı yöntemlerle bulmayı dene
            try {
                // Yöntem 1: XPath ile
                WebElement dashboard1 = driver.findElement(By.xpath("//div[text()='Dashboard']"));
                if (dashboard1.isDisplayed()) {
                    System.out.println("Dashboard found with method 1");
                    return true;
                }
            } catch (Exception e) {
                System.out.println("Method 1 failed: " + e.getMessage());
            }

            try {
                // Yöntem 2: Contains ile
                WebElement dashboard2 = driver.findElement(
                    By.xpath("//div[contains(text(), 'Dashboard')]"));
                if (dashboard2.isDisplayed()) {
                    System.out.println("Dashboard found with method 2");
                    return true;
                }
            } catch (Exception e) {
                System.out.println("Method 2 failed: " + e.getMessage());
            }

            try {
                // Yöntem 3: CSS Selector ile
                WebElement dashboard3 = driver.findElement(
                    By.cssSelector("div.ant-layout-content div:contains('Dashboard')"));
                if (dashboard3.isDisplayed()) {
                    System.out.println("Dashboard found with method 3");
                    return true;
                }
            } catch (Exception e) {
                System.out.println("Method 3 failed: " + e.getMessage());
            }

            // Hiçbir yöntem başarılı olmadı
            System.err.println("Dashboard element not found with any method");
            return false;

        } catch (Exception e) {
            System.err.println("Dashboard verification failed: " + e.getMessage());
            System.err.println("Current URL: " + driver.getCurrentUrl());
            return false;
        }
    }
} 