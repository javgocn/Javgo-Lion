package cn.javgo.lion.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * description：JSON 通用配置
 *
 * @author javgo.cn
 * @date 2023/12/28
 */
@Configuration
public class JsonConfig {

    /**
     * 配置 Jackson ObjectMapper 对象
     */
    @Bean
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        // 创建一个 Xml 格式的 ObjectMapper 对象
        ObjectMapper mapper = builder.createXmlMapper(false).build();
        // 创建一个 SimpleModule 对象
        SimpleModule simpleModule = new SimpleModule();

        // 向 simpleModule 中添加 Long 类的 serializer
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);

        // 将 simpleModule 注册到 mapper 中
        mapper.registerModule(simpleModule);
        return mapper;
    }

}
