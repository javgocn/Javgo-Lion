package cn.javgo.lion.common.core.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 响应状态码枚举
 *
 * @author javgo.cn
 * @date 2023/12/24
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ResultEnum {
    SUCCESS(200, "成功"),

    TOKEN_PAST(301, "Token 已过期"),
    TOKEN_ERROR(302, "Token 异常"),
    LOGIN_ERROR(303, "登录异常"),
    REMOTE_ERROR(304, "异地登录"),
    MENU_PAST(305, "菜单过期"),
    MENU_NO(306, "菜单权限不足"),

    COURSE_SAVE_FAIL(403, "课程添加失败"),
    COURSE_UPDATE_FAIL(404, "课程更新失败"),
    COURSE_DELETE_FAIL(405, "课程删除失败"),
    COLLECTION(406, "已收藏"),
    USER_ADVICE(406, "保存建议失败,不能重复提建议"),
    COURSE_AUDIT_FAIL(407, "审核失败"),
    LECTURER_REQUISITION_REGISTERED(501, "申请讲师失败！该手机号未注册，请先注册账号"),
    LECTURER_REQUISITION_WAIT(502, "申请讲师失败！该账号已提交申请入驻成为讲师，状态为待审核，在7个工作日内会有相关人员与您联系确认"),
    LECTURER_REQUISITION_FAIL(503, "申请讲师失败！该账号已提交申请入驻成为讲师，审核不通过，请联系平台管理员"),
    LECTURER_REQUISITION_YET(504, "申请讲师失败！该账号已成为讲师，请直接登录"),
    USER_SAVE_FAIL(505, "添加用户失败"),
    USER_UPDATE_FAIL(506, "更新用户失败"),
    USER_LECTURER_AUDIT(507, "审核失败"),
    USER_SEND_FAIL(508, "发送失败"),
    USER_DELETE_FAIL(509, "删除失败"),

    SYSTEM_SAVE_FAIL(601, "添加失败"),
    SYSTEM_UPDATE_FAIL(602, "更新失败"),
    SYSTEM_DELETE_FAIL(603, "删除失败"),

    ERROR(999, "错误");

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 描述
     */
    private final String message;

    public static ResultEnum fromCode(int code) {
        for (ResultEnum resultEnum : ResultEnum.values()) {
            if (resultEnum.getCode() == code) {
                return resultEnum;
            }
        }
        return ERROR;
    }
}
