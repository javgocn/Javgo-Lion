package cn.javgo.lion.common.core.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 视频平台枚举
 *
 * @author javgo.cn
 * @date 2023/12/25
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum VodPlatformEnum {

    PRIVATEY(1, "私有云", "priy%"),
    POLYV(2, "保利威", "polyv%"),
    BAIJY(3, "百家云(待实现)", "baijy%"),
    BOKECC(4, "获得场景(待实现)", "bokecc%");

    private final Integer code;

    private final String desc;

    private final String tag;

    public static VodPlatformEnum getVodPlatformEnum(Integer code) {
        for (VodPlatformEnum vodPlatformEnum : VodPlatformEnum.values()) {
            if (vodPlatformEnum.getCode().equals(code)) {
                return vodPlatformEnum;
            }
        }
        return null;
    }
}
