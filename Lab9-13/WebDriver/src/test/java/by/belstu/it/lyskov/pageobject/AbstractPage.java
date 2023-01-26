package by.belstu.it.lyskov.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public abstract class AbstractPage {

    protected static final int WAIT_TIMEOUT_SECONDS = 10;

    protected WebDriver webDriver;

    protected AbstractPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }
}
