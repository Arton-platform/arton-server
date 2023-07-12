package com.arton.backend.infra.config;

import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import lombok.Getter;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Configuration(proxyBeanMethods = false)
public class CacheConfig {

    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(60))
                .disableCachingNullValues()
                .serializeKeysWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())
                )
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())
                );

        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        for (CacheType value : CacheType.values()) {
            cacheConfigurations.put(value.name(),
                    RedisCacheConfiguration.defaultCacheConfig()
                            .prefixCacheNameWith("cache")
                            .entryTtl(Duration.ofHours(value.getExpireTime()))
                            .disableCachingNullValues()
                            .serializeKeysWith(
                                    RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())
                            )
                            .serializeValuesWith(
                                    RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())
                            ));
        }

        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(connectionFactory)
                .cacheDefaults(configuration)
                .withInitialCacheConfigurations(cacheConfigurations).build();
    }

    /**
     * GenericJackson2JsonRedisSerializer
     * list 역직렬화시 에러 발생할 수도..?
     * @return
     */
//    @Bean
//    public RedisCacheConfiguration redisCacheConfiguration() {
//        return RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(Duration.ofSeconds(60))
//                .disableCachingNullValues()
//                .serializeKeysWith(
//                        RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())
//                )
//                .serializeValuesWith(
//                        RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())
//                );
//    }
//
//    @Bean
//    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
//        System.out.println("CacheType.PERFORMANCE_LIST.cacheName = " + CacheType.PERFORMANCE_LIST.cacheName);
//        System.out.println("CacheType.DETAIL.cacheName = " + CacheType.PERFORMANCE_DETAIL.cacheName);
//        return (builder) -> builder
//                .withCacheConfiguration(CacheType.PERFORMANCE_LIST.cacheName,
//                        RedisCacheConfiguration.defaultCacheConfig()
//                                .prefixCacheNameWith("cache")
//                                .entryTtl(Duration.ofHours(CacheType.PERFORMANCE_LIST.getExpireTime()))
//                                .disableCachingNullValues()
//                                .serializeKeysWith(
//                                        RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())
//                                )
//                                .serializeValuesWith(
//                                        RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())
//                                ))
//                .withCacheConfiguration(CacheType.PERFORMANCE_DETAIL.cacheName,
//                        RedisCacheConfiguration.defaultCacheConfig()
//                                .prefixCacheNameWith("cache")
//                                .entryTtl(Duration.ofHours(CacheType.PERFORMANCE_DETAIL.getExpireTime()))
//                                .disableCachingNullValues()
//                                .serializeKeysWith(
//                                        RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())
//                                )
//                                .serializeValuesWith(
//                                        RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())
//                                ));
//    }

    @Getter
    private enum CacheType{
        PERFORMANCE_LIST("performanceList", 12),
        PERFORMANCE_DETAIL("performanceDetail", 12);

        private String cacheName;
        private Integer expireTime;

        CacheType(String cacheName, Integer expireTime) {
            this.cacheName = cacheName;
            this.expireTime = expireTime;
        }

        private static final Map<String, CacheType> CACHE_TYPE_MAP;

        static{
            Map<String, CacheType> map = new ConcurrentHashMap<>();
            for (CacheType cacheType : CacheType.values()) {
                map.put(cacheType.name(), cacheType);
            }
            CACHE_TYPE_MAP = Collections.unmodifiableMap(map);
        }

        public static CacheType get(String name){
            if (!StringUtils.hasText(name)){
                throw new CustomException(ErrorCode.BAD_REQUEST.getMessage(), ErrorCode.BAD_REQUEST);
            }
            return Optional.ofNullable(CACHE_TYPE_MAP.get(name)).orElseThrow(() -> new CustomException(ErrorCode.CACHE_TYPE_NOT_FOUND.getMessage(), ErrorCode.CACHE_TYPE_NOT_FOUND));
        }
    }
}
