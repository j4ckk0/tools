package org.jtools.utils.resources;

/*-
 * #%L
 * Java Tools - Utils
 * %%
 * Copyright (C) 2024 jtools.org
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
// TODO: Auto-generated Javadoc

/**
 * The Class ResourcesManager.
 */
public class ResourcesManager {

	//////////////////////////////////////////////////
	//
	// Class variables and constants
	//
	//////////////////////////////////////////////////

	/** The instance. */
	private static ResourcesManager instance;

	/** The Constant LABEL_PROPERTIES_FILE. */
	protected static final String LABEL_PROPERTIES_FILE = "labels.properties";

	/** The Constant CONFIG_PROPERTIES_FILE. */
	protected static final String CONFIG_PROPERTIES_FILE = "config.properties";

	/** The Constant PREFERENCES_PROPERTIES_FILE. */
	protected static final String PREFERENCES_PROPERTIES_FILE = "resources/conf/preferences.properties";

	/** The images. */
	private HashMap<String, BufferedImage> images;

	/** The preferences properties. */
	private Properties preferencesProperties;

	/** The labels properties. */
	private Properties labelsProperties;

	/** The config properties. */
	private Properties configProperties;

	//////////////////////////////////////////////////
	//
	// Constructors
	//
	//////////////////////////////////////////////////
	
	/**
	 * Instantiates a new resources manager.
	 */
	private ResourcesManager() {
		images = new HashMap<>();
//		preferencesProperties = this.loadExternalProperties(PREFERENCES_PROPERTIES_FILE);
//		configProperties = this.loadPropertiesInJar(CONFIG_PROPERTIES_FILE);
//		labelsProperties = this.loadPropertiesInJar(LABEL_PROPERTIES_FILE);
	}
	
	/**
	 * Instance.
	 *
	 * @return the resources manager
	 */
	public static ResourcesManager instance() {
		if (null == instance) {
			instance = new ResourcesManager();
		}
		return instance;
	}

	//////////////////////////////////////////////////
	//
	// Public methods
	//
	//////////////////////////////////////////////////
	
	/**
	 * Gets the image.
	 *
	 * @param imageFilename the image filename
	 * @return the image
	 */
	public BufferedImage getImage(String imageFilename) {
		BufferedImage image = images.get(imageFilename);
		if (null == image) {
			InputStream iconInputStream = ClassLoader.getSystemResourceAsStream(imageFilename);
			try {
				image = ImageIO.read(iconInputStream);
			} catch (IOException e) {
				Logger.getLogger(ResourcesManager.class.getName()).log(Level.SEVERE, e.getMessage(), e);
			}
		}
		return image;
	}

	/**
	 * Gets the label.
	 *
	 * @param key the key
	 * @return the label
	 */
	public String getLabel(String key) {
		String langage = getPreferencesProperty("langage");
		String label = labelsProperties.getProperty(key + "." + langage);
		if (null == label) {
			label = "/!\\ " + key;
			Logger.getLogger(ResourcesManager.class.getName()).log(Level.WARNING, "Missing label: " + key);
		}
		return label;
	}

	/**
	 * Gets the label.
	 *
	 * @param key the key
	 * @param replacementValues the replacement values
	 * @return the label
	 */
	public String getLabel(String key, String... replacementValues) {
		String label = this.getLabel(key);
		for (int i = 0; i < replacementValues.length; i++) {
			label = label.replace("{" + i + "}", replacementValues[i]);
		}
		return label;
	}

	/**
	 * Gets the preferences property.
	 *
	 * @param key the key
	 * @return the preferences property
	 */
	public String getPreferencesProperty(String key) {
		return preferencesProperties.getProperty(key);
	}

	/**
	 * Gets the config property.
	 *
	 * @param key the key
	 * @return the config property
	 */
	public String getConfigProperty(String key) {
		return configProperties.getProperty(key);
	}

	/**
	 * Gets the config property int.
	 *
	 * @param key the key
	 * @return the config property int
	 * @throws NumberFormatException the number format exception
	 */
	public Integer getConfigPropertyInt(String key) throws NumberFormatException {
		String property = configProperties.getProperty(key);
		if (property.equals("MAX")) {
			return Integer.MAX_VALUE;
		}
		if (property.equals("MAX")) {
			return Integer.MIN_VALUE;
		}

		return Integer.parseInt(property);
	}

	/**
	 * Gets the config property float.
	 *
	 * @param key the key
	 * @return the config property float
	 * @throws NumberFormatException the number format exception
	 */
	public Float getConfigPropertyFloat(String key) throws NumberFormatException {
		String property = configProperties.getProperty(key);
		if (property.equals("MAX")) {
			return Float.MAX_VALUE;
		}
		if (property.equals("MAX")) {
			return Float.MIN_VALUE;
		}

		return Float.parseFloat(property);
	}

	/**
	 * Gets the config property boolean.
	 *
	 * @param key the key
	 * @return the config property boolean
	 * @throws NumberFormatException the number format exception
	 */
	public Boolean getConfigPropertyBoolean(String key) throws NumberFormatException {
		String propriete = configProperties.getProperty(key);

		return Boolean.parseBoolean(propriete);
	}

	/**
	 * Load file content.
	 *
	 * @param filename the filename
	 * @return the string
	 */
	public String loadFileContent(String filename) {
		try {
			InputStream is = ClassLoader.getSystemResourceAsStream(filename);
			return IOUtils.toString(is, StandardCharsets.UTF_8);
		} catch (IOException e) {
			Logger.getLogger(ResourcesManager.class.getName()).log(Level.SEVERE, e.getMessage(), e);
		}
		return null;
	}

	//////////////////////////////////////////////////
	//
	// Private methods
	//
	//////////////////////////////////////////////////

	/**
	 * Load external properties.
	 *
	 * @param propertiesFilename the properties filename
	 * @return the properties
	 */
	protected Properties loadExternalProperties(String propertiesFilename) {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(propertiesFilename));
		} catch (IOException e) {
			Logger.getLogger(ResourcesManager.class.getName()).log(Level.SEVERE, e.getMessage(), e);
		}
		return properties;
	}

	/**
	 * Load properties in jar.
	 *
	 * @param propertiesFileName the properties file name
	 * @return the properties
	 */
	protected Properties loadPropertiesInJar(String propertiesFileName) {
		Properties properties = new Properties();
		InputStream propertiesIS = ClassLoader.getSystemResourceAsStream(propertiesFileName);
		try {
			properties.load(propertiesIS);
		} catch (IOException e) {
			Logger.getLogger(ResourcesManager.class.getName()).log(Level.SEVERE, e.getMessage(), e);
		}
		return properties;
	}
}
