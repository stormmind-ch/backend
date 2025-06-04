package com.stormmind.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;

import static org.assertj.core.api.Assertions.assertThat;

class CacheConfigTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withUserConfiguration(CacheConfig.class);

    @Test
    void testCacheManagerIsCreatedWithExpectedCaches() {
        contextRunner.run(context -> {
            CacheManager cacheManager = context.getBean(CacheManager.class);
            assertThat(cacheManager).isInstanceOf(CaffeineCacheManager.class);

            assertThat(cacheManager.getCache("weather-by-cluster")).isNotNull();
            assertThat(cacheManager.getCache("forecast-all-municipalities")).isNotNull();
            assertThat(cacheManager.getCache("inference")).isNotNull();
        });
    }
}