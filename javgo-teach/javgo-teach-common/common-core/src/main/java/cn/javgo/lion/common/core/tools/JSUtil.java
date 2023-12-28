package cn.javgo.lion.common.core.tools;

import cn.hutool.json.JSONUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * JSON 工具类
 *
 * @author javgo.cn
 * @date 2023/12/27
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JSUtil {

    public static String toJsonString(Object obj) {
        try {
            return JSONUtil.toJsonStr(obj);
        } catch (Exception e) {
            log.error("JSON 序列化失败", e);
            return null;
        }
    }

    public static <T> T parseObject(String jsonStr, Class<T> clazz) {
        try {
            return JSONUtil.parseObj(jsonStr).toBean(clazz);
        } catch (Exception e) {
            log.error("JSON 反序列化失败", e);
            return null;
        }
    }

    public static <T> List<T> parseArray(String jsonStr, Class<T> clazz) {
        try {
            return JSONUtil.parseArray(jsonStr).toList(clazz);
        } catch (Exception e) {
            log.error("JSON 反序列化失败", e);
            return null;
        }
    }
}
