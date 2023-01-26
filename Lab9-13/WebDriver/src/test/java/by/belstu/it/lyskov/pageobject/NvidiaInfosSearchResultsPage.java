package by.belstu.it.lyskov.pageobject;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NvidiaInfosSearchResultsPage extends AbstractPage {

    private final String filterLabelLocatorPart = "//label[@class='facet-label' and text()='";
    private final By checkedFilterLocator = By.cssSelector(".ant-checkbox.ant-checkbox-checked");
    private final By sortByDateLocator = By.className("sort-date");
    private final By sortByRelevanceLocator = By.className("sort-relevance");
    private final By suggestButtonLocator = By.xpath("//*[@class='spelling-dontsuggest']/button");
    private final By emptyResultLocator = By.className("search-no-results-headline");
    private final By searchResultLinksLocator = By.cssSelector(".search-results-item .item-link");
    private final By searchResultDatesLocator = By.cssSelector(".search-results-item .item-date");
    private final By searchResultTitlesLocator = By.cssSelector(".search-results-item .item-title .shiitake-children");

    public NvidiaInfosSearchResultsPage(WebDriver webDriver) {
        super(webDriver);
    }

    public NvidiaInfosSearchResultsPage filterBy(String filter) {
        new WebDriverWait(webDriver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(filterLabelLocatorPart + filter + "']"))).click();
        new WebDriverWait(webDriver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.presenceOfElementLocated(checkedFilterLocator));
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(WAIT_TIMEOUT_SECONDS));
        return this;
    }

    public NvidiaInfosSearchResultsPage sortByDate() {
        new WebDriverWait(webDriver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(sortByDateLocator)).click();
        new WebDriverWait(webDriver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.not(ExpectedConditions.attributeContains(sortByRelevanceLocator, "class", "active")));
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(WAIT_TIMEOUT_SECONDS));
        return this;
    }

    public WebElement getEmptyResult() {
        WebElement suggestButton;
        try {
            suggestButton = new FluentWait<>(webDriver)
                    .withTimeout(Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                    .pollingEvery(Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                    .ignoring(NoSuchElementException.class)
                    .until(ExpectedConditions.elementToBeClickable(suggestButtonLocator));
        } catch (TimeoutException e) {
            suggestButton = null;
        }
        if (suggestButton != null) {
            suggestButton.click();
            new WebDriverWait(webDriver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                    .until(ExpectedConditions.stalenessOf(suggestButton));
        }
        WebElement emptyResult;
        try {
            emptyResult = new FluentWait<>(webDriver)
                    .withTimeout(Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                    .pollingEvery(Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                    .ignoring(NoSuchElementException.class)
                    .until(ExpectedConditions.presenceOfElementLocated(emptyResultLocator));
        } catch (TimeoutException e) {
            emptyResult = null;
        }
        return emptyResult;
    }

    public List<LocalDate> getInfoDates() {
        if (getEmptyResult() != null)
            return new ArrayList<>();
        new FluentWait<>(webDriver)
                .withTimeout(Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .pollingEvery(Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.visibilityOfElementLocated(searchResultDatesLocator));
        return webDriver.findElements(searchResultDatesLocator).stream().map(e ->
                parseDate(e.getText())).collect(Collectors.toList());
    }

    public List<String> getInfoTitles() {
        if (getEmptyResult() != null)
            return new ArrayList<>();
        new FluentWait<>(webDriver)
                .withTimeout(Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .pollingEvery(Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.visibilityOfElementLocated(searchResultTitlesLocator));
        return webDriver.findElements(searchResultTitlesLocator).stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public List<String> getInfoLinks() {
        if (getEmptyResult() != null)
            return new ArrayList<>();
        new FluentWait<>(webDriver)
                .withTimeout(Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .pollingEvery(Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.visibilityOfElementLocated(searchResultLinksLocator));
        return webDriver.findElements(searchResultLinksLocator).stream().map(WebElement::getText).collect(Collectors.toList());
    }

    private LocalDate parseDate(String date) {
        int year = Integer.parseInt(date.substring(date.indexOf(' ') + 1));
        int month = 0;
        switch (date.substring(0, date.indexOf(' '))) {
            case "January":
                month = 1;
                break;
            case "February":
                month = 2;
                break;
            case "March":
                month = 3;
                break;
            case "April":
                month = 4;
                break;
            case "May":
                month = 5;
                break;
            case "June":
                month = 6;
                break;
            case "July":
                month = 7;
                break;
            case "August":
                month = 8;
                break;
            case "September":
                month = 9;
                break;
            case "October":
                month = 10;
                break;
            case "November":
                month = 11;
                break;
            case "December":
                month = 12;
                break;
        }
        return LocalDate.of(year, month, 1);
    }
}
