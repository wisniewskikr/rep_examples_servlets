package pl.kwi.intg;


import java.io.File;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.ScopeType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

@RunWith(Arquillian.class)
public class IntgTests {
	
	
	private final static String PATH_HOST = System.getProperty("test.intg.path.host");
	private final static String PATH_CONTEXT = System.getProperty("test.intg.path.context");
	private final static String WAR_FILE = PATH_CONTEXT + ".war";
	private static final String WEBAPP_SRC = "src/main/webapp"; 
	
	private InputIntgTestPage inputPage;
	private OutputIntgTestPage outputPage;
	
	
	@Drone
	WebDriver driver;
	
	
    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        File[] lib = Maven
        		.resolver()
        		.loadPomFromFile("pom.xml")
        		.importDependencies(ScopeType.COMPILE)
                .resolve()
                .withTransitivity().as(File.class);
         
        WebArchive war =  ShrinkWrap
        		.create(WebArchive.class, WAR_FILE)
        		.addPackages(true, "pl.kwi")
        		.addAsLibraries(lib);
        
        war.merge(ShrinkWrap.create(GenericArchive.class)
        		.as(ExplodedImporter.class)  
        	    .importDirectory(WEBAPP_SRC)
        	    .as(GenericArchive.class),  
        	    "/", Filters.includeAll());  
         
//        System.out.println(war.toString(true));
         
        return war;
    }
    
	@Before
	public void setUp(){
		
		Wait<WebDriver> wait = new WebDriverWait(driver, 10);		
		
		inputPage = new InputIntgTestPage(driver, wait);
		outputPage = new OutputIntgTestPage(driver, wait);
		
	}
	
	@Test
	public void typeNameToInputPageAndCheckOutputPage() {
		
		inputPage.initBrowserByUrl(PATH_HOST + PATH_CONTEXT);
	
		inputPage.checkIfPageLoaded();
		inputPage.typeTextInFieldById("name", "Chris");
		inputPage.pressButtonById("submit");
		
		outputPage.checkIfPageLoaded();
		outputPage.checkTextInFieldById("name", "Hello World Chris");
		outputPage.pressButtonById("submit");
		
		inputPage.checkIfPageLoaded();
		
	}
	
	@Test
	public void typeNoNameToInputPage() {
		
		inputPage.initBrowserByUrl(PATH_HOST + PATH_CONTEXT);
			
		inputPage.checkIfPageLoaded();
		inputPage.typeTextInFieldById("name", "");
		inputPage.pressButtonById("submit");
		
		inputPage.checkIfPageLoaded();
		inputPage.checkTextInFieldById("name.errors", "Please fill this field");
		
	}
	

}
