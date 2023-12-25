package cn.javgo.teach.common.core.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 资源类型枚举
 *
 * @author javgo.cn
 * @date 2023/12/25
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ResourceTypeEnum {

    VIDEO(1, "视频"),
    AUDIO(2, "音频"),
    DOC(3, "文档"),
    IMG(4, "图片");

    private final Integer code;

    private final String desc;
}
