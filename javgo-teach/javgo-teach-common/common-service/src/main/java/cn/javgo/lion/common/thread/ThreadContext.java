package cn.javgo.lion.common.thread;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * description：线程上下文工具类
 *
 * @author javgo.cn
 * @date 2023/12/28
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ThreadContext {

    /**
     * 存储用户ID（Admin、User 端登录之后会存储用户ID）
     */
    private static final ThreadLocal<String> USER_ID_LOCAL = new ThreadLocal<>();

    /**
     * 获取用户 ID
     */
    public static Long getUserId() {
        return Long.valueOf(USER_ID_LOCAL.get());
    }

    /**
     * 设置用户 ID
     */
    public static void setUserId(String value) {
        if (Objects.isNull(value)) {
            removeUserId();
            return;
        }
        USER_ID_LOCAL.set(value);
    }

    /**
     * 移除用户 ID
     */
    public static void removeUserId() {
        USER_ID_LOCAL.remove();
    }
}
