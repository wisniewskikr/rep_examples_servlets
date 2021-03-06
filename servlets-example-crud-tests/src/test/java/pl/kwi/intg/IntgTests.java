package pl.kwi.intg;



import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.jboss.shrinkwrap.resolver.api.maven.filter.ScopeFilter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import pl.kwi.db.jpa.DbUnitUtil;
import pl.kwi.intg.pages.CreateIntgTestPage;
import pl.kwi.intg.pages.DeleteIntgTestPage;
import pl.kwi.intg.pages.EditIntgTestPage;
import pl.kwi.intg.pages.TableIntgTestPage;
import pl.kwi.intg.pages.ViewIntgTestPage;

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
	private CreateIntgTestPage createPage;
	private ViewIntgTestPage viewPage;
	private EditIntgTestPage editPage;
	private DeleteIntgTestPage deletePage;
	
	
	
	// ATTENTION!!!
	// Parameter "testable = false" is very important and 
	// makes that @Drone is not null
    @Deployment(testable = false)
    public static WebArchive createDeployment() {
    	MavenDependencyResolver resolver = DependencyResolvers
				.use(MavenDependencyResolver.class)
				.loadMetadataFromPom("pom.xml")
				.includeDependenciesFromPom("pom.xml");
         
        WebArchive war =  ShrinkWrap
        		.create(WebArchive.class, WAR_FILE)
        		.addPackages(true, "pl.kwi")
        		.addAsLibraries(resolver.resolveAsFiles(new ScopeFilter("", "compile", "runtime")))
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
		
		WebDriver driver = new FirefoxDriver();
		Wait<WebDriver> wait = new WebDriverWait(driver, 10);		
		
		tablePage = new TableIntgTestPage(driver, wait);
		createPage = new CreateIntgTestPage(driver, wait);
		viewPage = new ViewIntgTestPage(driver, wait);
		editPage = new EditIntgTestPage(driver, wait);
		deletePage = new DeleteIntgTestPage(driver, wait);
		
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
	
	@Test
	public void createTestCase() {
		
		DbUnitUtil.clearDataFile("/dbunit/userDaoTest.xml", DB_DRIVER, DB_URL, DB_USERNAME, DB_PASSWORD);
				
		tablePage.initBrowserByUrl(PATH_HOST + PATH_CONTEXT);

		
		tablePage.checkIfPageLoaded();
		tablePage.checkTextInFieldById("noData", "No Data");
		tablePage.clickLinkByText("Create");
		
		createPage.checkIfPageLoaded();
		createPage.pressButtonById("back");
		
		tablePage.checkIfPageLoaded();
		tablePage.checkTextInFieldById("noData", "No Data");
		tablePage.clickLinkByText("Create");
		
		createPage.checkIfPageLoaded();
		createPage.typeTextInFieldById("name", "User1");
		createPage.pressButtonById("create");
		
		tablePage.checkIfPageLoaded();
		tablePage.checkBodyInElementByXPath("//label", "User1");
		
		tablePage.closeBrowser();
		
	}
	
	@Test
	public void readTestCase() {
		
		DbUnitUtil.executeDataFile("/dbunit/userDaoTest.xml", DB_DRIVER, DB_URL, DB_USERNAME, DB_PASSWORD);
		
		tablePage.initBrowserByUrl(PATH_HOST + PATH_CONTEXT);
		
		tablePage.checkIfPageLoaded();		
		tablePage.pressButtonById("selectedUsersIds1");
		tablePage.clickLinkByText("View");
		
		viewPage.checkIfPageLoaded();
		viewPage.checkAttributeInElementdById("name", "value", "User1");
		viewPage.pressButtonById("back");
				
		tablePage.checkIfPageLoaded();
		
		tablePage.closeBrowser();
		
				
	}
	
	@Test
	public void readTestCaseValidation() {
		
		DbUnitUtil.executeDataFile("/dbunit/userDaoTest.xml", DB_DRIVER, DB_URL, DB_USERNAME, DB_PASSWORD);
		
		tablePage.initBrowserByUrl(PATH_HOST + PATH_CONTEXT);
		
		tablePage.checkIfPageLoaded();	
		tablePage.clickLinkByText("View");
		
		tablePage.checkIfPageLoaded();	
		tablePage.checkTextInFieldById("errorMessage", "Select at least on row");		
		tablePage.pressButtonById("selectedUsersIds1");
		tablePage.pressButtonById("selectedUsersIds2");
		tablePage.clickLinkByText("View");
		
		tablePage.checkIfPageLoaded();	
		tablePage.checkTextInFieldById("errorMessage", "Only one row can be selected");
		
		tablePage.closeBrowser();
						
	}
	
	@Test
	public void updateTestCase() {
		
		DbUnitUtil.executeDataFile("/dbunit/userDaoTest.xml", DB_DRIVER, DB_URL, DB_USERNAME, DB_PASSWORD);
		
		tablePage.initBrowserByUrl(PATH_HOST + PATH_CONTEXT);
		
		tablePage.checkIfPageLoaded();		
		tablePage.pressButtonById("selectedUsersIds1");
		tablePage.clickLinkByText("Edit");
		
		editPage.checkIfPageLoaded();
		editPage.checkAttributeInElementdById("name", "value", "User1");
		editPage.pressButtonById("back");
				
		tablePage.checkIfPageLoaded();
		tablePage.pressButtonById("selectedUsersIds1");
		tablePage.clickLinkByText("Edit");
		
		editPage.checkIfPageLoaded();
		editPage.clearTextInFieldById("name");
		editPage.typeTextInFieldById("name", "User4");
		editPage.pressButtonById("update");
		
		tablePage.checkIfPageLoaded();
		tablePage.checkBodyInElementByXPath("//label[@for='selectedUsersIds1']", "User4");
		
		tablePage.closeBrowser();
						
	}
	
	@Test
	public void updateTestCaseValidation() {
		
		DbUnitUtil.executeDataFile("/dbunit/userDaoTest.xml", DB_DRIVER, DB_URL, DB_USERNAME, DB_PASSWORD);
		
		tablePage.initBrowserByUrl(PATH_HOST + PATH_CONTEXT);
		
		tablePage.checkIfPageLoaded();	
		tablePage.clickLinkByText("Edit");
		
		tablePage.checkIfPageLoaded();	
		tablePage.checkTextInFieldById("errorMessage", "Select at least on row");		
		tablePage.pressButtonById("selectedUsersIds1");
		tablePage.pressButtonById("selectedUsersIds2");
		tablePage.clickLinkByText("Edit");
		
		tablePage.checkIfPageLoaded();	
		tablePage.checkTextInFieldById("errorMessage", "Only one row can be selected");
		
		tablePage.closeBrowser();
						
	}
	
	@Test
	public void deleteTestCase() {
		
		DbUnitUtil.executeDataFile("/dbunit/userDaoTest.xml", DB_DRIVER, DB_URL, DB_USERNAME, DB_PASSWORD);
		
		tablePage.initBrowserByUrl(PATH_HOST + PATH_CONTEXT);
		
		tablePage.checkIfPageLoaded();	
		tablePage.checkBodyInElementByXPath("//label[@for='selectedUsersIds1']", "User1");
		tablePage.checkBodyInElementByXPath("//label[@for='selectedUsersIds2']", "User2");
		tablePage.checkBodyInElementByXPath("//label[@for='selectedUsersIds3']", "User3");
		tablePage.pressButtonById("selectedUsersIds1");
		tablePage.clickLinkByText("Delete");
		
		deletePage.checkIfPageLoaded();
		deletePage.checkBodyInElementByXPath("//div[@id='confirmationText']", "Do you really want delete user: User1?");
		deletePage.pressButtonById("back");
				
		tablePage.checkIfPageLoaded();
		tablePage.checkBodyInElementByXPath("//label[@for='selectedUsersIds1']", "User1");
		tablePage.checkBodyInElementByXPath("//label[@for='selectedUsersIds2']", "User2");
		tablePage.checkBodyInElementByXPath("//label[@for='selectedUsersIds3']", "User3");
		tablePage.pressButtonById("selectedUsersIds1");
		tablePage.clickLinkByText("Delete");
		
		deletePage.checkIfPageLoaded();
		deletePage.checkBodyInElementByXPath("//div[@id='confirmationText']", "Do you really want delete user: User1?");
		deletePage.pressButtonById("delete");
		
		tablePage.checkIfPageLoaded();
		tablePage.checkElementNotExistsByXPath("//label[@for='selectedUsersIds1']");
		
		tablePage.closeBrowser();
						
	}
	
	@Test
	public void deleteTestCaseValidation() {
		
		DbUnitUtil.executeDataFile("/dbunit/userDaoTest.xml", DB_DRIVER, DB_URL, DB_USERNAME, DB_PASSWORD);
		
		tablePage.initBrowserByUrl(PATH_HOST + PATH_CONTEXT);
		
		tablePage.checkIfPageLoaded();	
		tablePage.clickLinkByText("Delete");
		
		tablePage.checkIfPageLoaded();	
		tablePage.checkTextInFieldById("errorMessage", "Select at least on row");		
		tablePage.pressButtonById("selectedUsersIds1");
		tablePage.pressButtonById("selectedUsersIds2");
		tablePage.clickLinkByText("Delete");
		
		tablePage.checkIfPageLoaded();	
		tablePage.checkTextInFieldById("errorMessage", "Only one row can be selected");
		
		tablePage.closeBrowser();
						
	}
	

}
