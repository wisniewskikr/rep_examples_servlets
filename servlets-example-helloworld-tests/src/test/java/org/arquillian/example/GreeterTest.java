package org.arquillian.example;

import java.io.File;

import javax.inject.Inject;
 






import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.ScopeType;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class GreeterTest {
	
	private static final String WEBAPP_SRC = "src/main/webapp";  
     
    @Inject
//    Tmp greeter;
     
    @Deployment
    public static WebArchive createDeployment() {
        File[] lib = Maven
        		.resolver()
        		.loadPomFromFile("pom.xml")
        		.importDependencies(ScopeType.COMPILE)
                .resolve()
                .withTransitivity().as(File.class);
         
        WebArchive war =  ShrinkWrap
        		.create(WebArchive.class, "test.war")
        		.addPackages(true, "pl.kwi")
        		.addAsManifestResource("arquillian.xml")
        		.addAsLibraries(lib)
        		.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        
        war.merge(ShrinkWrap.create(GenericArchive.class)
        		.as(ExplodedImporter.class)  
        	    .importDirectory(WEBAPP_SRC)
        	    .as(GenericArchive.class),  
        	    "/", Filters.includeAll());  
         
        System.out.println(war.toString(true));
         
        return war;
    }
     
    @Test
    public void should_create_greeting() {
    	
    	System.out.println("---HERE");
        
    }
}
