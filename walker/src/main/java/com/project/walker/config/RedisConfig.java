package com.project.walker.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.project.core.common.service.redis.CoordinateDeserializer;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    private final ObjectMapper objectMapper;

    private static final String REDISSON_HOST_PREFIX="redis://";

    @Bean
    public RedissonClient redissonClient(){
        final Config config=new Config();
        config.useSingleServer().setAddress(REDISSON_HOST_PREFIX+redisHost+":"+redisPort);
        return Redisson.create(config);
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        return new LettuceConnectionFactory(redisHost,redisPort);
    }

    @Bean
    public RedisTemplate<String,Object> redisTemplate(){
        final SimpleModule module = new SimpleModule();
        module.addDeserializer(Coordinate.class, new CoordinateDeserializer(Coordinate.class));

        final RedisSerializer<Object> jsonSerializer = new GenericJackson2JsonRedisSerializer(objectMapper.registerModule(module));

        final RedisTemplate<String, Object > redisTemplate=new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jsonSerializer);
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

}

