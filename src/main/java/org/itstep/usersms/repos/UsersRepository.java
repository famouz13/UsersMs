package org.itstep.usersms.repos;

import org.itstep.usersms.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
