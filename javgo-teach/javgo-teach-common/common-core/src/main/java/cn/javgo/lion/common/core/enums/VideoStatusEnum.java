package cn.javgo.lion.common.core.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 视频状态枚举
 *
 * @author javgo.cn
 * @date 2023/12/25
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum VideoStatusEnum {

    WAIT(1, "转码中", ""),
    SUCCESS(2, "成功", "green"),
    FINAL(3, "失败", "red");

    private final Integer code;

    private final String desc;

    private final String color;
}
