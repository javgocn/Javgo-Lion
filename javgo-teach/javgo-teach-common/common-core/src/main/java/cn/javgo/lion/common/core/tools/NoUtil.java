package cn.javgo.lion.common.core.tools;

import cn.hutool.core.util.RandomUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 单号工具类（规则：时间戳 + 随机数）（注意：一定概率会重复，数据库还会做一层唯一性校验，因此可以忽略）
 *
 * @author javgo.cn
 * @date 2023/12/27
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NoUtil {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    /**
     * 生成 6 位随机数
     */
    public static String getVerCode() {
        return RandomUtil.randomNumbers(6);
    }

    /**
     * 生成订单号
     */
    public static synchronized Long getOrderNo() {
        return Long.valueOf(generateTimestamp() + RandomUtil.randomNumbers(3));
    }

    /**
     * 生成流水号
     */
    public static Long getSerialNumber() {
        return Long.valueOf(generateTimestamp() + RandomUtil.randomNumbers(4));
    }

    /**
     * 生成时间戳
     */
    private static String generateTimestamp() {
        return DATE_FORMAT.format(new Date());
    }
}
