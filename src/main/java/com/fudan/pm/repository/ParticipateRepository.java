package com.fudan.pm.repository;

import com.fudan.pm.domain.Participate;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Repository
public interface ParticipateRepository extends CrudRepository<Participate,Long> {
    List<Participate> findByUserId(int user_id);

    List<Participate> findByActivityId(int activity_id);

    @Query(value = "select count(*) from participate where activity_id=?1",nativeQuery = true)
    int getActivityCurrentNumber(int activity_id);

    @Query(value = "select count(*) from participate where activity_id=?1 and present=1",nativeQuery = true)
    int getActivityCheckedNumber(int activity_id);

    @Query(value = "select * from participate where activity_id=?1 and comment != ''",nativeQuery = true)
    List<Participate> findCommentsByActivityId(int activity_id);
    Participate findByUserIdAndActivityId(int user_id, int activity_id);

    @Modifying
    @Query(value = "update participate set present = 1 where user_id=?1 and activity_id=?2",nativeQuery = true)
    void updatePresent(int user_id, int activity_id);

    @Modifying
    @Query(value = "update participate set score=?3, comment=?4 where user_id=?1 and activity_id=?2",nativeQuery = true)
    void updateComment(int user_id, int activity_id, int score, String comment);


    @Modifying
    @Query(value = "delete from participate where activity_id=?1",nativeQuery = true)
    void deleteByActivityId(int activity_id);
}
