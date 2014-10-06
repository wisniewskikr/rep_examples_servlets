package pl.kwi.db.jpa;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.persistence.EntityManager;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;

public class DbUnitUtil {
	
	/**
	 * Method fills data base with data from data file with specified path.
	 * 
	 * @param path object <code>String</code> with path to data file
	 * @param sessionFactory object <code>SessionFactory</code> with data base informations
	 */
	@SuppressWarnings("deprecation")
	public static void executeDataFile(String path, EntityManager em) {

		try {			
			
			Session session = em.unwrap(Session.class);
			SessionFactoryImplementor sfi = (SessionFactoryImplementor) session.getSessionFactory();
			ConnectionProvider cp = sfi.getConnectionProvider();
			Connection conn = cp.getConnection();
			
			IDatabaseConnection iDbConn = new DatabaseConnection(conn);
			
			FlatXmlDataSet dataSet = new FlatXmlDataSetBuilder().
					build(DbUnitUtil.class.getResourceAsStream(path));

			
			DatabaseOperation.CLEAN_INSERT.execute(iDbConn, dataSet);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Method fills data base with data from data file with specified path.
	 * 
	 * @param path object <code>String</code> with path to file with data
	 * @param driverClassName object <code>String</code> with driver class name
	 * @param url object <code>String</code> with url to data base
	 * @param user object <code>String</code> with data base user
	 * @param password object <code>String</code> with data base password
	 */
	public static void executeDataFile(String path, String driverClassName, String url, String user, String password) {

		try {
			
			Class.forName(driverClassName);
			Connection conn = DriverManager.getConnection(url, user, password);	
			IDatabaseConnection iDbConn = new DatabaseConnection(conn);
			
			FlatXmlDataSet dataSet = new FlatXmlDataSetBuilder().
					build(DbUnitUtil.class.getResourceAsStream(path));

			
			DatabaseOperation.CLEAN_INSERT.execute(iDbConn, dataSet);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Method clears table from data.
	 * 
	 * @param path object <code>String</code> with path to file with data
	 * @param driverClassName object <code>String</code> with driver class name
	 * @param url object <code>String</code> with url to data base
	 * @param user object <code>String</code> with data base user
	 * @param password object <code>String</code> with data base password
	 */
	public static void clearDataFile(String path, String driverClassName, String url, String user, String password) {

		try {
			
			Class.forName(driverClassName);
			Connection conn = DriverManager.getConnection(url, user, password);	
			IDatabaseConnection iDbConn = new DatabaseConnection(conn);
			
			FlatXmlDataSet dataSet = new FlatXmlDataSetBuilder().
					build(DbUnitUtil.class.getResourceAsStream(path));
			
			DatabaseOperation.DELETE.execute(iDbConn, dataSet);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	

}
