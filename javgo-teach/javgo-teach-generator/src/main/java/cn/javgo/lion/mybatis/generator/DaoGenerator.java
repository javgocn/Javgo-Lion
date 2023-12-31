package cn.javgo.lion.mybatis.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * description：DAO 生成器
 *
 * @author javgo.cn
 * @date 2023/12/30
 */
public class DaoGenerator {

    public static void main(String[] args) throws Exception {
        System.out.println("Start mybatis generator...");
        // 生成的警告信息
        List<String> warnings = new ArrayList<>();
        // 是否覆盖原有代码
        boolean overwrite = true;
        // 读取配置文件
        File configFile = new File(DaoGenerator.class.getResource("/mybatis-config.xml").getFile());
        // 配置解析器
        ConfigurationParser configurationParser = new ConfigurationParser(warnings);
        // 解析配置文件
        Configuration configuration = configurationParser.parseConfiguration(configFile);
        // 默认回调
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        // 创建 mybatis 生成器
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(configuration, callback, warnings);
        // 执行生成
        myBatisGenerator.generate(null);
        System.out.println("Finish mybatis generator");
    }
}
