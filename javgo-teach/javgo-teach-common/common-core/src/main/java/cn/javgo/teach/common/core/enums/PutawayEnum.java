package cn.javgo.teach.common.core.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 上架状态枚举
 *
 * @author javgo.cn
 * @date 2023/12/25
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum PutawayEnum {

    UP(1, "上架", ""),
    DOWN(0, "下架", "red");

    private final Integer code;

    private final String desc;

    private final String color;
}
