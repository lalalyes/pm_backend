package com.fudan.pm.repository;

import com.fudan.pm.domain.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    User findByUserId(int user_id);
    User findByWorkNumber(String workNumber);

    @Modifying
    @Query(value = "update user set password = ?2 where user_id=?1",nativeQuery = true)
    void changePassword(int user_id, String password);
}
