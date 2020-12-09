package com.vosouq.hazelcast.cache.member;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.Predicates;
import com.vosouq.hazelcast.model.FicoScoring;
import com.vosouq.hazelcast.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.vosouq.hazelcast.util.AppConstant.*;

@Service
public class MemberCacheBinaryManager {

    private HazelcastInstance memberHazelcastInstance;
    private IMap<Long, Person> personIMap;
    private IMap<Long, FicoScoring> ficoScoringIMap;

    public MemberCacheBinaryManager(HazelcastInstance memberHazelcastInstance) {
        this.memberHazelcastInstance = memberHazelcastInstance;
        memberHazelcastInstance.getMap(CACHE_STATUS).put(FLAG, ZERO_INT);
        personIMap = memberHazelcastInstance.getMap(PERSONS);
        ficoScoringIMap = memberHazelcastInstance.getMap(FICO_SCORING);
    }

    public boolean isCacheChanged() {
        return (int) memberHazelcastInstance.getMap(CACHE_STATUS).get(FLAG) == 1;
    }

    public void putCacheChangeFlag(int flag) {
        memberHazelcastInstance.getMap(CACHE_STATUS).put(FLAG, flag);
    }

    public boolean isPersonMapEmpty() {
        return personIMap.isEmpty();
    }

    public boolean isPersonMapNotEmpty() {
        return !personIMap.isEmpty();
    }

    public boolean isFicoScoringMapEmpty() {
        return ficoScoringIMap.isEmpty();
    }

    public boolean isFicoScoringMapNotEmpty() {
        return !ficoScoringIMap.isEmpty();
    }

    public void putPersonList(List<Person> personList) {
        putPersonList(personList, true);
    }

    public void putPersonList(List<Person> personList, boolean overwrite) {
        if (overwrite) {
            personIMap.clear();
            ficoScoringIMap.clear();
        }

        for (Person person : personList) {
            personIMap.put(person.getId(), person);
            if (person.getFicoScoring() != null && person.getFicoScoring().getId() != null)
                ficoScoringIMap.put(person.getFicoScoring().getId(), person.getFicoScoring());
        }
    }

    public void putFicoScoringList(List<FicoScoring> ficoScoringList) {
        putFicoScoringList(ficoScoringList, true);
    }

    public void putFicoScoringList(List<FicoScoring> ficoScoringList, boolean overwrite) {
        if (overwrite)
            ficoScoringIMap.clear();
        for (FicoScoring ficoScoring : ficoScoringList) {
            ficoScoringIMap.put(ficoScoring.getId(), ficoScoring);
        }
    }

    public Person getPersonById(Long personId) {
        return personIMap.get(personId);
    }

    public FicoScoring getFicoScoringById(Long ficoScoringId) {
        return ficoScoringIMap.get(ficoScoringId);
    }

    public List<Person> getPersonByLastName(String lastName) {
        Predicate<Long, Person> predicate = Predicates.like("lastName", lastName);
        return (List<Person>) personIMap.values(predicate);
    }

    public Person getPersonByIdentityCode(String identityCode) {
        Predicate<Long, Person> predicate = Predicates.equal("identityCode", identityCode);
        List<Person> personList = (List<Person>) personIMap.values(predicate);
        if (!personList.isEmpty())
            return personList.get(ZERO_INT);
        return null;
    }

/*    public List<FicoScoring> getFicoScoringListByPaymentHistoryRange(Float min,Float max) {
        Predicate<Long, FicoScoring> predicate = Predicates.between()
        List<Person> personList = (List<Person>) personIMap.values(predicate);
        if (!personList.isEmpty())
            return personList.get(ZERO_INT);
        return null;
    }*/

    public List<Person> getPersonList() {
        return new ArrayList(personIMap.values());
    }

    public List<FicoScoring> getFicoScoringList() {
        return new ArrayList(ficoScoringIMap.values());
    }

    public Person putPerson(Person person) {
        return personIMap.put(person.getId(), person);
    }

    public FicoScoring putFicoScoring(FicoScoring ficoScoring) {
        Person person = getPersonById(ficoScoring.getPerson().getId());
        person.setFicoScoring(ficoScoring);
        putPerson(person);
        return ficoScoringIMap.put(ficoScoring.getId(), ficoScoring);
    }

    public Person removePerson(Long personId) {
        return personIMap.remove(personId);
    }

    public FicoScoring removeFicoScoring(Long ficoScoring) {
        return ficoScoringIMap.remove(ficoScoring);
    }

    public Person removePerson(Person person) {
        return personIMap.remove(person.getId());
    }

    public FicoScoring removeFicoScoring(FicoScoring ficoScoring) {
        return ficoScoringIMap.remove(ficoScoring.getId());
    }

}
