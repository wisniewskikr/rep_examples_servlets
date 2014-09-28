package pl.kwi.intg;

import java.io.File;

import org.jboss.shrinkwrap.api.spec.WebArchive;

public final class IntgTestUtils {
	
	private IntgTestUtils() {}
	
	/**
	 * Method adds subfiles from specified file to war file.
	 * 
	 * @param war object WarArchive with war file
	 * @param dir object File with subfiles which should be added to war file
	 */
	public static void addFilesToWar(WebArchive war, File dir) {
		
		try {
			
			if (!dir.isDirectory()) {
	            throw new Exception("not a directory");
	        }
	        for (File f : dir.listFiles()) {
	            if (f.isFile()) {
	                war.addAsWebResource(f, f.getPath().replace("\\", "/").substring("src/main/webapp/".length()));
	            } else {
	                addFilesToWar(war, f);
	            }
	        }
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
        
    }

}
