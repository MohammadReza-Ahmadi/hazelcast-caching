package com.vosouq.hazelcast.service;

import com.vosouq.hazelcast.cache.embedded.EmbeddedCacheInObjectConfig;
import com.vosouq.hazelcast.cache.member.MemberCacheBinaryManager;
import com.vosouq.hazelcast.model.CachePersonLocalStatus;
import com.vosouq.hazelcast.model.Message;
import com.vosouq.hazelcast.model.Person;
import com.vosouq.hazelcast.repository.PersonRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

import static com.vosouq.hazelcast.util.AppConstant.ZERO_INT;

@Service
@CacheConfig(cacheNames = EmbeddedCacheInObjectConfig.PERSONS)
public class PersonService {

    private final PersonRepository personRepository;
    private final MemberCacheBinaryManager memberCacheBinaryManager;
    private final CachePersonLocalStatus cachePersonLocalStatus;
    private final Message message;


    public PersonService(PersonRepository personRepository, MemberCacheBinaryManager memberCacheBinaryManager, CachePersonLocalStatus cachePersonLocalStatus, Message message) {
        this.personRepository = personRepository;
        this.memberCacheBinaryManager = memberCacheBinaryManager;
        this.cachePersonLocalStatus = cachePersonLocalStatus;
        this.message = message;
    }

    @PostConstruct
    private void init() {
        creatBulkPersonRecords();
    }


    /**
     * model-1 read from db first and write to cache
     */
    @Cacheable(cacheNames = "findAll", key = "#root.method.name")
    public List<Person> findAll() {
        message.setValue("<h5 style='color:red'>Db Call: getPersonList() ....</h5>");
        List<Person> personList = (List<Person>) personRepository.findAll();
        if (memberCacheBinaryManager.isPersonMapEmpty())
            memberCacheBinaryManager.putPersonList(personList);
        cachePersonLocalStatus.cacheHit();
        return personList;
    }

    /**
     * model-2 Read-Through Cache: firstly, read from cache and if cache is empty then read from db
     */
    @Cacheable(cacheNames = "findAllReadThroughCache")
    public List<Person> findAllReadThroughCache() {
        if (memberCacheBinaryManager.isPersonMapNotEmpty()) {
            cachePersonLocalStatus.cacheHit();
            return memberCacheBinaryManager.getPersonList();
        }

        List<Person> personList = (List<Person>) personRepository.findAll();
        message.setValue("<h5 style='color:red'>Db Call: getPersonList() ....</h5>");
        memberCacheBinaryManager.putPersonList(personList);
        cachePersonLocalStatus.cacheHit();
        return personList;
    }

    @Cacheable(cacheNames = "findByIdentityCode", key = "#identityCode")
    public Person findByIdentityCode(String identityCode) {
        Person person;
        List<Person> personList;
        person = memberCacheBinaryManager.getPersonByIdentityCode(identityCode);
        if (person != null)
            return person;

        personList = personRepository.findByIdentityCode(identityCode);
        message.setValue("<h5 style='color:red'>Db Call: personFind() ....</h5>");
        if (!personList.isEmpty()) {
            person = personList.get(ZERO_INT);
            memberCacheBinaryManager.putPerson(person);
            return person;
        }

        return new Person();
    }

    @CachePut(cacheNames = "personInsert", key = "#person.id")
    public Person personInsert(Person person) {
        person = personRepository.save(person);
        memberCacheBinaryManager.putPerson(person);
        message.setValue("<h5 style='color:red'>Db Call: personInsert() ....</h5>");
        cachePersonLocalStatus.cacheMiss();
        return person;
    }

    @CacheEvict(cacheNames = "findAllReadThroughCache")
    public void cacheEvict() {
        memberCacheBinaryManager.putCacheChangeFlag(1);
        System.out.println("Cache evicted !!");
    }

    private void creatBulkPersonRecords() {
        for (long i = 2; i <= 5; i++) {
            Person person = new Person();
            person.setFirstName("first-name-" + i);
            person.setLastName("last-name-" + i);
            person.setAccountNumber("acc-number-" + i);
            person.setAddress("address-" + i);

            if (i % 10 < 9) {
                person.setIdentityCode(i + "" + (i + 3) + "" + (i + 4) + "" + (i + 2) + "" + (i + 3) + "" + (i + 1) + "" + (i + 1) + "" + (i + 1) + "" + (i + 1) + "" + (i + 1));
                person.setMobile("0912" + i + "" + (i + 2) + "" + (i + 3) + "" + i + "" + (i + 6) + "" + (i + 1) + "" + (i + 1) + "" + (i + 1));
            } else {
                person.setIdentityCode(i + "" + (i + 6) + "" + (i + 2) + "" + (i + 4) + "" + i + "" + (i + 1));
                person.setMobile("0910" + i + "" + i + "" + (i + 4) + "" + i + "" + (i + 5) + "" + (i + 6) + "" + i);
            }

            if (person.getIdentityCode().length() > 10)
                person.setIdentityCode(person.getIdentityCode().substring(0, 9));

            if (person.getMobile().length() > 11)
                person.setMobile(person.getMobile().substring(0, 10));

            personRepository.save(person);
        }

    }
}

