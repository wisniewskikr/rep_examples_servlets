package pl.kwi.services;

import java.util.Properties;

/**
 * Class of service for name.
 * 
 * @author Krzysztof Wisniewski
 *
 */
public class NameService {
	
	
	public static final String NAME_PROP = "name";
	private Properties props;

	
	public NameService(){		
		props = System.getProperties();
	}

	
	/**
	 * Method saves name to system properties.
	 * 
	 * @param name object String with name
	 */
	public void save(String name){
		props.setProperty(NAME_PROP, name);
	}
	
	/**
	 * Method loads name from system properties.
	 * 
	 * @return object String with name
	 */
	public String load() {
		return props.getProperty(NAME_PROP);
	}
	
	
	// ************************************************************************************************** //
	// ************************************ GETTERS AND SETTERS ***************************************** //
	// ************************************************************************************************** //
	
	
	public Properties getProps() {
		return props;
	}
	public void setProps(Properties props) {
		this.props = props;
	}
	
	
}
