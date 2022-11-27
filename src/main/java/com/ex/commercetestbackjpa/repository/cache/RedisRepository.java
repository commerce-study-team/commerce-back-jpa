package com.ex.commercetestbackjpa.repository.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RedisRepository implements CacheRepository {

    private final RedisTemplate redisTemplate;


    @Override
    public void sortSetAdd(String key, String item, Long score) {
        redisTemplate.opsForZSet().add(key, item, score);
    }

    @Override
    public void sortSetFind(String key) {

    }
}
