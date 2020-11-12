package com.fudan.pm.repository;

import com.fudan.pm.domain.Activity;
import com.fudan.pm.domain.LaunchActivity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author lanping
 */
public interface LaunchActivityRepository extends CrudRepository<LaunchActivity, Long> {
    List<LaunchActivity> findByUserId(int userId);
}
