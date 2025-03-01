package org.example.forcegetregister.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
} 