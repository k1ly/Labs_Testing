package by.belstu.it.lyskov.pageobject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NvidiaInfosPage extends AbstractPage {
    public static final String SEARCH_PAGE_URL = "https://www.nvidia.com/ru-ru/search/";

    Logger logger = LogManager.getRootLogger();

    @FindBy(xpath = "//*[@class='ant-select-search__field']")
    private WebElement searchInput;

    @FindBy(xpath = "//*[@class='search-input-right']/button")
    private WebElement searchButton;

    public NvidiaInfosPage(WebDriver webDriver) {
        super(webDriver);
    }

    public NvidiaInfosPage openPage() {
        webDriver.get(SEARCH_PAGE_URL);
        logger.info("Nvidia downloads page opened");
        return this;
    }

    public NvidiaInfosPage enterTerm(String term) {
        searchInput.sendKeys(term);
        return this;
    }

    public NvidiaInfosSearchResultsPage searchForInfos() {
        searchButton.click();
        logger.info("Nvidia infos search completed");
        return new NvidiaInfosSearchResultsPage(webDriver);
    }
}
