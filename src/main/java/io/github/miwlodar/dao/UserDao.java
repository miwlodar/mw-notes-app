//DAO interface for handling methods on users
package io.github.miwlodar.dao;

import io.github.miwlodar.entity.Users;

public interface UserDao {

    public Users findByUserName(String userName);
    
    public void save(Users user);
}
