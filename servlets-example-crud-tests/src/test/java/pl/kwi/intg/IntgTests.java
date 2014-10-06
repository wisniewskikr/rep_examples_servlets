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

import pl.kwi.db.jpa.DbUnitUtil;
import pl.kwi.intg.pages.TableIntgTestPage;

@RunWith(Arquillian.class)
public class IntgTests {
	
	
	private final static String PATH_HOST = System.getProperty("test.intg.path.host");
	private final static String PATH_CONTEXT = System.getProperty("test.intg.path.context");
	private final static String WAR_FILE = PATH_CONTEXT + ".war";
	private static final String WEBAPP_SRC = "src/main/webapp"; 
	
	private final static String DB_URL = System.getProperty("test.db.url");
	private final static String DB_USERNAME = System.getProperty("test.db.username");
	private final static String DB_PASSWORD = System.getProperty("test.db.password");
	private final static String DB_DRIVER = System.getProperty("test.db.driver");
	
	private TableIntgTestPage tablePage;
	
	
	@Drone
	WebDriver driver;
	
	
	// ATTENTION!!!
	// Parameter "testable = false" is very important and 
	// makes that @Drone is not null
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
        		.addAsLibraries(lib)
        		.addAsResource("intg-tests/persistence.xml", "META-INF/persistence.xml");;
        
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
		
		tablePage = new TableIntgTestPage(driver, wait);
		
	}
	
	@Test
	public void tableTestCase() {
		

		DbUnitUtil.executeDataFile("/dbunit/userDaoTest.xml", DB_DRIVER, DB_URL, DB_USERNAME, DB_PASSWORD);
		
		tablePage.initBrowserByUrl(PATH_HOST + PATH_CONTEXT);
		
		tablePage.checkIfPageLoaded();
		
		tablePage.checkBodyInElementByXPath("//label[@for='selectedUsersIds1']", "User1");
		tablePage.checkBodyInElementByXPath("//label[@for='selectedUsersIds2']", "User2");
		tablePage.checkBodyInElementByXPath("//label[@for='selectedUsersIds3']", "User3");
		
		tablePage.closeBrowser();
		
		
	}
	
	
	

}
