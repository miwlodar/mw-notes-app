//DAO interface for handling roles (admin/user)
package io.github.miwlodar.dao;

import io.github.miwlodar.entity.Role;

public interface RoleDao {
	Role findRoleByName(String roleName);
}
