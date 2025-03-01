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

public class RegisterPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//input[@id='firstName']")
    private WebElement firstNameInput;

    @FindBy(xpath = "//input[@id='lastName']")
    private WebElement lastNameInput;

    @FindBy(xpath = "(//*[name()='svg'])[1]")
    private WebElement countryDropdown;

    @FindBy(xpath = "//input[contains(@class, 'ant-select-selection-search-input')]")
    private WebElement countrySearchInput;

    @FindBy(xpath = "//div[@class='flex items-center gap-2 text-12px w-350px ng-star-inserted']")
    private WebElement turkeyOption;

    @FindBy(xpath = "//input[@id='phoneNumber']")
    private WebElement mobileNumberInput;

    @FindBy(xpath = "//input[@id='companyName']")
    private WebElement companyNameInput;

    @FindBy(xpath = "//input[@placeholder='E-mail']")
    private WebElement emailInput;

    @FindBy(xpath = "(//*[name()='svg'])[2]")
    private WebElement titleDropdown;

    @FindBy(xpath = "//div[@class='ant-select-item-option-content'][1]")
    private WebElement firstTitleOption;

    @FindBy(xpath = "//nz-option-item[@title='Logistic Manager']//div[1]")
    private WebElement logisticManagerOption;

    @FindBy(xpath = "//input[@placeholder='Password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//input[@placeholder='Confirm password']")
    private WebElement confirmPasswordInput;

    @FindBy(xpath = "//span[@class='checkbox-box']")
    private WebElement checkboxTerms;

    @FindBy(xpath = "//span[normalize-space()='Accept']")
    private WebElement acceptButton;

    @FindBy(xpath = "(//span[@class='cb-i'])[3]")
    private WebElement verifyHumanCheckbox;

    @FindBy(xpath = "//button[@class='ant-btn w-full ant-btn-primary']")
    private WebElement registerButton;

  

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        PageFactory.initElements(driver, this);
    }

    public void navigateToRegisterPage() {
        try {
            driver.get("https://app.forceget.com/system/account/register");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void fillForm(String email, String password) {
        try {
            // First Name
            waitAndFill(firstNameInput, "Ultra");
            Thread.sleep(1000);
            
            // Last Name
            waitAndFill(lastNameInput, "Yzci");
            Thread.sleep(1000);
            
            // Country selection
            System.out.println("Selecting country...");
            wait.until(ExpectedConditions.elementToBeClickable(countryDropdown)).click();
            Thread.sleep(2000);
            
            // Search and select Turkey
            try {
                // Arama kutusuna Turkey yaz
                WebElement searchInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//input[contains(@class, 'ant-select-selection-search-input')]")));
                searchInput.sendKeys("Turkey");
                Thread.sleep(2000);
                
                // Turkey seçeneğini bul ve tıkla
                WebElement turkey = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@class='flex items-center gap-2 text-12px w-350px ng-star-inserted']")));
                
                // JavaScript ile tıklama işlemi
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", turkey);
                Thread.sleep(1000);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", turkey);
                
                Thread.sleep(2000);
                System.out.println("Country selected successfully");

            } catch (Exception e) {
                System.err.println("Error selecting country: " + e.getMessage());
                throw e;
            }
            
            // Phone number
            System.out.println("Entering phone number...");
            try {
                wait.until(ExpectedConditions.elementToBeClickable(mobileNumberInput));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", mobileNumberInput);
                Thread.sleep(1000);
                mobileNumberInput.clear();
                mobileNumberInput.sendKeys("5451311802");
                Thread.sleep(2000);
                System.out.println("Phone number entered successfully");
            } catch (Exception e) {
                System.err.println("Error entering phone number: " + e.getMessage());
                throw e;
            }
            
            // Company
            waitAndFill(companyNameInput, "Phyix Growth Hub");
            
            // Email
            waitAndFill(emailInput, email);
            
            // Title selection
            System.out.println("Selecting title...");
            try {
                // Title dropdown'a tıkla
                wait.until(ExpectedConditions.elementToBeClickable(titleDropdown)).click();
                Thread.sleep(2000);
                
                // İlk seçeneği bul ve tıkla
                WebElement firstOption = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@class='ant-select-item-option-content'][1]")));
                
                // JavaScript ile scroll ve tıklama
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", firstOption);
                Thread.sleep(1000);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstOption);
                
                Thread.sleep(2000);
                System.out.println("Title selected successfully");
            } catch (Exception e) {
                System.err.println("Error selecting title: " + e.getMessage());
                throw e;
            }
            
            // Password
            waitAndFill(passwordInput, password);
            waitAndFill(confirmPasswordInput, password);
            
            // Terms checkbox and Accept
            System.out.println("Handling terms and conditions...");
            try {
                // Terms checkbox'a tıkla
                wait.until(ExpectedConditions.elementToBeClickable(checkboxTerms)).click();
                Thread.sleep(2000);
                
                // Accept butonunu bekle ve tıkla
                WebElement acceptBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//span[normalize-space()='Accept']")));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", acceptBtn);
                Thread.sleep(1000);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", acceptBtn);
                Thread.sleep(2000);

                // Cloudflare doğrulaması için bekleme
                System.out.println("WARNING: Waiting for Cloudflare verification...");
                System.out.println("Please complete the verification manually within 60 seconds...");
                
                // Doğrulama tamamlanana kadar bekle
                for (int i = 0; i < 60; i++) {
                    try {
                        // Register butonunun tıklanabilir olup olmadığını kontrol et
                        if (registerButton.isEnabled() && registerButton.isDisplayed()) {
                            System.out.println("Verification completed successfully!");
                            break;
                        }
                    } catch (Exception e) {
                        // Hata durumunda devam et
                    }
                    Thread.sleep(1000);
                }

                System.out.println("Continuing with registration...");
            } catch (Exception e) {
                System.err.println("Error in verification process: " + e.getMessage());
                throw e;
            }
            
            Thread.sleep(2000);
            
        } catch (Exception e) {
            System.err.println("Form filling error: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Form filling error: " + e.getMessage());
        }
    }

    private void waitAndFill(WebElement element, String text) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            Thread.sleep(1000);
            
            element.clear();
            element.sendKeys(text);
            Thread.sleep(1000);
        } catch (Exception e) {
            System.err.println("Error filling element: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void clickRegisterButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(registerButton));
            registerButton.click();
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
} 