package com.vosouq.hazelcast.cache.embedded;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class EmbeddedCacheInObjectConfig {

    public static final String PERSONS = "persons";
    public static final String FICO_SCORING = "ficoScoring";


//    @Bean(name = "hazelcastInstanceLocal")
    public HazelcastInstance getHazelcastInstance(Config config) {
        HazelcastInstance hazelcastInstanceLocal = Hazelcast.newHazelcastInstance(config);
        return hazelcastInstanceLocal;
    }


//    @Bean
    public Config hazelcastConfig() {
        /**create NetworkConfig */
        NetworkConfig networkConfig = new NetworkConfig();
        JoinConfig joinConfig = new JoinConfig();
        joinConfig.getMulticastConfig().setEnabled(false);
        networkConfig.setJoin(joinConfig);

        /**create Persons MapConfig*/
        MapConfig personMapConfig = new MapConfig();
        personMapConfig.setName(PERSONS);
        personMapConfig.setInMemoryFormat(InMemoryFormat.NATIVE);
        personMapConfig.setBackupCount(0);
        personMapConfig.setTimeToLiveSeconds(0);
        personMapConfig.setMaxIdleSeconds(0);

        /**create Persons MapConfig*/
        MapConfig ficoMapConfig = new MapConfig();
        ficoMapConfig.setName(FICO_SCORING);
        ficoMapConfig.setInMemoryFormat(InMemoryFormat.OBJECT);
        ficoMapConfig.setBackupCount(0);
        ficoMapConfig.setTimeToLiveSeconds(0);
        ficoMapConfig.setMaxIdleSeconds(0);

        /**create EvictionConfig*/
        EvictionConfig evictionConfig = new EvictionConfig();
        evictionConfig.setEvictionPolicy(EvictionPolicy.LRU);
        evictionConfig.setMaxSizePolicy(MaxSizePolicy.FREE_HEAP_SIZE);
        ficoMapConfig.setEvictionConfig(evictionConfig);

        /**create config*/
        return new Config()
                .setInstanceName("hazelcast-embedded-in-object-instance")
//                .addMapConfig(personMapConfig)
                .addMapConfig(ficoMapConfig);
    }

}
