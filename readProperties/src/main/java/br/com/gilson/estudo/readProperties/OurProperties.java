package br.com.gilson.estudo.readProperties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class OurProperties {
	
	private static String PATH_PROPERTIES = "./properties/config.properties";
	private static Properties  prop;
	
	private static void buildProperties() throws IOException {
		if(prop == null) {
			prop = new Properties();
			try {
				InputStream file = new FileInputStream(PATH_PROPERTIES);
				prop.load(file);
			}catch(IOException e) {
				prop = null;
				throw e;
			}
		}
	}
	
	public static Properties getPropertiesInformix() throws IOException {
		if(prop == null) {
			buildProperties();
		}
		return prop;
	}
	

}