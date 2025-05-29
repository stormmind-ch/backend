package com.stormmind.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        Caffeine<Object, Object> spec = Caffeine.newBuilder()
                .maximumSize(5_000) // max cache size. After 5k entries, the cache deletes some values.
                .expireAfterWrite(Duration.ofDays(1));
        CaffeineCacheManager mgr = new CaffeineCacheManager("weather-by-cluster",
                "forecast-all-municipalities",
                "inference"

        );
        mgr.setCaffeine(spec);
        return mgr;
    }
}
