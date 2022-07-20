package io.github.miwlodar.dao;

import io.github.miwlodar.entity.Roles;

public interface RoleDao {

	public Roles findRoleByName(String theRoleName);
	
}
