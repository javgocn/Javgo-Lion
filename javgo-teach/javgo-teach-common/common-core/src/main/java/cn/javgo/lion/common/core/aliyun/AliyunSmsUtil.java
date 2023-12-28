package cn.javgo.lion.common.core.aliyun;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * description：阿里云短信工具类
 *
 * @author javgo.cn
 * @date 2023/12/28
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AliyunSmsUtil {

    // 阿里云短信 API 的域名
    private static final String SMS_API_DOMAIN = "dysmsapi.aliyuncs.com";
    // 阿里云短信 API 的版本号
    private static final String SMS_API_VERSION = "2017-05-25";
    // 阿里云短信 API 的操作名称
    private static final String SMS_API_ACTION = "SendSms";
    // 默认的地域
    private static final String DEFAULT_REGION = "cn-hangzhou";

    /**
     * 发送验证码短信。
     *
     * @param mobile 接收短信的手机号
     * @param code   验证码
     * @param aliyun 阿里云配置信息
     * @return 发送是否成功
     */
    public static boolean sendVerificationCode(String mobile, String code, Aliyun aliyun) {
        String templateParam = String.format("{\"code\":\"%s\"}", code);
        return sendSms(mobile, templateParam, aliyun);
    }

    /**
     * 发送短信
     *
     * @param phoneNumber   手机号码
     * @param templateParam 短信模板参数
     * @param aliyun        阿里云配置
     * @return 是否发送成功
     */
    private static boolean sendSms(String phoneNumber, String templateParam, Aliyun aliyun) {
        setSystemProperties();
        IAcsClient acsClient = createAcsClient(aliyun);
        CommonRequest request = createCommonRequest(phoneNumber, templateParam, aliyun);
        return processSendRequest(acsClient, request);
    }

    /**
     * 设置系统属性
     */
    private static void setSystemProperties() {
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
    }


    /**
     * 创建并返回一个 IAcsClient 对象
     *
     * @param aliyun 用于配置阿里云参数的 Aliyun 对象
     * @return 返回一个 IAcsClient 对象
     */
    private static IAcsClient createAcsClient(Aliyun aliyun) {
        // 获取阿里云默认区域的配置文件
        IClientProfile profile = DefaultProfile.getProfile(DEFAULT_REGION, aliyun.getAliyunSmsAccessKeyId(), aliyun.getAliyunSmsAccessKeySecret());
        // 返回一个新的 DefaultAcsClient 对象
        return new DefaultAcsClient(profile);
    }

    /**
     * 创建一个 CommonRequest 对象
     *
     * @param phoneNumber   手机号
     * @param templateParam 模板参数
     * @param aliyun        阿里云对象
     * @return CommonRequest 对象
     */
    private static CommonRequest createCommonRequest(String phoneNumber, String templateParam, Aliyun aliyun) {
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(SMS_API_DOMAIN);
        request.setSysVersion(SMS_API_VERSION);
        request.setSysAction(SMS_API_ACTION);
        request.putQueryParameter("RegionId", DEFAULT_REGION);
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", aliyun.getAliyunSmsSignName());
        request.putQueryParameter("TemplateCode", aliyun.getAliyunSmsAuthCode());
        request.putQueryParameter("TemplateParam", templateParam);
        return request;
    }

    /**
     * 处理发送请求
     *
     * @param acsClient AcsClient对象
     * @param request   发送请求
     * @return 如果发送成功返回true，否则返回false
     */
    private static boolean processSendRequest(IAcsClient acsClient, CommonRequest request) {
        try {
            CommonResponse response = acsClient.getCommonResponse(request);
            JSONObject resultJson = JSONUtil.parseObj(response.getData());
            if (!"OK".equals(resultJson.getStr("Code"))) {
                log.error("短信发送错误: {}", resultJson.getStr("Message"));
                return false;
            }
            return true;
        } catch (Exception e) {
            log.error("系统繁忙", e);
            return false;
        }
    }

}
