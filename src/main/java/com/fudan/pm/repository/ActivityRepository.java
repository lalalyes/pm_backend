package com.fudan.pm.repository;


import com.fudan.pm.domain.Activity;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author lanping
 */
@Repository
public interface ActivityRepository extends CrudRepository<Activity, Long> {
    Activity findByActivityId(int activity_id);

    @Query(value = "select * from activity where unix_timestamp(sign_up_start_time) < unix_timestamp(NOW()) and unix_timestamp(sign_up_end_time) > unix_timestamp(NOW()) and activity_id not in (select activity_id from participate where user_id = ?1)",nativeQuery = true)
    List<Activity> findAll(int id);

    @Query(value = "select * from activity where activity_name like CONCAT('%',?1,'%')",nativeQuery = true)
    List<Activity> findByActivityName(String name);

    @Modifying
    @Query(value = "update activity set launch_time = ?1 where activity_id=?2",nativeQuery = true)
    void updateLaunch(Date launch_time, int activity_id);

    void deleteByActivityId(int activityId);

    @Modifying
    @Query(value = "update activity set activity_name=?1, introduction=?2, type=?3, picture=?4, capacity=?5, activity_start_time=?6, activity_end_time=?7, sign_up_start_time=?8, sign_up_end_time=?9, create_time=?10 where activity_id=?11",nativeQuery = true)
    void updateActivity(String param1, String param2, String param3, String param4, int param5, Date param6, Date param7, Date param8, Date param9, Date param10, int param11);
}
