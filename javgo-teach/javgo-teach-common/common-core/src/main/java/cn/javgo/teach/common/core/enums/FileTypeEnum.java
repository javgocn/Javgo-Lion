package cn.javgo.teach.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文件类型枚举
 *
 * @author javgo.cn
 * @date 2023/12/25
 */
@Getter
@AllArgsConstructor
public enum FileTypeEnum {

    VIDEO(1, "视频"),
    FILE(2, "文件");

    private final Integer code;

    private final String desc;
}
