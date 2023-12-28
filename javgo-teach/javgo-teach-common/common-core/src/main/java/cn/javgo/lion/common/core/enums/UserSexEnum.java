package cn.javgo.lion.common.core.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户性别枚举
 *
 * @author javgo.cn
 * @date 2023/12/25
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum UserSexEnum {

    MALE(1, "男", "green"),
    FEMALE(2, "女", "red"),
    SECRET(3, "保密", "orange");

    private final Integer code;

    private final String desc;

    private final String color;
}
