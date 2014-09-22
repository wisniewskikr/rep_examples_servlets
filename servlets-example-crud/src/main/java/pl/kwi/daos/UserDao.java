package pl.kwi.daos;

import java.util.List;

import javax.persistence.EntityManager;
import pl.kwi.db.jpa.AbstractDao;
import pl.kwi.entities.UserEntity;

public class UserDao extends AbstractDao<UserEntity> {
	
	
	public UserDao(EntityManager em){
		super(em);	
		setClazz(UserEntity.class);
	}
	

	public List<UserEntity> getAllUserList(){
		
		return (List<UserEntity>) readAll();
		
	}
	
}
