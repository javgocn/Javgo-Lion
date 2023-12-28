package cn.javgo.teach.common.core.tools;

import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Spring Bean 工具类
 *
 * @author javgo.cn
 * @date 2023/12/26
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BeanUtil<T extends Serializable> {

    /**
     * 复制属性（浅拷贝）
     *
     * @param source 源对象
     * @param clazz  目标对象
     * @param <T>    目标对象类型
     * @return 目标对象
     */
    public static <T> T copyProperties(Objects source, Class<T> clazz) {
        if (Objects.isNull(source)) {
            return null;
        }
        T target = null;
        try {
            target = clazz.newInstance();
            BeanUtils.copyProperties(source, target);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return target;
    }

    /**
     * 复制属性（批量浅拷贝）
     *
     * @param source 源对象
     * @param clazz  目标对象
     * @param <T>    目标对象类型
     * @return 目标对象
     */
    public static <T> List<T> copyProperties(List<Objects> source, Class<T> clazz) {
        if (CollectionUtils.isEmpty(source)) {
            return Lists.newArrayList();
        }
        List<T> target = new ArrayList<>(source.size());
        for (Objects objects : source) {
            T targetObj = null;
            try {
                targetObj = clazz.newInstance();
                BeanUtils.copyProperties(objects, targetObj);
            } catch (Exception e) {
                e.printStackTrace();
            }
            target.add(targetObj);
        }
        return target;
    }

    /**
     * 对象或 Map 转 Bean，忽略字段转换时发生的异常
     * @param map Bean 对象或 Map
     * @param clazz 目标的 Bean 类型
     * @return
     * @param <T>
     */
    public static <T> T objectToBean(Map<?, ?> map, Class<T> clazz) {
        // 是否忽略注入错误
        return cn.hutool.core.bean.BeanUtil.toBeanIgnoreCase(map,clazz,true);
    }
}
