//interface using JpaRepository and adding a couple of useful methods used in the service implementation
package io.github.miwlodar.db;

import io.github.miwlodar.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

}
