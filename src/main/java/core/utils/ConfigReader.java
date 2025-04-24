package core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private final Properties properties;

    public ConfigReader() {
        properties = new Properties();
        loadProperties();
    }

    private void loadProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IllegalStateException("Unable to find config.properties");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public String getConfigValue(String key) {
        String value = System.getProperty(key); // Allow command line override
        return value != null ? value : properties.getProperty(key);
    }

    public String getBrowser() {
        return getConfigValue("browser");
    }

    public String getGridUrl() {
        return getConfigValue("grid.url");
    }

    public String getLoginUrl() {
        return getConfigValue("login.url");
    }

    // New methods for parallel execution and browser configuration
    public boolean isParallelExecution() {
        return Boolean.parseBoolean(getConfigValue("parallel.tests"));
    }

    public int getThreadCount() {
        return Integer.parseInt(getConfigValue("thread.count"));
    }

    public String[] getBrowsersList() {
        String browsers = getConfigValue("browsers");
        return browsers != null ? browsers.split(",") : new String[]{"chrome"};
    }
    
    public String getBrowserVersion(String browser) {
        return getConfigValue(browser + ".version");
    }
}