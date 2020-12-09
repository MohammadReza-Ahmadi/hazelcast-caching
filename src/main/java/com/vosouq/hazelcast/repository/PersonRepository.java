package com.vosouq.hazelcast.repository;

import com.vosouq.hazelcast.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends CrudRepository<Person,Long> {

    List<Person> findByIdentityCode(String identityCode);
}
