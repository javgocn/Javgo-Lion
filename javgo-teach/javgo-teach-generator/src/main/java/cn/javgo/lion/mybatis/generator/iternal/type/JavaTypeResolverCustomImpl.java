package cn.javgo.lion.mybatis.generator.iternal.type;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * description：Java 类型解析器
 *
 * @author javgo.cn
 * @date 2023/12/29
 */
public class JavaTypeResolverCustomImpl extends JavaTypeResolverDefaultImpl {
    public JavaTypeResolverCustomImpl() {
        super();

        // TINYINT -> Integer
        super.typeMap.put(Types.TINYINT, new JdbcTypeInformation("TINYINT", new FullyQualifiedJavaType(Integer.class.getName())));
        // TIMESTAMP -> LocalDateTime
        super.typeMap.put(Types.TIMESTAMP, new JdbcTypeInformation("TIMESTAMP", new FullyQualifiedJavaType(LocalDateTime.class.getName())));
        // DATE -> LocalDate
        super.typeMap.put(Types.DATE, new JdbcTypeInformation("DATE", new FullyQualifiedJavaType(LocalDate.class.getName())));
        // TIME -> LocalTime
        super.typeMap.put(Types.TIME, new JdbcTypeInformation("TIME", new FullyQualifiedJavaType(LocalTime.class.getName())));
    }
}





