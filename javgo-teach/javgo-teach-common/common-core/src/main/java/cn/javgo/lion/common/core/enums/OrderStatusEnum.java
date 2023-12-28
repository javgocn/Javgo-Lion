/**
 * Copyright 2023-现在
 */
package cn.javgo.lion.common.core.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单状态枚举
 *
 * @author javgo.cn
 * @date 2023/12/25
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum OrderStatusEnum {

    WAIT(1, "待支付"),
    SUCCESS(2, "支付成功"),
    FAIL(3, "支付失败"),
    CLOSE(4, "关闭支付");

    private final Integer code;

    private final String desc;

}
