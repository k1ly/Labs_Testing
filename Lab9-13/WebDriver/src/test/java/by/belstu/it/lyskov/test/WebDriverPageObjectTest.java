package by.belstu.it.lyskov.test;

import by.belstu.it.lyskov.driver.DriverSingleton;
import by.belstu.it.lyskov.model.NvidiaDownload;
import by.belstu.it.lyskov.model.NvidiaDriver;
import by.belstu.it.lyskov.pageobject.NvidiaDownloadsPage;
import by.belstu.it.lyskov.pageobject.NvidiaDriversPage;
import by.belstu.it.lyskov.pageobject.NvidiaInfosPage;
import by.belstu.it.lyskov.service.NvidiaDriverCreator;
import by.belstu.it.lyskov.util.ScreenshotTestListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ScreenshotTestListener.class)
class WebDriverPageObjectTest {

    private WebDriver webDriver;

    @BeforeEach
    void initWebDriver() {
        webDriver = DriverSingleton.getWebDriver();
    }

    @Test
    void commonDriverParamsShouldReturnResults() {
        NvidiaDriver nvidiaDriver = new NvidiaDriver(
                "GeForce",
                "GeForce RTX 30 Series",
                "GeForce RTX 3090",
                "Windows 10 64-bit",
                "Japanese",
                "Драйвер Game Ready ");
        int searchDriversResultNumber = new NvidiaDriversPage(webDriver)
                .openPage()
                .selectPageLocaleRu()
                .fillForm(nvidiaDriver)
                .searchForDrivers()
                .countResultNumber();
        assertThat(searchDriversResultNumber).isPositive();
    }

    @Test
    void ineligibleDriverParamsShouldReturnEmptyResults() {
        NvidiaDriver nvidiaDriver = new NvidiaDriver(
                "GeForce",
                "GeForce 900M Series (Notebooks)",
                "GeForce GTX 950M",
                "FreeBSD x86",
                "Polski",
                "Драйверы для Linux с краткосрочной поддержкой");
        int searchDriversResultNumber = new NvidiaDriversPage(webDriver)
                .openPage()
                .selectPageLocaleRu()
                .fillForm(nvidiaDriver)
                .searchForDrivers()
                .countResultNumber();
        assertThat(searchDriversResultNumber).isZero();
    }

    @Test
    void commonDownloadParamsShouldReturnResults() {
        NvidiaDownload nvidiaDownload = new NvidiaDownload(
                "TITAN",
                "NVIDIA TITAN Series",
                "NVIDIA TITAN RTX",
                "Windows 10 64-bit",
                "Драйвер Game Ready (GRD)",
                "Русский");
        List<String> supportedProducts = new NvidiaDownloadsPage(webDriver)
                .openPage()
                .fillForm(nvidiaDownload)
                .searchForDownloads()
                .openResultsTab()
                .getSupportedProducts();
        assertThat(supportedProducts).hasSizeGreaterThan(0);
    }

    @Test
    void downloadParamsShouldReturnExpectedSupportedProducts() {
        NvidiaDownload nvidiaDownload = new NvidiaDownload(
                "TITAN",
                "NVIDIA TITAN Series",
                "NVIDIA TITAN RTX",
                "Windows 10 64-bit",
                "Драйвер Game Ready (GRD)",
                "Русский");
        List<String> supportedProducts = new NvidiaDownloadsPage(webDriver)
                .openPage()
                .fillForm(nvidiaDownload)
                .searchForDownloads()
                .openResultsTab()
                .getSupportedProducts();
        assertThat(supportedProducts).anyMatch(link -> link.contains(nvidiaDownload.getProductFamily()));
    }

    @Test
    void commonTermSearchShouldReturnResults() {
        String term = "GeForce RTX";
        List<String> searchResultInfoLinks = new NvidiaInfosPage(webDriver)
                .openPage()
                .enterTerm(term)
                .searchForInfos()
                .getInfoLinks();
        assertThat(searchResultInfoLinks).isNotEmpty();
    }

    @Test
    void specifiedTermSearchResultsShouldContainInfo() {
        String term = "GeForce GTX 970";
        List<String> searchResultInfoTitles = new NvidiaInfosPage(webDriver)
                .openPage()
                .enterTerm(term)
                .searchForInfos()
                .getInfoTitles();
        assertThat(searchResultInfoTitles).first().asString().containsIgnoringCase(term);
    }

    @Test
    void ineligibleTermSearchShouldReturnEmptyResults() {
        String term = "anime";
        List<String> searchResultInfoLinks = new NvidiaInfosPage(webDriver)
                .openPage()
                .enterTerm(term)
                .searchForInfos()
                .getInfoLinks();
        assertThat(searchResultInfoLinks).isEmpty();
    }

    @Test
    void filteredByWebsiteTermSearchResultsShouldContainFacet() {
        String term = "GeForce GTX 970";
        String filter = "GeForce";
        List<String> searchResultInfoLinks = new NvidiaInfosPage(webDriver)
                .openPage()
                .enterTerm(term)
                .searchForInfos()
                .filterBy(filter)
                .getInfoLinks();
        assertThat(searchResultInfoLinks).allMatch(link -> link.startsWith("www.nvidia.com/ru-ru/geforce/"));
    }

    @Test
    void sortedByDateTermSearchResultsShouldBeOrdered() {
        String term = "GeForce GTX 970";
        List<LocalDate> searchResultInfoDates = new NvidiaInfosPage(webDriver)
                .openPage()
                .enterTerm(term)
                .searchForInfos()
                .sortByDate()
                .getInfoDates();
        Collections.reverse(searchResultInfoDates);
        assertThat(searchResultInfoDates).isSorted();
    }

    @Test
    void dataDrivenDriverParamsSearchShouldReturnResults() {
        int searchDriversResultNumber = new NvidiaDriversPage(webDriver)
                .openPage()
                .selectPageLocaleRu()
                .fillForm(NvidiaDriverCreator.withParametersFromProperty())
                .searchForDrivers()
                .countResultNumber();
        assertThat(searchDriversResultNumber).isGreaterThanOrEqualTo(0);
    }
}
