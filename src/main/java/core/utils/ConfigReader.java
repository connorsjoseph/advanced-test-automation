package core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Logger logger = LoggerFactory.getLogger(ConfigReader.class);
    private final Properties properties;
    private static final String DEFAULT_ENV = "qa";

    public ConfigReader() {
        properties = new Properties();
        loadProperties();
    }

    private void loadProperties() {
        String env = System.getProperty("env", DEFAULT_ENV);
        String configFile = String.format("config/%s.properties", env.toLowerCase());

        try (InputStream input = getClass().getClassLoader().getResourceAsStream(configFile)) {
            if (input == null) {
                logger.error("Unable to find {}", configFile);
                // Fallback to default config.properties if environment specific file is not found
                try (InputStream defaultInput = getClass().getClassLoader().getResourceAsStream("config.properties")) {
                    if (defaultInput == null) {
                        throw new IllegalStateException("Unable to find config.properties");
                    }
                    properties.load(defaultInput);
                    logger.info("Loaded default config.properties");
                }
            } else {
                properties.load(input);
                logger.info("Loaded configuration for environment: {}", env);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration", e);
        }
    }

    public String getConfigValue(String key) {
        String value = System.getProperty(key); // Allow command line override
        if (value != null) {
            return value;
        }

        value = properties.getProperty(key);
        if (value == null) {
            logger.warn("Configuration key not found: {}", key);
        }
        return value;
    }

    // Existing methods remain unchanged
    public String getBrowser() {
        return getConfigValue("browser");
    }

    public String getGridUrl() {
        return getConfigValue("grid.url");
    }

    public String getLoginUrl() {
        return getConfigValue("login.url");
    }

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

    // New helper method to get environment
    public String getCurrentEnvironment() {
        return System.getProperty("env", DEFAULT_ENV);
    }
}