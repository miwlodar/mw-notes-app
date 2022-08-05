//implementation of Role DAO interface (1 method)
package io.github.miwlodar.dao;

import io.github.miwlodar.entity.Roles;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;

@Repository
public class RoleDaoImpl implements RoleDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Roles findRoleByName(String theRoleName) {

		Session currentSession = entityManager.unwrap(Session.class);

		Query<Roles> theQuery = currentSession.createQuery("from Roles where name=:roleName", Roles.class);
		theQuery.setParameter("roleName", theRoleName);
		
		Roles theRole = null;
		
		try {
			theRole = theQuery.getSingleResult();
		} catch (Exception e) {
			theRole = null;
		}
		
		return theRole;
	}
}
