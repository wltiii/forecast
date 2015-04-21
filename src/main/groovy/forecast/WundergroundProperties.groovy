package forecast

import java.io.Reader;

import groovy.transform.Immutable
import groovy.transform.ToString

@ToString
final class WundergroundProperties {
	
	private final Properties props
	
	private WundergroundProperties(String propertiesFile) {
		println "WundergroundProperties(String ${propertiesFile})"
		InputStream is = getClass().getResourceAsStream(propertiesFile)
		
		props = new Properties()
		props.load(is);
		
		is.close();
	}
	
	static final WundergroundProperties from(String propertiesFile) {
		println "WundergroundProperties.from(String ${propertiesFile})"
		new WundergroundProperties(propertiesFile)
	}
	
	public String getApiKey() {
		props.apikey
	}
}
