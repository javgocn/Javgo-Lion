package cn.javgo.teach.common.core.sms;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.javgo.teach.common.core.enums.SmsPlatformEnum;
import cn.javgo.teach.common.core.tools.JSUtil;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * description：短信服务工具类
 *
 * @author javgo.cn
 * @date 2023/12/28
 */
@Slf4j
public class SmsUtil {

    /**
     * 发送验证码
     *
     * @param mobile    接收手机号
     * @param code      验证码
     * @param smsConfig 短信配置
     * @return 是否发送成功
     */
    public static Boolean sendVerCode(String mobile, String code, SmsConfig smsConfig) {
        if (smsConfig.getSmsPlatform().equals(SmsPlatformEnum.LK_YUN.getCode())) {
            return lkyun(mobile, getParamForCode(code), smsConfig.getLkyunSmsSignName(), smsConfig.getLkyunSmsAuthCode(), smsConfig.getLkyunSmsAccessKeyId(), smsConfig.getLkyunSmsAccessKeySecret());
        } else if (smsConfig.getSmsPlatform().equals(SmsPlatformEnum.ALI_YUN.getCode())) {
            return aliyun(mobile, getParamForCode(code), smsConfig.getAliyunSmsSignName(), smsConfig.getAliyunSmsAuthCode(), smsConfig.getAliyunSmsAccessKeyId(), smsConfig.getAliyunSmsAccessKeySecret());
        }
        log.error("未知的短信平台, smsPlatform={}", smsConfig.getSmsPlatform());
        return false;
    }


    /**
     * 发送短信到指定手机号
     *
     * @param phone           短信接收手机号
     * @param templateParam   短信模板参数
     * @param signName        签名名称
     * @param templateCode    模板编码
     * @param accessKeyId     访问密钥ID
     * @param accessKeySecret 访问密钥秘密
     * @return 发送成功返回true，否则返回false
     */
    private static Boolean lkyun(String phone, String templateParam, String signName, String templateCode, String accessKeyId, String accessKeySecret) {
        Map<String, Object> map = new HashMap<>();
        map.put("phoneNumbers", phone);
        map.put("templateParam", templateParam);
        map.put("signName", signName);
        map.put("templateCode", templateCode);
        map.put("accessKeyId", accessKeyId);
        map.put("signatureNonce", String.valueOf(System.currentTimeMillis()));
        map.put("sign", sign(map, accessKeySecret));

        try {
            JSONObject resultJson = JSONUtil.parseObj(HttpRequest.post("https://cloud.elearnings.com/gateway/user/api/sms/send/sms")
                    .header("Content-Type", "application/json")
                    .body(JSONUtil.toJsonStr(map)).execute().body());
            if (!resultJson.getInt("code").equals(200)) {
                log.error("发送短信失败，错误信息：{}, 手机号：{}， 模板参数：{}", resultJson, phone, templateParam);
                return false;
            }
            return true;
        } catch (Exception e) {
            log.error("发送短信错误：{}", e.getMessage());
            return false;
        }
    }


    /**
     * 发送短信到指定手机号使用阿里云短信服务
     *
     * @param phone           发送短信的手机号
     * @param templateParam   短信模板参数
     * @param signName        签名名称
     * @param templateCode    模板编码
     * @param accessKeyId     阿里云访问密钥ID
     * @param accessKeySecret 阿里云访问密钥Secret
     * @return 发送成功返回true，否则返回false
     */
    private static Boolean aliyun(String phone, String templateParam, String signName, String templateCode, String accessKeyId, String accessKeySecret) {
        // 设置网络连接超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        // 创建阿里云账号信息
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);

        // 创建阿里云通用客户端
        DefaultAcsClient acsClient = new DefaultAcsClient(profile);

        // 创建请求对象
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", templateParam);

        try {
            JSONObject resultJson = JSONUtil.parseObj(acsClient.getCommonResponse(request).getData());
            if (!"OK".equals(resultJson.getStr("Code"))) {
                log.error("发送短信失败，错误信息：{}, 手机号：{}， 模板参数：{}", resultJson, phone, templateParam);
                return false;
            }
            return true;
        } catch (Exception e) {
            log.error("发送短信错误：{}", e.getMessage());
            return false;
        }
    }

    private static String sign(Map<String, Object> paramMap, String key) {
        // 将参数按照排序顺序转换为TreeMap
        SortedMap<String, Object> treeMap = new TreeMap<>(paramMap);
        StringBuilder sb = new StringBuilder();

        // 遍历TreeMap中的每个键值对
        for (Map.Entry<String, Object> entry : treeMap.entrySet()) {
            // 如果键不是 "sign" 且对应的值不为空，则将其拼接到 StringBuilder 中
            if (!"sign".equals(entry.getKey()) && StrUtil.isNotBlank(String.valueOf(entry.getValue()))) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }

        // 拼接"key="和传入的key，并使用MD5算法进行签名
        sb.append("key=").append(key);
        return SecureUtil.md5(sb.toString());
    }

    private static String getParamForCode(String code) {
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        return JSUtil.toJsonString(map);
    }

}
