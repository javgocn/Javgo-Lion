package cn.javgo.lion.common.cache;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

/**
 * description：自定义 Redis 缓存管理器
 *
 * @author javgo.cn
 * @date 2023/12/28
 */
@EnableCaching
@Configuration
public class CacheConfig extends CachingConfigurerSupport {

    /**
     * 自定义 Redis Key 生成策略。<br>
     * 生成策略为：类名::方法名:所有参数值，从而生成唯一的 Key。<br>
     * 即使 @Cacheable 的 value 属性值相同，最终的 Key 也会不同。
     */
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder keyBuilder = new StringBuilder();
            keyBuilder.append(target.getClass().getName());
            keyBuilder.append("::");
            keyBuilder.append(method.getName());
            for (Object param : params) {
                keyBuilder.append(":");
                if (Objects.nonNull(param)) {
                    keyBuilder.append(param);
                }
            }
            return keyBuilder.toString();
        };
    }
}
