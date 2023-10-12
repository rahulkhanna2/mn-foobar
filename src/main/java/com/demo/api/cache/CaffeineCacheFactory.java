package com.demo.api.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;

import java.util.concurrent.TimeUnit;

@Factory
public class CaffeineCacheFactory {

    @Value("${cache.max.size}")
    private long maximumSize;

    @Value("${cache.duration.hours}")
    private long durationInHours;

    @Bean
    public Cache<String, Object> entitlementCache() {
        return Caffeine.newBuilder()
                .maximumSize(maximumSize)
                .expireAfterWrite(durationInHours, TimeUnit.HOURS)
                .build();
    }
}
