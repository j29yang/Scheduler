package com.scm.dashboard.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:conf/redis/redis.properties")
public class RedisConfiguration {

}
