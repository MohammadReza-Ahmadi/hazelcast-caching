package com.vosouq.hazelcast.repository;

import com.vosouq.hazelcast.model.FicoScoring;
import com.vosouq.hazelcast.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FicoScoringRepository extends CrudRepository<FicoScoring,Long> {


}
