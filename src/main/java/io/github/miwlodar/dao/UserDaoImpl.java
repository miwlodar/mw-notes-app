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
	public Users findByUserName(String theUserName) {

		Session currentSession = entityManager.unwrap(Session.class);

		Query<Users> theQuery = currentSession.createQuery("from Users where userName=:uName", Users.class);
		theQuery.setParameter("uName", theUserName);
		Users theUser = null;
		try {
			theUser = theQuery.getSingleResult();
		} catch (Exception e) {
			theUser = null;
		}

		return theUser;
	}

	@Override
	public void save(Users theUser) {

		Session currentSession = entityManager.unwrap(Session.class);

		currentSession.saveOrUpdate(theUser);
	}
}