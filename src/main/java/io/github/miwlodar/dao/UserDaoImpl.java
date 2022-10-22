//implementation of User DAO interface (2 methods)
package io.github.miwlodar.dao;

import io.github.miwlodar.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

public class UserDaoImpl implements UserDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    public User findByUserName(String userName) {
        Session currentSession = entityManager.unwrap(Session.class);

        Query<User> query = currentSession.createQuery("from User where userName=:uName", User.class);
        query.setParameter("uName", userName);
        User user = null;
        try {
            user = query.getSingleResult();
        } catch (Exception e) {
            user = null;
        }

        return user;
    }

    @Override
    public void save(User user) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.saveOrUpdate(user);
    }
}
