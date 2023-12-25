package cn.javgo.teach.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 配置类型枚举
 *
 * @author javgo.cn
 * @date 2023/12/25
 */
@Getter
@AllArgsConstructor
public enum ConfigTypeEnum {

    SYSTEM(1, "系统信息"),
    PAY(6, "支付信息");

    private final Integer code;

    private final String desc;

    public static ConfigTypeEnum getEnum(Integer code) {
        for (ConfigTypeEnum configTypeEnum : ConfigTypeEnum.values()) {
            if (configTypeEnum.getCode().equals(code)) {
                return configTypeEnum;
            }
        }
        return null;
    }
}
