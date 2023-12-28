package cn.javgo.lion.common.core.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 状态枚举
 *
 * @author javgo.cn
 * @date 2023/12/25
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum StatusIdEnum {

    YES(1, "正常", ""),
    NO(0, "禁用", "red");

    private final Integer code;

    private final String desc;

    private final String color;
}
