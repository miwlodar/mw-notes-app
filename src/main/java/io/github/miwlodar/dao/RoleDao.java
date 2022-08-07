//DAO interface for handling roles (admin/user)
package io.github.miwlodar.dao;

import io.github.miwlodar.entity.Roles;

public interface RoleDao {
	Roles findRoleByName(String roleName);
}
