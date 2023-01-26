package by.belstu.it.lyskov.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public class NvidiaDriversSearchResultsPage extends AbstractPage {

    private final By searchResultsLocator = By.cssSelector("div#betaSearchResults div.driverBox");
    private final By emptyResultLocator = By.cssSelector("div#manualSearchNoResultsFound");

    public NvidiaDriversSearchResultsPage(WebDriver webDriver) {
        super(webDriver);
    }

    public int countResultNumber() {
        new FluentWait<>(webDriver)
                .withTimeout(Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .pollingEvery(Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.or(ExpectedConditions.presenceOfElementLocated(searchResultsLocator),
                        ExpectedConditions.visibilityOfElementLocated(emptyResultLocator)));
        return webDriver.findElements(searchResultsLocator).size();
    }
}
