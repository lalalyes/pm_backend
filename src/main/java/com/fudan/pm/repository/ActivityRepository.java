package com.fudan.pm.repository;


import com.fudan.pm.domain.Activity;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author lanping
 */
@Repository
public interface ActivityRepository extends CrudRepository<Activity, Long> {
    Activity findByActivityId(int activity_id);

    @Query(value = "select * from activity where unix_timestamp(sign_up_start_time) < unix_timestamp(NOW()) and unix_timestamp(sign_up_end_time) > unix_timestamp(NOW()) and activity_id not in (select activity_id from participate where user_id = ?1)",nativeQuery = true)
    List<Activity> findAll(int id);

    @Query(value = "select * from activity where activity_name like CONCAT('%',?1,'%')",nativeQuery = true)
    List<Activity> findByActivityName(String name);
//    List<Activity> findAll(new Specification<Activity>() {
//        @Override
//        public Predicate toPredicate(Root<Activity> transaction,
//                CriteriaQuery<?> q, CriteriaBuilder cb) {
//            Predicate between = cb.between(Activity.get(Activity_.dateSubmit), startDate, endDate);
//
//            return between;
//        }
//    },new Sort(Direction.DESC,"dateSubmit"));;

    //List<Activity> findAll();
}
