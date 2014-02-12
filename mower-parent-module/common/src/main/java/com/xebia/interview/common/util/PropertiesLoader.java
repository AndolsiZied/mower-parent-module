package com.xebia.interview.common.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Utility class for loading properties file.
 * 
 * @author Zied ANDOLSI
 * 
 */
public final class PropertiesLoader {

	/**
	 * Resource bundles.
	 */
	private ResourceBundle properties;

	/**
	 * Constructs a new <code>PropertiesLoader</code>. It initialize the resource name.
	 * 
	 * @param resourceName
	 *            resource name
	 */
	public PropertiesLoader(String resourceName) {
		this(resourceName, Locale.getDefault());
	}

	/**
	 * Constructs a new <code>PropertiesLoader</code>. It initialize the resource name and locale.
	 * 
	 * @param resourceName
	 *            resource name
	 * @param locale
	 *            locale
	 */
	public PropertiesLoader(String resourceName, Locale locale) {
		properties = ResourceBundle.getBundle(resourceName, locale);
	}

	/**
	 * Loads property corresponding to the received key as parameter.
	 * 
	 * @param key
	 *            property key.
	 * @return property value
	 */
	public String loadProperty(String key) {
		if (properties != null) {
			return properties.getString(key);
		}
		return null;
	}

}
