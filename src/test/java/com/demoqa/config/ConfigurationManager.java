package com.demoqa.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Properties properties;
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationManager.class);

    /**
     * Initializes a new ConfigurationManager instance.
     * Loads properties from the config.properties file located in the classpath.
     * If the file is not found or cannot be loaded, logs an error message.
     */
    public ConfigurationManager() {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                LOGGER.error("Configuration file 'config.properties' not found in classpath");
                return;
            }
            properties.load(input);
            LOGGER.info("Configuration loaded successfully from config.properties");
        } catch (IOException ex) {
            LOGGER.error("Failed to load configuration from config.properties: {}", ex.getMessage(), ex);
        }
    }

    /**
     * Gets the base URL of the application under test.
     *
     * @return The base URL as specified in config.properties
     * @throws IllegalStateException if the base.url property is not set
     */
    public String getBaseUrl() {
        String baseUrl = properties.getProperty("base.url");
        if (baseUrl == null) {
            LOGGER.error("Property 'base.url' is not defined in config.properties");
            throw new IllegalStateException("base.url property is required but not set");
        }
        LOGGER.debug("Retrieved base.url: {}", baseUrl);
        return baseUrl;
    }

    /**
     * Gets the page load timeout for WebDriver in seconds.
     *
     * @return The page load timeout in seconds
     * @throws NumberFormatException if the value cannot be parsed to long
     * @throws IllegalStateException if the page.load.timeout.seconds property is not set
     */
    public long getPageLoadTimeoutSeconds() {
        String value = properties.getProperty("page.load.timeout.seconds");
        if (value == null) {
            LOGGER.error("Property 'page.load.timeout.seconds' is not defined in config.properties");
            throw new IllegalStateException("page.load.timeout.seconds property is required but not set");
        }
        try {
            long seconds = Long.parseLong(value);
            LOGGER.debug("Retrieved page.load.timeout.seconds: {}", seconds);
            return seconds;
        } catch (NumberFormatException e) {
            LOGGER.error("Property 'page.load.timeout.seconds' has invalid format: {}", value);
            throw new NumberFormatException("page.load.timeout.seconds must be a valid number");
        }
    }
}