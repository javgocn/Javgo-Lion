package cn.javgo.lion.common.cache;

import cn.hutool.core.util.StrUtil;
import cn.javgo.lion.common.core.tools.JSUtil;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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

    /**
     * 将对象存储在 Redis 中，使用默认的过期时间。
     *
     * @param key 缓存的键
     * @param value 缓存的值
     * @param <T> 值的类型
     * @return 存储的对象
     */
    public <T> T set(String key, T value) {
        return set(key, value, defaultTimeToLive, TimeUnit.MILLISECONDS);
    }

    /**
     * 将对象存储在 Redis 中，指定过期时间和时间单位。
     *
     * @param key 缓存的键
     * @param value 缓存的值
     * @param time 过期时间
     * @param timeUnit 时间单位
     * @param <T> 值的类型
     * @return 存储的对象
     */
    public <T> T set(String key, T value, long time, TimeUnit timeUnit) {
        if (Objects.nonNull(value)) {
            String jsonValue = (value instanceof String) ? value.toString() : JSUtil.toJsonString(value);
            stringRedisTemplate.opsForValue().set(key, Objects.requireNonNull(jsonValue), time, timeUnit);
        }
        return value;
    }

    /**
     * 从 Redis 中获取字符串值。
     *
     * @param key 缓存的键
     * @return 缓存的字符串值，若不存在则返回 null
     */
    public String get(String key) {
        return Objects.nonNull(key) ? stringRedisTemplate.opsForValue().get(key) : null;
    }

    /**
     * 从 Redis 中获取对象，将 JSON 字符串转换成对象。
     *
     * @param key 缓存的键
     * @param clazz 对象的类型
     * @param <T> 类型参数
     * @return 转换后的对象，若不存在则返回 null
     */
    public <T> T getByJson(String key, Class<T> clazz) {
        String jsonValue = get(key);
        return StringUtils.hasText(jsonValue) ? JSUtil.parseObject(jsonValue, clazz) : null;
    }

    /**
     * 从 Redis 中获取一个列表，将 JSON 字符串转换成列表。
     *
     * @param key 缓存的键
     * @param clazz 列表中元素的类型
     * @param <T> 类型参数
     * @return 转换后的列表对象
     */
    public <T> List<T> listByJson(String key, Class<T> clazz) {
        return JSUtil.parseArray(get(key), clazz);
    }

    /**
     * 从 Redis 中删除一个键。
     *
     * @param key 要删除的键
     */
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }
}
