package top.juanshen.wx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class AccessTokenService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 从Redis中获取access_token
     * @param key access_token的键
     * @return access_token
     */
    public String getAccessToken(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 将access_token保存到Redis中
     * @param key access_token的键
     * @param value access_token的值
     * @param expiresIn access_token的有效期（单位：秒）
     */
    public void saveAccessToken(String key, String value, long expiresIn) {
        redisTemplate.opsForValue().set(key, value, expiresIn, TimeUnit.SECONDS);
    }
}

