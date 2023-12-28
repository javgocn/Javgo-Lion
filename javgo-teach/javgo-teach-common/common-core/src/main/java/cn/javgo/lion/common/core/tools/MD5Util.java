package cn.javgo.lion.common.core.tools;

import cn.hutool.core.util.StrUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * MD5 加密工具类
 *
 * @author javgo.cn
 * @date 2023/12/27
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MD5Util {

    private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String md5(String... text) {
        if (text == null || text.length == 0 || Arrays.stream(text).allMatch(StrUtil::isBlank)) {
            throw new IllegalArgumentException("Provided text is empty or null");
        }
        String combinedText = String.join("", text);
        return md5(combinedText, StandardCharsets.UTF_8);
    }

    public static String md5(String text, Charset charset) {
        MessageDigest msgDigest;
        try {
            msgDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            log.error("MD5 algorithm not supported", e);
            throw new IllegalStateException("System does not support MD5 encryption algorithm");
        }
        msgDigest.update(text.getBytes(charset));
        byte[] digested = msgDigest.digest();
        return new String(encodeHex(digested));
    }

    private static char[] encodeHex(byte[] data) {
        int l = data.length;
        char[] out = new char[l << 1];
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS[0x0F & data[i]];
        }
        return out;
    }
}
