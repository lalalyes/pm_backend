package com.fudan.pm.repository;


import com.fudan.pm.domain.Activity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author lanping
 */
@Repository
public interface ActivityRepository extends CrudRepository<Activity, Long> {
    Activity findByActivityId(int activity_id);
}
