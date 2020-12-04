package com.fudan.pm.repository;

import com.fudan.pm.domain.Participate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Repository
public interface ParticipateRepository extends CrudRepository<Participate,Long> {
    List<Participate> findByUserId(int user_id);

    @Query(value = "select * from participate where activity_id=?1 and comment != ''",nativeQuery = true)
    List<Participate> findCommentsByActivityId(int activity_id);
    Participate findByUserIdAndActivityId(int user_id, int activity_id);
}
