package cn.javgo.lion.common.core.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 登录状态枚举
 * @author javgo.cn
 * @date 2023/12/25
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum LoginStatusEnum {

  REGISTER(2, "注册成功", ""),
  SUCCESS(1, "登录成功", ""),
  FAIL(0, "登录失败", "red");

  private final Integer code;

  private final String desc;

  private final String color;
}
