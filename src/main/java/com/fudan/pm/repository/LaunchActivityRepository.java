package com.fudan.pm.repository;

import com.fudan.pm.domain.Activity;
import com.fudan.pm.domain.LaunchActivity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author lanping
 */
public interface LaunchActivityRepository extends CrudRepository<LaunchActivity, Long> {
    List<LaunchActivity> findByUserId(int userId);
    LaunchActivity findByActivityId(int activity_id);
    LaunchActivity findByActivityIdAndUserId(int activity_id, int user_id);

    @Modifying
    @Query(value = "delete from launchActivity where activity_id=?1",nativeQuery = true)
    void deleteByActivityId(int activityId);
}
