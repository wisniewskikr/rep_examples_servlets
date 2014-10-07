package pl.kwi.db.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.commons.lang3.StringUtils;

public class JpaUtil {
	
	public static EntityManager createEntityManager(){
		String persistenceUnitName = System.getProperty("test.db.persistence-unit.name");
		if(StringUtils.isBlank(persistenceUnitName)){
			persistenceUnitName = System.getProperty("main.db.unit.persistence-unit.name");
		}
		return Persistence.createEntityManagerFactory(persistenceUnitName).createEntityManager();		
	}
	
	public static EntityManager createEntityManager(String persistenceUnitName){		
		return Persistence.createEntityManagerFactory(persistenceUnitName).createEntityManager();		
	}
	
	public static void closeEntityManager(EntityManager em){
		em.close();
	}
	
	public static EntityTransaction beginTransaction(EntityManager em){
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		return transaction;
	}
	
	public static void commitTransaction(EntityTransaction transaction){
		transaction.commit();
	}

}
