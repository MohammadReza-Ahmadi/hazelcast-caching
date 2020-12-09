package com.vosouq.hazelcast.cache.client;

import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static com.vosouq.hazelcast.util.AppConstant.*;

//@Configuration
public class ClientCacheBinaryConfig {

//    @Bean
    public HazelcastInstance clientHazelcastInstance(Config config) {
        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(config);
        hazelcastInstance.getMap(CACHE_STATUS).put(FLAG, ZERO_INT);
        return hazelcastInstance;
    }

//    @Bean
    public Config memberHazelcastConfig() {
        /**create NetworkConfig */
        NetworkConfig networkConfig = new NetworkConfig();
//        networkConfig.setPort(5701);
        JoinConfig joinConfig = new JoinConfig();
        joinConfig.getMulticastConfig().setEnabled(true);
        networkConfig.setJoin(joinConfig);

        /**create MapConfig*/
        MapConfig personMapConfig = new MapConfig();
        personMapConfig.setName("personMapConfig");
        personMapConfig.setInMemoryFormat(InMemoryFormat.BINARY);
        personMapConfig.setTimeToLiveSeconds(0);

        /**create change MapConfig*/
        MapConfig cacheStatusMapConfig = new MapConfig();
        cacheStatusMapConfig.setName(CACHE_STATUS);
        cacheStatusMapConfig.setInMemoryFormat(InMemoryFormat.BINARY);
        cacheStatusMapConfig.setTimeToLiveSeconds(0);

        /**create EvictionConfig*/
        EvictionConfig evictionConfig = new EvictionConfig();
//        evictionConfig.setEvictionPolicy(EvictionPolicy.LRU);
        evictionConfig.setMaxSizePolicy(MaxSizePolicy.FREE_HEAP_SIZE);
        personMapConfig.setEvictionConfig(evictionConfig);

        /** create mapConfigMap*/
        Map<String, MapConfig> mapConfigMap = new HashMap<>();
        mapConfigMap.put(personMapConfig.getName(), personMapConfig);
        mapConfigMap.put(cacheStatusMapConfig.getName(), cacheStatusMapConfig);

        /**create config*/
        return new Config()
                .setInstanceName(this.getClass().getSimpleName())
                .setNetworkConfig(networkConfig)
                .setMapConfigs(mapConfigMap);
    }

}
