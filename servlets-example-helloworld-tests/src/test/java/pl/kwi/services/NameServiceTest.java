package pl.kwi.services;

import static org.junit.Assert.*;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

public class NameServiceTest {
	
	
	private NameService nameService;
	private Properties props;
	

	@Before
	public void setUp() throws Exception {
		props = new Properties();
		nameService = new NameService();
		nameService.setProps(props);
	}

	@Test
	public void save() {
				
		nameService.save("Chris");
		
		String name = (String)props.get(NameService.NAME_PROP);
		
		assertEquals("Chris", name);
		
	}
	
	@Test
	public void load() {
		
		props.put(NameService.NAME_PROP, "Chris");
		
		String name = nameService.load();
		
		assertEquals("Chris", name);
		
	}

}
