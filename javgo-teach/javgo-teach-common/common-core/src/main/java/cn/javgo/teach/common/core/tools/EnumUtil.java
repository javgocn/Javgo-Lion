package cn.javgo.teach.common.core.tools;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

/**
 * 枚举工具类
 *
 * @author javgo.cn
 * @date 2023/12/26
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EnumUtil {

    private static final String DEFAULT_ENUM_NAME = "name";

    /**
     * 将枚举类转换为列表
     *
     * @param targetEnumClazz 枚举类
     * @param <T>             枚举类型
     * @return 枚举信息列表
     */
    public static <T extends Enum<T>> List<Map<String, Object>> toList(Class<T> targetEnumClazz) {
        return toList(targetEnumClazz, DEFAULT_ENUM_NAME);
    }

    /**
     * 将枚举类转换为列表，并指定枚举名字段
     *
     * @param targetEnumClazz 枚举类
     * @param enumName        枚举名字段
     * @param <T>             枚举类型
     * @return 枚举信息列表
     */
    public static <T extends Enum<T>> List<Map<String, Object>> toList(Class<T> targetEnumClazz, String enumName) {
        List<Map<String, Object>> resultList = Lists.newArrayList();

        if (!targetEnumClazz.isEnum()) {
            return resultList;
        }

        try {
            Field[] fields = targetEnumClazz.getDeclaredFields();
            EnumSet<T> enums = EnumSet.allOf(targetEnumClazz);

            for (T enumConstant : enums) {
                Map<String, Object> enumMap = Maps.newHashMap();
                for (Field field : fields) {
                    if (!field.isEnumConstant() && !field.isSynthetic()) {
                        field.setAccessible(true);
                        enumMap.put(field.getName(), field.get(enumConstant));
                    }
                }
                enumMap.put(enumName, enumConstant.name());
                resultList.add(enumMap);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return resultList;
    }
}
