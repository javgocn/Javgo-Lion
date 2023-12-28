package cn.javgo.teach.common.core.aliyun;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * description：阿里云 (OSS、短信) 配置
 *
 * @author javgo.cn
 * @date 2023/12/28
 */
@Data
@Accessors(chain = true)
public class Aliyun implements Serializable {

    private static final long serialVersionUID = 1L;

    // 阿里云 OSS
    private String aliyunAccessKeyId;
    private String aliyunAccessKeySecret;
    private String aliyunOssUrl;
    private String aliyunOssEndpoint;
    private String aliyunOssBucket;

    // 阿里云短信
    private String aliyunSmsSignName;
    private String aliyunSmsAccessKeyId;
    private String aliyunSmsAccessKeySecret;
    private String aliyunSmsAuthCode;
}
