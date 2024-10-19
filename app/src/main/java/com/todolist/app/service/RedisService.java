package com.todolist.app.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todolist.app.customException.RedisException;
import com.todolist.app.util.LogUtil;
import com.todolist.app.util.StatusCode;
import com.todolist.app.util.Utility;

@Service
public class RedisService {

    @Value("${redis.default-ttl}")
    private Long defaultTtl;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public <T> T get(String key, Class<T> entityClass) {

        String value = redisTemplate.opsForValue().get(key).toString().toString();

        if (value != null) {
            switch (entityClass.getSimpleName()) {
                case "String":
                    return (T) value;

                case "Integer":
                    return (T) Integer.valueOf(value);

                case "Double":
                    return (T) Double.valueOf(value);

                case "LocalDateTime":
                    return (T) LocalDateTime.parse(value);

                default:

                    if (List.class.isAssignableFrom(entityClass)) {
                        try {
                            return (T) objectMapper.readValue(value, new TypeReference<List<Object>>() {
                            });
                        } catch (Exception e) {
                            throw new RedisException("Failed to deserialize JSON to List", StatusCode.SERVER_ERROR, e);
                        }
                    }
                    if (Map.class.isAssignableFrom(entityClass)) {
                        try {
                            return (T) objectMapper.readValue(value, new TypeReference<Map<String, Object>>() {
                            });
                        } catch (Exception e) {
                            throw new RedisException("Failed to deserialize JSON to Map", StatusCode.SERVER_ERROR, e);
                        }
                    }

                    try {
                        return objectMapper.readValue(value, entityClass);
                    } catch (Exception e) {
                        throw new RedisException("Failed to deserialize JSON to " + entityClass.getSimpleName(), StatusCode.SERVER_ERROR,
                                e);
                    }

            }
        } else {
            LogUtil.warn(RedisService.class, "Redis key not found: " + key);
            throw new RedisException("Key not found in Redis", StatusCode.NOT_FOUND, null);
        }
    }

    public void set(String key, Object value, Long ttl) {
        String valueToStore;
        try {
            if(!Utility.isEmpty(value)){
            if (value instanceof String) {
                valueToStore = (String) value;
            } else {
                valueToStore = objectMapper.writeValueAsString(value);
            }
            if (ttl != null) {
                redisTemplate.opsForValue().set(key, valueToStore, ttl, TimeUnit.SECONDS);
            } else {
                redisTemplate.opsForValue().set(key, valueToStore, defaultTtl, TimeUnit.MINUTES);
            }}
            else{
                throw new RedisException("Value cannot be null", StatusCode.BAD_REQUEST, null);
            }
        } catch (JsonProcessingException e) {
            throw new RedisException("Failed to serialize the given value", StatusCode.SERVER_ERROR, e);
        } catch (Exception e) {
            throw new RedisException("Failed to store data in Redis", StatusCode.SERVICE_UNAVAILABLE, e);
        }

        LogUtil.info(RedisService.class, "Data stored in Redis successfully");
    }

    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public void delete(String Key) {
        redisTemplate.delete(Key);
    }
}
