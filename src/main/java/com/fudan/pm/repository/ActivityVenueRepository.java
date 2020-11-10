package com.fudan.pm.repository;

import com.fudan.pm.domain.Activity;
import com.fudan.pm.domain.ActivityVenue;
import org.springframework.data.repository.CrudRepository;

/**
 * @author lanping
 */
public interface ActivityVenueRepository extends CrudRepository<ActivityVenue, Long> {
   // void deleteByActivity(Activity activity);
}
