package com.gardenary.infra.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate;

    public void setValue(String key, String data) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.set(key, data);
    }

    public String getStringValue(String key) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }

    public void setValue(String key, String[] data) {
        SetOperations<String, String> operations = redisTemplate.opsForSet();
        for (String d : data) {
            operations.add(key, d);
        }
    }

    public Set<String> getSetValue(String key) {
        SetOperations<String, String> operations = redisTemplate.opsForSet();
        return operations.members(key);
    }

    public void setValue(String key, Object obj1, Object obj2) {
        HashOperations<String, Object, Object> operations = redisTemplate.opsForHash();
        operations.put(key, obj1, obj2);
    }

    public Object getHashValue(String key, String hash) {
        HashOperations<String, Object, Object> operations = redisTemplate.opsForHash();
        return operations.get(key, hash);
    }

    public void setStringValueAndExpire(String key, String token, long expireDate) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.set(key, token, expireDate, TimeUnit.MILLISECONDS);
    }

    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }

    public void deleteKey(String hashKey, String key) {
        redisTemplate.opsForHash().delete(hashKey, key);
    }

    public void setTokenBlackList(String token, String value, long expireTime) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.set(token, value, expireTime, TimeUnit.MILLISECONDS);
    }

}
