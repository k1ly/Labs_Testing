package by.belstu.it.lyskov.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled("Simple webdriver test disabled")
class WebDriverTest {

    private WebDriver webDriver;

    @BeforeEach
    void initBrowser() {
        webDriver = new ChromeDriver();
    }

    @Test
    void commonDriverParamsShouldReturnResults() {
        webDriver.get("https://www.nvidia.com/ru-ru/geforce/drivers/");

        new WebDriverWait(webDriver, Duration.ofSeconds(3))
                .until(ExpectedConditions.elementToBeClickable(By.id("languagebutton-ru")));
        webDriver.findElement(By.id("languagebutton-ru")).click();

        new WebDriverWait(webDriver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.id("manualSearch-0-button")));
        webDriver.findElement(By.id("manualSearch-0-button")).click();
        new WebDriverWait(webDriver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("ul#manualSearch-0-menu li")));
        webDriver.findElement(By.xpath("//ul[@id='manualSearch-0-menu']/li[text()='GeForce']")).click();

        new WebDriverWait(webDriver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.id("manualSearch-1-button")));
        webDriver.findElement(By.id("manualSearch-1-button")).click();
        new WebDriverWait(webDriver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("ul#manualSearch-1-menu li")));
        webDriver.findElement(By.xpath("//ul[@id='manualSearch-1-menu']/li[text()='GeForce RTX 30 Series']")).click();

        new WebDriverWait(webDriver, Duration.ofSeconds(5))
                .until(ExpectedConditions.and(
                        ExpectedConditions.presenceOfElementLocated(
                                By.xpath("//span[@id='manualSearch-2-button']/span[@class='ui-selectmenu-text' and text()='GeForce RTX 3090 Ti']")),
                        ExpectedConditions.elementToBeClickable(By.id("manualSearch-2-button"))));
        webDriver.findElement(By.id("manualSearch-2-button")).click();
        new WebDriverWait(webDriver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("ul#manualSearch-2-menu li")));
        webDriver.findElement(By.xpath("//ul[@id='manualSearch-2-menu']/li[text()='GeForce RTX 3090']")).click();

        new WebDriverWait(webDriver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.id("manualSearch-4-button")));
        webDriver.findElement(By.id("manualSearch-4-button")).click();
        new WebDriverWait(webDriver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("ul#manualSearch-4-menu li")));
        webDriver.findElement(By.xpath("//ul[@id='manualSearch-4-menu']/li[text()='Windows 10 64-bit']")).click();

        ((JavascriptExecutor) webDriver).executeScript("javascript:window.scrollBy(0,200)",
                webDriver.findElement(By.id("manualSearch-5-button")));
        new WebDriverWait(webDriver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.id("manualSearch-5-button")));
        webDriver.findElement(By.id("manualSearch-5-button")).click();
        new WebDriverWait(webDriver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("ul#manualSearch-5-menu li")));
        webDriver.findElement(By.xpath("//ul[@id='manualSearch-5-menu']/li[text()='Japanese']")).click();

        ((JavascriptExecutor) webDriver).executeScript("javascript:window.scrollBy(0,200)",
                webDriver.findElement(By.id("driverType-button")));
        new WebDriverWait(webDriver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.id("driverType-button")));
        webDriver.findElement(By.id("driverType-button")).click();
        new WebDriverWait(webDriver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("ul#driverType-menu li")));
        webDriver.findElement(By.xpath("//ul[@id='driverType-menu']/li[text()='Драйвер Game Ready ']")).click();

        ((JavascriptExecutor) webDriver).executeScript("javascript:window.scrollBy(0,200)",
                webDriver.findElement(By.id("manualSearchButton")));
        new WebDriverWait(webDriver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("manualSearchButton")));
        webDriver.findElement(By.id("manualSearchButton")).click();


        new FluentWait<>(webDriver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div#betaSearchResults div.driverBox")));

        List<WebElement> results = webDriver.findElements(By.cssSelector("div#betaSearchResults div.driverBox div.detailTitle span"));
        assertTrue(results.size() > 0, "Search results are empty");
    }

    @AfterEach
    void closeBrowser() {
        webDriver.quit();
        webDriver = null;
    }
}
