package com.lam.liveamonthapp.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import com.lam.liveamonthapp.auth.dto.response.TokenResponse;
import com.lam.liveamonthapp.config.AppProperties;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
public class RedisRepositoryImpl implements RedisRepository {
    private final AppProperties appProperties;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void save(String key, TokenResponse response) {
        redisTemplate.opsForValue()
                .set(
                        key,
                        response.getRefreshToken(),
                        appProperties.getAuth().getRefreshTokenExpireTime(),
                        TimeUnit.MILLISECONDS);
    }

    @Override
    public void save(String key, String value, long expiration) {
        redisTemplate.opsForValue()
                .set(
                        key,
                        value,
                        expiration,
                        TimeUnit.MILLISECONDS);
    }

    @Override
    public String getValue(String key) {
        return redisTemplate.opsForValue()
                .get(key);
    }

    @Override
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }


}
