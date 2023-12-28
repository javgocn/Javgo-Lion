package cn.javgo.lion.common.core.tools;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * JWT 工具类
 *
 * @author javgo.cn
 * @date 2023/12/27
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JWTUtil {

    /**
     * JWT 秘钥（后期考虑配置到 Nacos 配置中心）
     */
    private static final String TOKEN_SECRET = "eyJhbGciOiJIUzI1NiJ9";

    /**
     * JWT 签发者（后期考虑配置到 Nacos 配置中心）
     */
    private static final String ISSUER = "JAVGOLION";

    /**
     * JWT 自定义字段(用户ID)
     */
    public static final String USERID = "userId";

    /**
     * JWT 有效期(一个月，单位毫秒)
     */
    public static final Long EXPIRATION = 30 * 24 * 3600 * 1000L;

    /**
     * 创建 JWT
     *
     * @param userId     用户ID
     * @param expiration 有效期
     * @return Token
     */
    public static String create(Long userId, Long expiration) {
        try {
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withClaim(USERID, userId.toString())
                    .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (Exception e) {
            log.error("JWT 生成失败", e);
            return "";
        }
    }

    /**
     * 校验 JWT
     *
     * @param token token
     * @return JWT
     */
    public static DecodedJWT verify(String token) {
        return JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer(ISSUER).build().verify(token);
    }

    /**
     * 获取用户ID
     *
     * @param token token
     * @return 用户ID
     */
    public static Long getUserId(String token) {
        return Long.valueOf(verify(token).getClaim(USERID).asString());
    }
}
