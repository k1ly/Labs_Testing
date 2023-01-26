package by.belstu.it.lyskov.pageobject;

import by.belstu.it.lyskov.model.NvidiaDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class NvidiaDriversPage extends AbstractPage {
    public static final String SEARCH_PAGE_URL = "https://www.nvidia.com/ru-ru/geforce/drivers/";

    Logger logger = LogManager.getRootLogger();

    @FindBy(id = "languagebutton-ru")
    private WebElement localePromptRuButton;

    private final By productParamLocator = By.id("manualSearch-0-button");
    private final By productListLocator = By.cssSelector("ul#manualSearch-0-menu li");
    private final String productListElementLocatorPart = "//ul[@id='manualSearch-0-menu']/li[text()='";

    private final By seriesParamLocator = By.id("manualSearch-1-button");
    private final By seriesListLocator = By.cssSelector("ul#manualSearch-1-menu li");
    private final String seriesListElementLocatorPart = "//ul[@id='manualSearch-1-menu']/li[text()='";

    private final String defaultModelParamTextLocator = "//span[@id='manualSearch-2-button']/span[@class='ui-selectmenu-text' and text()='";
    private final String defaultModelParam_GeForceRTX3090Ti = "GeForce RTX 3090 Ti";
    private final String defaultModelParam_GeForceGTX980 = "GeForce GTX 980";
    private final By modelParamLocator = By.id("manualSearch-2-button");
    private final By modelListLocator = By.cssSelector("ul#manualSearch-2-menu li");
    private final String modelListElementLocatorPart = "//ul[@id='manualSearch-2-menu']/li[text()='";

    private final By osParamLocator = By.id("manualSearch-4-button");
    private final By osListLocator = By.cssSelector("ul#manualSearch-4-menu li");
    private final String osListElementLocatorPart = "//ul[@id='manualSearch-4-menu']/li[text()='";

    private final By langParamLocator = By.id("manualSearch-5-button");
    private final By langListLocator = By.cssSelector("ul#manualSearch-5-menu li");
    private final String langListElementLocatorPart = "//ul[@id='manualSearch-5-menu']/li[text()='";

    private final By typeParamLocator = By.id("driverType-button");
    private final By typeListLocator = By.cssSelector("ul#driverType-menu li");
    private final String typeListElementLocatorPart = "//ul[@id='driverType-menu']/li[text()='";

    private final By searchButton = By.id("manualSearchButton");

    public NvidiaDriversPage(WebDriver webDriver) {
        super(webDriver);
    }

    public NvidiaDriversPage openPage() {
        webDriver.get(SEARCH_PAGE_URL);
        logger.info("Nvidia drivers page opened");
        return this;
    }

    public NvidiaDriversPage selectPageLocaleRu() {
        new WebDriverWait(webDriver, Duration.ofSeconds(3))
                .until(ExpectedConditions.elementToBeClickable(localePromptRuButton)).click();
        return this;
    }

    public NvidiaDriversPage fillForm(NvidiaDriver nvidiaDriver) {
        return this.selectProduct(nvidiaDriver.getProduct())
                .selectSeries(nvidiaDriver.getSeries())
                .selectModel(nvidiaDriver.getModel())
                .selectOS(nvidiaDriver.getOs())
                .selectLang(nvidiaDriver.getLang())
                .selectType(nvidiaDriver.getType());
    }

    public NvidiaDriversPage selectProduct(String product) {
        WebElement productParam = new WebDriverWait(webDriver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(productParamLocator));
        ((JavascriptExecutor) webDriver)
                .executeScript("arguments[0].scrollIntoView(true);javascript:window.scrollBy(0,-100);", productParam);
        productParam.click();
        new WebDriverWait(webDriver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(productListLocator));
        webDriver.findElement(By.xpath(productListElementLocatorPart + product + "']")).click();
        return this;
    }

    public NvidiaDriversPage selectSeries(String series) {
        new WebDriverWait(webDriver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(seriesParamLocator)).click();
        new WebDriverWait(webDriver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(seriesListLocator));
        webDriver.findElement(By.xpath(seriesListElementLocatorPart + series + "']")).click();
        return this;
    }

    public NvidiaDriversPage selectModel(String model) {
        new WebDriverWait(webDriver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.and(ExpectedConditions.or(
                                ExpectedConditions.presenceOfElementLocated(
                                        By.xpath(defaultModelParamTextLocator + defaultModelParam_GeForceRTX3090Ti + "']")),
                                ExpectedConditions.presenceOfElementLocated(
                                        By.xpath(defaultModelParamTextLocator + defaultModelParam_GeForceGTX980 + "']"))),
                        ExpectedConditions.elementToBeClickable(modelParamLocator)));
        webDriver.findElement(modelParamLocator).click();
        new WebDriverWait(webDriver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(modelListLocator));
        webDriver.findElement(By.xpath(modelListElementLocatorPart + model + "']")).click();
        return this;
    }

    public NvidiaDriversPage selectOS(String os) {
        new WebDriverWait(webDriver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(osParamLocator)).click();
        new WebDriverWait(webDriver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(osListLocator));
        webDriver.findElement(By.xpath(osListElementLocatorPart + os + "']")).click();
        return this;
    }

    public NvidiaDriversPage selectLang(String lang) {
        new WebDriverWait(webDriver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(langParamLocator)).click();
        new WebDriverWait(webDriver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(langListLocator));
        webDriver.findElement(By.xpath(langListElementLocatorPart + lang + "']")).click();
        return this;
    }

    public NvidiaDriversPage selectType(String type) {
        new WebDriverWait(webDriver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(typeParamLocator)).click();
        new WebDriverWait(webDriver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(typeListLocator));
        webDriver.findElement(By.xpath(typeListElementLocatorPart + type + "']")).click();
        return this;
    }

    public NvidiaDriversSearchResultsPage searchForDrivers() {
        new WebDriverWait(webDriver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        logger.info("Nvidia drivers search completed");
        return new NvidiaDriversSearchResultsPage(webDriver);
    }
}
