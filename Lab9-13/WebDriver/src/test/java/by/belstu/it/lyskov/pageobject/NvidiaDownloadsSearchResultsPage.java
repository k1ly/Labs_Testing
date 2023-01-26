package by.belstu.it.lyskov.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class NvidiaDownloadsSearchResultsPage extends AbstractPage {

    private final By resultsTabLocator = By.id("tab2");
    private final By searchResultsLocator = By.id("half-spaced");

    public NvidiaDownloadsSearchResultsPage(WebDriver webDriver) {
        super(webDriver);
    }

    public NvidiaDownloadsSearchResultsPage openResultsTab() {
        new WebDriverWait(webDriver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(resultsTabLocator)).click();
        return this;
    }

    public List<String> getSupportedProducts() {
        new FluentWait<>(webDriver)
                .withTimeout(Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .pollingEvery(Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(searchResultsLocator)));
        return webDriver.findElements(searchResultsLocator).stream().map(WebElement::getText).collect(Collectors.toList());
    }
}
