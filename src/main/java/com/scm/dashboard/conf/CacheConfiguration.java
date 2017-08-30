package com.scm.dashboard.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableCaching
public class CacheConfiguration {

	@Bean
	@Autowired
	public CacheManager cacheManager(RedisTemplate<Object, Object> redisTemplate) {
		return new RedisCacheManager(redisTemplate);
	}
}