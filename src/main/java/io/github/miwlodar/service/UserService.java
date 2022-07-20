package io.github.miwlodar.service;

import io.github.miwlodar.entity.Users;
import io.github.miwlodar.user.CrmUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

	Users findByUserName(String userName);

	void save(CrmUser crmUser);
}
