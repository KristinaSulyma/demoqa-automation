package com.demoqa.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Manages application configuration properties loaded from config.properties file.
 * Provides type-safe access to configuration values with proper type conversion.
 *
 * <p>The configuration file should be placed in the resources folder and
 * contain at least these properties:
 * <ul>
 *   <li>base.url - The base URL of the application under test</li>
 *   <li>implicit.wait.seconds - Default implicit wait timeout for WebDriver</li>
 *   <li>page.load.timeout.seconds - Page load timeout for WebDriver</li>
 * </ul>
 */
public class ConfigurationManager {
    private Properties properties;

    /**
     * Initializes a new ConfigurationManager instance.
     * Loads properties from the config.properties file located in the classpath.
     * If the file is not found or cannot be loaded, prints an error message.
     */
    public ConfigurationManager() {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Gets the base URL of the application under test.
     *
     * @return The base URL as specified in config.properties
     * @throws NullPointerException if the base.url property is not set
     */
    public String getBaseUrl() {
        return properties.getProperty("base.url");
    }

    /**
     * Gets the implicit wait timeout for WebDriver in seconds.
     *
     * @return The implicit wait timeout in seconds
     * @throws NumberFormatException if the value cannot be parsed to long
     * @throws NullPointerException if the implicit.wait.seconds property is not set
     */
    public long getImplicitWaitSeconds() {
        return Long.parseLong(properties.getProperty("implicit.wait.seconds"));
    }

    /**
     * Gets the page load timeout for WebDriver in seconds.
     *
     * @return The page load timeout in seconds
     * @throws NumberFormatException if the value cannot be parsed to long
     * @throws NullPointerException if the page.load.timeout.seconds property is not set
     */
    public long getPageLoadTimeoutSeconds() {
        return Long.parseLong(properties.getProperty("page.load.timeout.seconds"));
    }
}