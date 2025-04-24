package core.context;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import core.utils.ConfigReader;

public class BrowserTestFactory {
    
    @DataProvider(name = "browsers", parallel = true)
    public static Object[][] getBrowsers() {
        ConfigReader configReader = new ConfigReader();
        String[] browsers = configReader.getBrowsersList();
        Object[][] data = new Object[browsers.length][1];
        for (int i = 0; i < browsers.length; i++) {
            data[i][0] = browsers[i];
        }
        return data;
    }
}