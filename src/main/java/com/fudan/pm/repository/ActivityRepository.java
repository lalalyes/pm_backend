package com.fudan.pm.repository;


import com.fudan.pm.domain.Activity;

import org.springframework.data.domain.Page;
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
    List<Activity> findAll();
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
