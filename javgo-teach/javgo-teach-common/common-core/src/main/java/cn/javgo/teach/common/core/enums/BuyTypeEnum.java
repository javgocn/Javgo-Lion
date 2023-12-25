package cn.javgo.teach.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付类型枚举
 *
 * @author javgo.cn
 * @date 2023/12/25
 */
@Getter
@AllArgsConstructor
public enum BuyTypeEnum {

    BUY(1, "支付", ""),
    FREE(0, "免费", "red");

    private final Integer code;

    private final String desc;

    private final String color;
}
