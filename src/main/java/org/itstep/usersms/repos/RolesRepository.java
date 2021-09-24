package org.itstep.usersms.repos;

import org.itstep.usersms.models.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Role, Long> {
}
