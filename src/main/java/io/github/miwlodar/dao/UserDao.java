//DAO interface for handling methods on users
package io.github.miwlodar.dao;

import io.github.miwlodar.entity.User;

public interface UserDao {

    User findByUserName(String userName);

    void save(User user);
}
