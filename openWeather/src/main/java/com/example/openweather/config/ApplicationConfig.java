package com.example.openweather.config;

import com.example.openweather.model.WeatherData;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(OpenWeatherConfig.class)
public class ApplicationConfig {
    private final CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);
    @Bean
    public Cache<String, WeatherData> currencyRateCache(@Value("${app.cache.size}") int cashSize) {
        return cacheManager.createCache("WeatherData-Cache",
                CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, WeatherData.class,
                                ResourcePoolsBuilder.heap(cashSize))
                        .build());
    }
}
