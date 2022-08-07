//implementation of User DAO interface (2 methods)
package io.github.miwlodar.dao;

import io.github.miwlodar.entity.Users;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Users findByUserName(String userName) {

		Session currentSession = entityManager.unwrap(Session.class);

		Query<Users> query = currentSession.createQuery("from Users where userName=:uName", Users.class);
		query.setParameter("uName", userName);
		Users user = null;
		try {
			user = query.getSingleResult();
		} catch (Exception e) {
			user = null;
		}

		return user;
	}

	@Override
	public void save(Users user) {

		Session currentSession = entityManager.unwrap(Session.class);

		currentSession.saveOrUpdate(user);
	}
}
