package com.project.core.common.service.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Point;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RedisService {

  private final RedisTemplate<String,Object> redisTemplate;
  private final ObjectMapper objectMapper;

  public void addData(
          final String key,
          final String value,
          final long expiredTime
  ) {
    redisTemplate.opsForValue().set(key, value, expiredTime);
  }

  public Object getData(final String key) {
    return redisTemplate.opsForValue().get(key);
  }

  public void addList(final String key, final Point point) {
    try {
      redisTemplate.opsForList().rightPush(key,objectMapper.writeValueAsString(point));
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public List<Point> getList(final String key) {
    final RedisOperations<String, Object> routes = redisTemplate.opsForList().getOperations();
    final List <Object> list = routes.opsForList().range(key , 0 , -1);

    if(list.isEmpty()) {
      throw new RuntimeException("redis list is empty");
    }

    return list.stream()
        .map(obj->
            {
              try {
                return objectMapper.readValue(obj.toString(),Point.class);
              } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
              }
            }
        )
        .collect(Collectors.toList());
  }

  public void deleteData(final String key){
    redisTemplate.delete(key);
  }
}
