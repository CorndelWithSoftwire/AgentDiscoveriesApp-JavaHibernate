package org.softwire.training.api.integration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.softwire.training.api.integration.helper.LoginHelper;
import org.softwire.training.api.integration.helper.WebDriverHelper;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchReportsIT {
    public static final String TARGET_ADDRESS = System.getProperty("target.address");

    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeAll
    public static void setUp() {
        driver = WebDriverHelper.getSharedDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void testCanSearchLocationReportsByPartialTitle() {
        driver.get(TARGET_ADDRESS);
        LoginHelper.ensureLoggedIn(driver);
        driver.get(TARGET_ADDRESS + "/#/submit/location");

        WebElement locationSelect = driver.findElement(By.id("location-select"));
        new Select(locationSelect).selectByIndex(1);
        WebElement statusInput = driver.findElement(By.id("status-input"));
        statusInput.sendKeys("1");
        WebElement reportTitleInput = driver.findElement(By.id("title-input"));
        reportTitleInput.sendKeys("A test report title");
        WebElement reportInput = driver.findElement(By.id("report-input"));
        reportInput.sendKeys("A test report");
        WebElement submitButton = driver.findElement(By.id("submit-report"));
        submitButton.click();

        LoginHelper.logOut(driver);
        LoginHelper.ensureLoggedInAsAdmin(driver);

        driver.get(TARGET_ADDRESS + "/#/search/location");

        WebElement reportTitle = driver.findElement(By.id("title-search"));
        reportTitle.sendKeys("test");
        WebElement searchButton = driver.findElement(By.id("search-report"));
        searchButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("resultsBox")));
        WebElement alert = driver.findElement(By.className("results"));
        assertTrue(alert.getText().contains("results"));
    }
}