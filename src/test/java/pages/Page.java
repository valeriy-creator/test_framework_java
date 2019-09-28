package pages;

import driverFactory.DriverFactory;
import java.util.ResourceBundle;

public abstract class Page extends DriverFactory {
    public abstract ResourceBundle getResourceBundle();
}