package com.fudan.pm.repository;

import com.fudan.pm.domain.Participate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Repository
public interface ParticipateRepository extends CrudRepository<Participate,Long> {
    List<Participate> findByUserId(int user_id);
}
