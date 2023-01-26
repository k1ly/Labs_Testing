package by.belstu.it.lyskov.util;

import by.belstu.it.lyskov.driver.DriverSingleton;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotTestListener implements TestWatcher {

    Logger logger = LogManager.getRootLogger();

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        saveScreenShot();
        DriverSingleton.closeDriver();
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        DriverSingleton.closeDriver();
    }

    private void saveScreenShot() {
        File screenShot = ((TakesScreenshot) DriverSingleton
                .getWebDriver()).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenShot, new File(".//target//screenshots/"
                    + ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"))
                    + ".png"));
        } catch (IOException e) {
            logger.error("Failed to save screenshot: " + e.getLocalizedMessage());
        }
    }
}
