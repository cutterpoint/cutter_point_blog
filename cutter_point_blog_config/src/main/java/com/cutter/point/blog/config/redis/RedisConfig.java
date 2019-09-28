package com.cutter.point.blog.config.redis;


import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


@Configuration
public class RedisConfig extends CachingConfigurerSupport{
  
	@Bean
	public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
			@Override
			public Object generate(Object target, java.lang.reflect.Method method, Object... params) {
				// TODO Auto-generated method stub
				StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
			}
        };
    }

    @SuppressWarnings("rawtypes")
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
//        RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
        //设置缓存过期时间
//        rcm.setDefaultExpiration(60*8);//秒
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);

        // 默认配置，过期时间指定是30分钟
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig();
        defaultCacheConfig.entryTtl(Duration.ofMinutes(8));

        // redisExpire1h cache配置，过期时间指定是1小时，缓存key的前缀指定成prefixaaa_（存到redis的key会自动添加这个前缀）
        RedisCacheConfiguration userCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().
                entryTtl(Duration.ofHours(1)).prefixKeysWith("prefixaaa_");
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
        redisCacheConfigurationMap.put("redisExpire1h", userCacheConfiguration);

        RedisCacheManager cacheManager = new RedisCacheManager(redisCacheWriter, defaultCacheConfig, redisCacheConfigurationMap);
        return cacheManager;
    }
    
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
    
}
