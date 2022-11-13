package com.jijo.jerseyhouse.config.hazelcast;

import com.hazelcast.config.*;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!junit")
@Configuration
@EnableCaching
public class HazelcastConfig {

    @Bean
    public Config hazelcastConfiguration(){
        return new Config()
                .setInstanceName("hazelcast-instance")
                .addMapConfig(new MapConfig()
                        .setName("jersey-house-cache")
                        .setTimeToLiveSeconds(3000)
                        .setEvictionConfig(new EvictionConfig()
                                .setSize(200)
                                .setMaxSizePolicy(MaxSizePolicy.FREE_HEAP_SIZE)
                                .setEvictionPolicy(EvictionPolicy.LRU)
                ));

    }
}
