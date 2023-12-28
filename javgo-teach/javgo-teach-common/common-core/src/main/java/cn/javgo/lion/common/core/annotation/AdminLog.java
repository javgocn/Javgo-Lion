package cn.javgo.lion.common.core.annotation;

import java.lang.annotation.*;

/**
 * description：系统日志注解
 *
 * @author javgo.cn
 * @date 2023/12/28
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AdminLog {

    String value() default "操作日志";
}
