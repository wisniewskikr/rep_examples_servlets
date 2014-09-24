package pl.kwi.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import pl.kwi.daos.UserDao;
import pl.kwi.db.jpa.JpaUtil;
import pl.kwi.entities.UserEntity;

public class UserService {

	
	private EntityManager em;
	private EntityTransaction transaction;
	
	
	public Long createUser(UserEntity user){
		
		em = JpaUtil.createEntityManager("pu");
		transaction = JpaUtil.beginTransaction(em);
		
		UserDao dao = new UserDao(em);		
		dao.create(user);
		
		JpaUtil.commitTransaction(transaction);
		JpaUtil.closeEntityManager(em);
		
		return user.getId();
		
	}
	
	public UserEntity readUser(Long id){
		
		UserEntity entity;
		
		em = JpaUtil.createEntityManager("pu");
		transaction = JpaUtil.beginTransaction(em);
		
		UserDao dao = new UserDao(em);		
		entity = dao.read(id);
		
		JpaUtil.commitTransaction(transaction);
		JpaUtil.closeEntityManager(em);
		
		return entity;
		
	}
	
	public void updateUser(UserEntity user){
		
		em = JpaUtil.createEntityManager("pu");
		transaction = JpaUtil.beginTransaction(em);
		
		UserDao dao = new UserDao(em);		
		dao.update(user);
		
		JpaUtil.commitTransaction(transaction);
		JpaUtil.closeEntityManager(em);
		
	}
	
	public void deleteUser(UserEntity user){
		
		em = JpaUtil.createEntityManager("pu");
		transaction = JpaUtil.beginTransaction(em);
		
		UserDao dao = new UserDao(em);		
		dao.delete(user);
		
		JpaUtil.commitTransaction(transaction);
		JpaUtil.closeEntityManager(em);
		
	}
	
	public List<UserEntity> getUserList(){
		
		List<UserEntity> list;
		
		em = JpaUtil.createEntityManager("pu");
		transaction = JpaUtil.beginTransaction(em);
		
		UserDao dao = new UserDao(em);
		list = dao.getAllUserList();
		
		JpaUtil.commitTransaction(transaction);
		JpaUtil.closeEntityManager(em);
		
		return list;
		
	}

}
