package cn.javgo.teach.common.core.sms;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * description：短信配置
 *
 * @author javgo.cn
 * @date 2023/12/28
 */
@Data
@Accessors(chain = true) // 链式编程
public class SmsConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 短信平台( 详见 SmsPlatformEnum)
     */
    private Integer smsPlatform;

    // Lkyun 短信签名
    private String lkyunSmsSignName;
    private String lkyunSmsAccessKeyId;
    private String lkyunSmsAccessKeySecret;
    private String lkyunSmsAuthCode;

    // Aliyun 短信签名
    private String aliyunSmsSignName;
    private String aliyunSmsAccessKeyId;
    private String aliyunSmsAccessKeySecret;
    private String aliyunSmsAuthCode;
}
