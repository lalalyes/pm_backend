package com.fudan.pm.repository;

import com.fudan.pm.domain.User;
import com.fudan.pm.domain.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Long> {
    void deleteAllByUser(User user);

}
