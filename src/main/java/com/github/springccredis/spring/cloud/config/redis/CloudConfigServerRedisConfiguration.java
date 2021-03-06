package com.github.springccredis.spring.cloud.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.config.server.config.ConfigServerProperties;
import org.springframework.cloud.config.server.environment.SearchPathLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author kumar ( )
 */
@Configuration
@EnableConfigurationProperties(ConfigServerProperties.class)
public class CloudConfigServerRedisConfiguration {

    @Autowired
    private ConfigServerProperties configServerProperties;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Bean
    //TODO: Replace with actual implementation
    public SearchPathLocator searchPathLocator() {
        return new SearchPathLocator() {
            @Override
            public Locations getLocations(String application, String profile, String label) {
                return null;
            }
        };
    }

    @Bean
    public RedisEnvironmentRepository redisEnvironmentRepository() {
        return new RedisEnvironmentRepository(redisConfigPropertySourceProvider(), configServerProperties);
    }

    @Bean
    public RedisConfigPropertySourceProvider redisConfigPropertySourceProvider() {
        return new RedisConfigPropertySourceProvider(stringRedisTemplate, redisConfigKeysProvider(), redisConfigKeysUtilities(), configServerProperties);
    }

    @Bean
    public RedisConfigKeysProvider redisConfigKeysProvider() {
        return new RedisConfigKeysProvider(stringRedisTemplate, redisConfigKeysUtilities());
    }

    @Bean
    public RedisPropertyNamePatternProvider redisConfigKeysUtilities() {
        return new RedisPropertyNamePatternProvider();
    }
}
