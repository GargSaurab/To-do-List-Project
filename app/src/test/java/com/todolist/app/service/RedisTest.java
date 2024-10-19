package com.todolist.app.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testRedis(){
        redisTemplate.opsForValue().set("Saurabh", "32425");
        String otp = redisTemplate.opsForValue().get("Saurabh").toString();
        System.out.println(otp);
        assertEquals("32425", otp);
    }
}
