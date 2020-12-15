package com.fudan.pm.repository;

import com.fudan.pm.domain.Activity;
import com.fudan.pm.domain.ActivityVenue;
import com.fudan.pm.domain.Venue;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author lanping
 */
public interface ActivityVenueRepository extends CrudRepository<ActivityVenue, Long> {
    @Modifying
    @Query(value = "delete from activityVenue where activity_id=?1",nativeQuery = true)
    void deleteByActivityId(int activity_id);

    @Modifying
    @Query(value = "update activityVenue set venue_id = ?1 where activity_id=?2",nativeQuery = true)
    void updateAV(int venue_id, int activity_id);

    ActivityVenue findByActivityId(int activity_id);
    List<ActivityVenue> findByVenueId(int venue_id);
}
