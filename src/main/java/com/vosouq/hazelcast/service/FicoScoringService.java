package com.vosouq.hazelcast.service;

import com.vosouq.hazelcast.cache.embedded.EmbeddedCacheInObjectConfig;
import com.vosouq.hazelcast.cache.member.MemberCacheBinaryManager;
import com.vosouq.hazelcast.model.CacheFicoScoringLocalStatus;
import com.vosouq.hazelcast.model.CachePersonLocalStatus;
import com.vosouq.hazelcast.model.FicoScoring;
import com.vosouq.hazelcast.model.Message;
import com.vosouq.hazelcast.repository.FicoScoringRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = EmbeddedCacheInObjectConfig.FICO_SCORING)
public class FicoScoringService {

    private final FicoScoringRepository ficoScoringRepository;
    private final MemberCacheBinaryManager memberCacheBinaryManager;
    private final CachePersonLocalStatus cachePersonLocalStatus;
    private final Message message;
    private CacheFicoScoringLocalStatus cacheFicoScoringLocalStatus;

    public FicoScoringService(FicoScoringRepository ficoScoringRepository, MemberCacheBinaryManager memberCacheBinaryManager, CachePersonLocalStatus cachePersonLocalStatus, CacheFicoScoringLocalStatus cacheFicoScoringLocalStatus, Message message) {
        this.ficoScoringRepository = ficoScoringRepository;
        this.memberCacheBinaryManager = memberCacheBinaryManager;
        this.cachePersonLocalStatus = cachePersonLocalStatus;
        this.cacheFicoScoringLocalStatus = cacheFicoScoringLocalStatus;
        this.message = message;
    }

    // @Cacheable(key = "#root.method.name")
    @Cacheable(cacheNames = "findAll")
    public List<FicoScoring> findAll() {
        if (memberCacheBinaryManager.isFicoScoringMapNotEmpty()) {
            cacheFicoScoringLocalStatus.cacheHit();
            return memberCacheBinaryManager.getFicoScoringList();
        }

        message.setValue("<h5 style='color:red'>Db Call: getFicoScoringList() ....</h5>");
        List<FicoScoring> ficoScoringList = (List<FicoScoring>) ficoScoringRepository.findAll();
        memberCacheBinaryManager.putFicoScoringList(ficoScoringList);
        cacheFicoScoringLocalStatus.cacheHit();
        return ficoScoringList;
    }

    @Cacheable(key = "#personId-#ficoScoringId")
    public FicoScoring findById(Long ficoScoringId, Long personId) {
        FicoScoring ficoScoring;
        ficoScoring = memberCacheBinaryManager.getFicoScoringById(ficoScoringId);
        if (ficoScoring != null)
            return ficoScoring;

        message.setValue("<h5 style='color:red'>Db Call: getFicoScoringFindById() ....</h5>");
        Optional<FicoScoring> optional = ficoScoringRepository.findById(ficoScoringId);
        if (optional.isPresent()) {
            optional.get();
            memberCacheBinaryManager.putFicoScoring(optional.get());
        }

        return optional.orElseGet(FicoScoring::new);
    }

    @CachePut(key = "#ficoScoring.id")
    public FicoScoring ficoScoringInsert(FicoScoring ficoScoring) {
        message.setValue("<h5 style='color:red'>Db Call: personInsert() ....</h5>");
        ficoScoring = ficoScoringRepository.save(ficoScoring);
        memberCacheBinaryManager.putFicoScoring(ficoScoring);
        cacheFicoScoringLocalStatus.cacheMiss();
        cachePersonLocalStatus.cacheMiss();
        return ficoScoring;
    }

    @CacheEvict(cacheNames = "findAll")
    public void cacheEvict() {
        memberCacheBinaryManager.putCacheChangeFlag(1);
        System.out.println("Cache evicted !!");
    }
}
