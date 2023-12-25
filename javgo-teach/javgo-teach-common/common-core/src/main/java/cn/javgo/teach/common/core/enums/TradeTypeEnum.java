package cn.javgo.teach.common.core.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 交易类型枚举
 *
 * @author javgo.cn
 * @date 2023/12/25
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum TradeTypeEnum {

    ONLINE(1, "线上支付"),
    OFFLINE(2, "线下支付");

    private final Integer code;

    private final String desc;
}
