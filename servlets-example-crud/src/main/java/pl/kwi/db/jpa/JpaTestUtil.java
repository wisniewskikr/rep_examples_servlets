package pl.kwi.db.jpa;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;


public class JpaTestUtil {

	
	public static EntityManager beginEntityManager(){
		
		try {
			
			String persistenceUnitName = System.getProperty("test.db.persistence-unit.name");
			return Persistence.createEntityManagerFactory(persistenceUnitName).createEntityManager();
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static void finishEntityManager(EntityManager em){
		
		try {
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static EntityTransaction beginTransaction(EntityManager em){
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		return transaction;
	}
	
	public static void commitTransaction(EntityTransaction transaction){
		transaction.commit();
	}
	
	public static void executeDataFile(String path){
		
		try {
			
			String dbDriver = System.getProperty("test.db.driver");
			String dbUrl = System.getProperty("test.db.url");
			String dbUser = System.getProperty("test.db.username");
			String dbPassword = System.getProperty("test.db.password");
			
			Class.forName(dbDriver).newInstance();
			Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			
			IDatabaseConnection iConn = new DatabaseConnection(conn);
			
			IDataSet dataSet = new FlatXmlDataSet(JpaTestUtil.class
					.getResourceAsStream(path));

			DatabaseOperation.CLEAN_INSERT.execute(iConn, dataSet);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
