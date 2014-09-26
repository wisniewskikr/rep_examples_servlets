package pl.kwi.daos;

import static junit.framework.Assert.assertNotNull;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import pl.kwi.daos.UserDao;
import pl.kwi.db.jpa.JpaTestUtil;
import pl.kwi.entities.UserEntity;

public class UserDaoTest {

	private static EntityManager em;
	private static UserDao dao;
	
	@Before
	public void setUp() throws Exception{
		em = JpaTestUtil.beginEntityManager();
		dao = new UserDao(em);
	}
	
	@After
	public void after(){
		JpaTestUtil.finishEntityManager(em);
	}

	@Test
	public void create() {

		EntityTransaction tx = JpaTestUtil.beginTransaction(em);		
		JpaTestUtil.executeDataFile("/dbunit/input.xml");
				
		UserEntity entity = new UserEntity();
		entity.setName("Name4");
		dao.create(entity);
		
		JpaTestUtil.commitTransaction(tx);
		
		assertNotNull(entity.getId());

	}

	@Test
	public void read() {
		
		EntityTransaction tx = JpaTestUtil.beginTransaction(em);		
		JpaTestUtil.executeDataFile("/dbunit/input.xml");
		
		UserEntity entity = dao.read(1L);
		
		JpaTestUtil.commitTransaction(tx);
		
		Assert.assertEquals("Name1", entity.getName());

	}

	@Test
	public void update() {

		EntityTransaction tx = JpaTestUtil.beginTransaction(em);
		JpaTestUtil.executeDataFile("/dbunit/input.xml");

		UserEntity entity = dao.read(1L);
		entity.setName("Name44");
		dao.update(entity);
		entity = dao.read(1L);
		
		JpaTestUtil.commitTransaction(tx);

		Assert.assertEquals("Name44", entity.getName());

	}

	@Test
	public void delete() {
		
		EntityTransaction tx = JpaTestUtil.beginTransaction(em);
		JpaTestUtil.executeDataFile("/dbunit/input.xml");
		
		UserEntity entity = dao.read(1L);
		dao.delete(entity);
		entity = dao.read(1L);
		
		JpaTestUtil.commitTransaction(tx);
		
		Assert.assertNull(entity);

	}
	
	@Test
	public void getAllUserList(){
		
		EntityTransaction tx = JpaTestUtil.beginTransaction(em);
		JpaTestUtil.executeDataFile("/dbunit/input.xml");
		
		List<UserEntity> list = dao.getAllUserList();
		
		JpaTestUtil.commitTransaction(tx);
		
		Assert.assertEquals(3, list.size());
		
	}

}
