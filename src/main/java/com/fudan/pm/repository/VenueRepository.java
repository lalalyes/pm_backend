package com.fudan.pm.repository;

import com.fudan.pm.domain.Venue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
/**
 * @author lanping
 */
@Repository
public interface VenueRepository extends CrudRepository<Venue, Long>{
   // Venue findByVenue_id(int venue_id);
}
