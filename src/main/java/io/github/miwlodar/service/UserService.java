//interface for Users service - all CRUD methods, implemented in a separate class - in line with a common design pattern
package io.github.miwlodar.service;

import io.github.miwlodar.entity.User;
import io.github.miwlodar.user.CreateUserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

	User findByUserName(String userName);

	void save(CreateUserDto createUserDto);
}
