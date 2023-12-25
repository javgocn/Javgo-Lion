package cn.javgo.teach.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 站内信阅读状态
 *
 * @author javgo.cn
 * @date 2023/12/25
 */
@Getter
@AllArgsConstructor
public enum ReadEnum {

    READ(1, "已读"),
    NO(0, "未读");

    private final Integer code;

    private final String desc;
}
