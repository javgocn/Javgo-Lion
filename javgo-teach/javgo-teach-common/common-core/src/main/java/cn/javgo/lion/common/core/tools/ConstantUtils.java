package cn.javgo.lion.common.core.tools;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 常量工具类
 *
 * @author javgo.cn
 * @date 2023/12/26
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConstantUtils {

    /**
     * 手机号码正则表达式
     */
    public static final String REGEX_MOBILE = "^((13[0-9])|(14[5,7,9])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199)\\d{8}$";

    public static final String ADMIN = "admin";

    public static final String TOKEN = "token";

    public static final String USER_ID = "userId";

    /**
     * SESSION 过期时间
     */
    public static final int SESSION_TIME = 40;

    /**
     * Redis 前缀
     */
    public interface RedisPre {
        String ADMIN_MENU = "admin::menu::";
        String ADMIN_VER_CODE = "admin::ver::code::";
        String USER_STUDY = "user::study::";
        String RESOURCE = "resource::";
        String PROGRESS = "progress::";
        String CODE = "code::";
        String PAY = "pay::";
        String DOMAIN = "domain::";
    }
}
