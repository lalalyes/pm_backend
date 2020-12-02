package com.fudan.pm.repository;

import com.fudan.pm.domain.Activity;
import com.fudan.pm.domain.ActivityVenue;
import com.fudan.pm.domain.Venue;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author lanping
 */
public interface ActivityVenueRepository extends CrudRepository<ActivityVenue, Long> {
    void deleteByActivityId(int activity_id);
    List<ActivityVenue> findAllByVenue(Venue venue);
}
