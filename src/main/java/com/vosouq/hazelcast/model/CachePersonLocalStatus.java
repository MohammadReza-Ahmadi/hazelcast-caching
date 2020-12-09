package com.vosouq.hazelcast.model;

/*
 Author: MohammadReza Ahmadi,  "m.reza79ahmadi@gmail.com"
 8/5/2020, 9:46 AM
*/

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class CachePersonLocalStatus {
    private CacheStatusEnum status;

    public boolean isCacheHit() {
        return status != null && status.equals(CacheStatusEnum.CACHE_HIT);
    }

    public boolean isCacheMiss() {
        return status == null || status.equals(CacheStatusEnum.CACHE_MISS);
    }

    public void cacheMiss() {
        status = CacheStatusEnum.CACHE_MISS;
    }

    public void cacheHit() {
        status = CacheStatusEnum.CACHE_HIT;
    }

    public enum CacheStatusEnum {
        CACHE_HIT, CACHE_MISS;
    }
}
