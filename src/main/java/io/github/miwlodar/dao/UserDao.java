//DAO interface for handling methods on users
package io.github.miwlodar.dao;

import io.github.miwlodar.entity.Users;

public interface UserDao {

    Users findByUserName(String userName);
    
    void save(Users user);
}
