//interface using JpaRepository and adding a couple of useful methods used in the service implementation
package io.github.miwlodar.dao;

import io.github.miwlodar.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String userName);

}
