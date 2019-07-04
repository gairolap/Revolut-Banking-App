/**
 * Util class to read and load H2 data source config. 
 */
package com.org.revolut.banking.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import lombok.extern.log4j.Log4j;

@Log4j
public class AccountUtil {

	static Properties properties = new Properties();

	/**
	 * Reads config file into properties.
	 * 
	 * @param fileName.
	 */
	public static void loadConfig(String fileName) {

		if (fileName == null) {

			log.error("config file could not be found...");
		} else {
			try {
				log.info("lodaing config file...");
				final InputStream fis = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
				properties.load(fis);
			} catch (IOException ioexp) {
				log.error("something went wrong while processing the config file...");
			}
		}
	}

	/**
	 * Gets the value as string for given key.
	 * 
	 * @param key.
	 * @return value.
	 */
	public static String getStringProperty(String key) {
		String value = properties.getProperty(key);
		if (value == null) {
			value = System.getProperty(key);
		}
		return value;
	}

	static {
		String configFileName = System.getProperty("bankingapp.properties");

		if (configFileName == null) {
			configFileName = "bankingapp.properties";
		}
		loadConfig(configFileName);

	}

}