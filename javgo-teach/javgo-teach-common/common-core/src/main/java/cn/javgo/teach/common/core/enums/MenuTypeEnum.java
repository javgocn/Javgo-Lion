package cn.javgo.teach.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 菜单类型枚举
 *
 * @author javgo.cn
 * @date 2023/12/25
 */
@Getter
@AllArgsConstructor
public enum MenuTypeEnum {

    DIRECTORY(1, "目录"),
    MENU(2, "菜单"),
    PERMISSION(3, "权限");

    private final Integer code;

    private final String desc;
}
