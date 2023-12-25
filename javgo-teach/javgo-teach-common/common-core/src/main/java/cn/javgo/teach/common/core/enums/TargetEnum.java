package cn.javgo.teach.common.core.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 链接跳转方式枚举
 *
 * @author javgo.cn
 * @date 2023/12/25
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum TargetEnum {

    BLANK("_blank", "新窗口打开"),
    SELF("_self", "同窗口打开");

    private final String code;

    private final String desc;
}
