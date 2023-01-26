package by.belstu.it.lyskov.pageobject;

import by.belstu.it.lyskov.model.NvidiaDownload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class NvidiaDownloadsPage extends AbstractPage {
    public static final String SEARCH_PAGE_URL = "https://www.nvidia.ru/Download/index.aspx?lang=ru";

    Logger logger = LogManager.getRootLogger();

    @FindBy(id = "selProductSeriesType")
    private WebElement productSeriesTypeSelect;

    @FindBy(id = "selProductSeries")
    private WebElement productSeriesSelect;

    @FindBy(id = "selProductFamily")
    private WebElement productFamilySelect;

    @FindBy(id = "selOperatingSystem")
    private WebElement operatingSystemSelect;

    @FindBy(id = "ddlDownloadTypeCrdGrd")
    private WebElement downloadTypeSelect;

    @FindBy(id = "ddlLanguage")
    private WebElement languageSelect;

    @FindBy(xpath = "//*[@id='ManualSearchButtonTD']/a")
    private WebElement searchButton;

    public NvidiaDownloadsPage(WebDriver webDriver) {
        super(webDriver);
    }

    public NvidiaDownloadsPage openPage() {
        webDriver.get(SEARCH_PAGE_URL);
        logger.info("Nvidia downloads page opened");
        return this;
    }

    public NvidiaDownloadsPage fillForm(NvidiaDownload nvidiaDownload) {
        return this.selectProductSeriesType(nvidiaDownload.getProductSeriesType())
                .selectProductSeries(nvidiaDownload.getProductSeries())
                .selectProductFamily(nvidiaDownload.getProductFamily())
                .selectOperatingSystem(nvidiaDownload.getOperatingSystem())
                .selectDownloadType(nvidiaDownload.getDownloadType())
                .selectLanguage(nvidiaDownload.getLanguage());
    }

    public NvidiaDownloadsPage selectProductSeriesType(String seriesType) {
        new Select(productSeriesTypeSelect).selectByVisibleText(seriesType);
        return this;
    }

    public NvidiaDownloadsPage selectProductSeries(String series) {
        new Select(productSeriesSelect).selectByVisibleText(series);
        return this;
    }

    public NvidiaDownloadsPage selectProductFamily(String family) {
        new Select(productFamilySelect).selectByVisibleText(family);
        return this;
    }

    public NvidiaDownloadsPage selectOperatingSystem(String operatingSystem) {
        new Select(operatingSystemSelect).selectByVisibleText(operatingSystem);
        return this;
    }

    public NvidiaDownloadsPage selectDownloadType(String downloadType) {
        new Select(downloadTypeSelect).selectByVisibleText(downloadType);
        return this;
    }

    public NvidiaDownloadsPage selectLanguage(String language) {
        new Select(languageSelect).selectByVisibleText(language);
        return this;
    }

    public NvidiaDownloadsSearchResultsPage searchForDownloads() {
        searchButton.click();
        logger.info("Nvidia downloads search completed");
        return new NvidiaDownloadsSearchResultsPage(webDriver);
    }
}
