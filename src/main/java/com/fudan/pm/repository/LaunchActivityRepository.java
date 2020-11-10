package com.fudan.pm.repository;

import com.fudan.pm.domain.Activity;
import com.fudan.pm.domain.LaunchActivity;
import org.springframework.data.repository.CrudRepository;

/**
 * @author lanping
 */
public interface LaunchActivityRepository extends CrudRepository<LaunchActivity, Long> {
    LaunchActivity findByUserId(int userId);
}
