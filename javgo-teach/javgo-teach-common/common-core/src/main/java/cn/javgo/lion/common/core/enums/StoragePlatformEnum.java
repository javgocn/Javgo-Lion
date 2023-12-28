package cn.javgo.lion.common.core.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 存储平台枚举
 *
 * @author javgo.cn
 * @date 2023/12/25
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum StoragePlatformEnum {

    LOCAL(1, "本地", "local%", "local"),
    MinIO(2, "MinIO", "minio%", "minio"),
    ALIYUN(3, "阿里云", "aliyun%", "aliyun"),
    TENCENT(4, "腾讯云(待开发)", "tencent%", "tencent");

    private final Integer code;

    private final String desc;

    private final String tag;

    private final String mode;

    public static StoragePlatformEnum getStoragePlatformEnum(Integer code) {
        for (StoragePlatformEnum storagePlatformEnum : StoragePlatformEnum.values()) {
            if (storagePlatformEnum.getCode().equals(code)) {
                return storagePlatformEnum;
            }
        }
        return null;
    }

}
