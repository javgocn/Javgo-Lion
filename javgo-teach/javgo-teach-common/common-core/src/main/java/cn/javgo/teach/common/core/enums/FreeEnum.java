package cn.javgo.teach.common.core.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 免费类型枚举（与 BuyTypeEnum 的区别是啥？）
 *
 * @author javgo.cn
 * @date 2023/12/25
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum FreeEnum {
    FREE(1, "免费", ""),
    CHARGE(0, "收费", "red");

    private final Integer code;

    private final String desc;

    private final String color;
}
