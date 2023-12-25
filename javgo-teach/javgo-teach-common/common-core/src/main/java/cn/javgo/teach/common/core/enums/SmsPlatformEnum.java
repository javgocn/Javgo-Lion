package cn.javgo.teach.common.core.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * SMS 平台枚举（短信服务平台）
 *
 * @author javgo.cn
 * @date 2023/12/25
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SmsPlatformEnum {

    LK_YUN(1, "云", "lkyun%"),

    ALI_YUN(2, "阿里云", "aliyun%");

    private final Integer code;

    private final String desc;

    private final String tag;

    public static SmsPlatformEnum getSmsPlatformEnum(Integer code) {
        for (SmsPlatformEnum smsPlatformEnum : SmsPlatformEnum.values()) {
            if (smsPlatformEnum.getCode().equals(code)) {
                return smsPlatformEnum;
            }
        }
        return null;
    }
}
