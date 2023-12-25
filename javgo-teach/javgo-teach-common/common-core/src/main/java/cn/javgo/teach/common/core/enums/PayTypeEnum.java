package cn.javgo.teach.common.core.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付类型枚举
 *
 * @author javgo.cn
 * @date 2023/12/25
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum PayTypeEnum {

    WEIXIN_SCAN(1, "微信扫码支付", "wxScanPay"),
    ALIPAY_SCAN(2, "支付宝扫码支付", "aliScanPay");

    private final Integer code;

    private final String desc;

    /**
     * 实现类
     */
    private final String impl;

    public static PayTypeEnum getPayTypeEnum(Integer code) {
        for (PayTypeEnum payTypeEnum : PayTypeEnum.values()) {
            if (payTypeEnum.getCode().equals(code)) {
                return payTypeEnum;
            }
        }
        return null;
    }

}
