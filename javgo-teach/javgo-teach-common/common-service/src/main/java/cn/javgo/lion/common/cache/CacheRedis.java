package cn.javgo.lion.common.cache;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * description：Redis 缓存
 *
 * @author javgo.cn
 * @date 2023/12/28
 */
@Component
public class CacheRedis {

    /**
     * 默认过期时间（配置中心没有则使用默认值 60000 ms）
     */
    @Value("${spring.cache.redis.time-to-live:60000}")
    private long defaultTimeToLive;

    @Getter
    @Resource
    private StringRedisTemplate stringRedisTemplate;
}
