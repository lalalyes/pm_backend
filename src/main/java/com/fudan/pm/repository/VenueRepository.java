package com.fudan.pm.repository;

import com.fudan.pm.domain.Venue;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lanping
 */
@Repository
public interface VenueRepository extends CrudRepository<Venue, Long>{
    @Query(value = "select * from venue where venue_name like CONCAT('%',?1,'%')",nativeQuery = true)
    List<Venue> findByVenueName(String name);

    Venue findByVenueId(int venueId);
   // Venue findByVenue_id(int venue_id);
}
