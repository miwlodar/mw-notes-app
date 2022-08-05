//interface for Users service - all CRUD methods, implemented in a separate class - in line with a common design pattern
package io.github.miwlodar.service;

import io.github.miwlodar.entity.Users;
import io.github.miwlodar.user.CrmUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

	Users findByUserName(String userName);

	void save(CrmUser crmUser);
}
