package cn.javgo.lion.mybatis.generator.engine;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * description：freeMarker 模板引擎
 *
 * @author javgo.cn
 * @date 2023/12/29
 */
public class ViewTemplateEngine extends AbstractTemplateEngine {

    /**
     * freemarker 配置信息
     */
    private Configuration configuration;

    /**
     * 初始化
     */
    @Override
    public AbstractTemplateEngine init(ConfigBuilder configBuilder) {
        // 调用父类的init方法初始化
        super.init(configBuilder);
        // 创建 Freemarker 的 Configuration 对象
        configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        // 设置默认编码为 UTF-8
        configuration.setDefaultEncoding(ConstVal.UTF8);
        // 将当前类路(/)径添加到 Freemarker 的搜索路径中
        configuration.setClassForTemplateLoading(FreemarkerTemplateEngine.class, StringPool.SLASH);
        return this;
    }

    /**
     * 执行批量输出操作 (生成 js 文件和页面)
     */
    @Override
    public AbstractTemplateEngine batchOutput() {
        // 获取所有表的信息
        List<TableInfo> tableInfoList = getConfigBuilder().getTableInfoList();
        Map<String, Object> jsObjectMap = getTableListMap(tableInfoList);
        InjectionConfig injectionConfig = getConfigBuilder().getInjectionConfig();

        // 自定义：生成 js 文件
        generateFiles(tableInfoList, injectionConfig, jsObjectMap, true);
        // 自定义：生成页面
        generateFiles(tableInfoList, injectionConfig, null, false);
        return this;
    }


    /**
     * 根据指定的模板路径和输出文件路径，将数据对象对象渲染成指定模板文件
     *
     * @param objectMap 数据对象Map，用于渲染模板
     * @param templatePath 模板文件路径
     * @param outputFile 输出文件路径
     * @throws Exception 如果渲染或写入文件过程中发生异常
     */
    @Override
    public void writer(Map<String, Object> objectMap, String templatePath, String outputFile) throws Exception {
        Template template = configuration.getTemplate(templatePath);
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
            // 将数据对象渲染到模板，并写入输出文件
            template.process(objectMap, new OutputStreamWriter(fileOutputStream, ConstVal.UTF8));
        }
        logger.debug("模板 {} 生成成功！文件路径：{}", templatePath, outputFile);
    }

    /**
     * 将给定的文件路径作为模板文件路径返回
     *
     * @param filePath 文件路径
     * @return 模板文件路径
     */
    @Override
    public String templateFilePath(String filePath) {
        return filePath + ".ftl";
    }

    /**
     * 获取表的列表的 Map 对象
     * @param tableInfoList 表的列表
     * @return 包含表的列表的Map对象
     */
    public Map<String, Object> getTableListMap(List<TableInfo> tableInfoList) {
        HashMap<String, Object> objectMap = new HashMap<>(30);
        ConfigBuilder config = getConfigBuilder();
        GlobalConfig globalConfig = config.getGlobalConfig();

        objectMap.put("restControllerStyle", config.getStrategyConfig().isRestControllerStyle());
        objectMap.put("config", config);
        objectMap.put("package", config.getPackageInfo());
        objectMap.put("author", globalConfig.getAuthor());
        objectMap.put("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        objectMap.put("tableList", tableInfoList);

        return Objects.isNull(config.getInjectionConfig()) ? objectMap : config.getInjectionConfig().prepareObjectMap(objectMap);
    }

    /**
     * 根据给定的表信息列表、注入配置、JavaScript对象映射等生成文件。
     * @param tableInfoList 表信息列表
     * @param injectionConfig 注入配置
     * @param jsObjectMap JavaScript对象映射
     * @param isJsFile 是否为JavaScript文件
     */
    private void generateFiles(List<TableInfo> tableInfoList, InjectionConfig injectionConfig, Map<String, Object> jsObjectMap, boolean isJsFile) {
        // 判断注入配置和文件输出配置列表是否为空
        if (Objects.nonNull(injectionConfig) && CollectionUtils.isNotEmpty(injectionConfig.getFileOutConfigList())) {
            // 遍历文件输出配置列表
            injectionConfig.getFileOutConfigList().forEach(foc -> {
                // 判断是否需要创建 JavaScript 文件
                boolean shouldCreateJs = isJsFile && foc.getTemplatePath().endsWith(".js.ftl");
                // 判断是否需要创建其他类型的文件
                boolean shouldCreateOther = !isJsFile && !foc.getTemplatePath().endsWith(".js.ftl");

                // 如果需要创建 JavaScript 文件或其他类型的文件
                if (shouldCreateJs || shouldCreateOther) {
                    // 获取对象映射
                    Map<String, Object> objectMap = isJsFile ? jsObjectMap : getObjectMap(tableInfoList, injectionConfig);
                    // 根据文件类型获取输出文件名
                    String outputFile = isJsFile ? foc.outputFile(null) : foc.outputFile(tableInfoList.get(0));
                    // 判断是否需要创建文件
                    if (isCreate(FileType.OTHER, outputFile)) {
                        try {
                            // 写入文件
                            writer(objectMap, foc.getTemplatePath(), outputFile);
                        } catch (Exception e) {
                            logger.error("模板引擎渲染失败，请检查配置信息", e);
                        }
                    }
                }
            });
        }
    }


    /**
     * 生成对象映射图
     * @param tableInfoList 表信息列表
     * @param injectionConfig 注入配置
     * @return 对象映射图
     */
    private Map<String, Object> getObjectMap(List<TableInfo> tableInfoList, InjectionConfig injectionConfig) {
        Map<String, Object> objectMap = new HashMap<>();
        tableInfoList.forEach(tableInfo -> {
            injectionConfig.initTableMap(tableInfo);
            objectMap.put("cfg", injectionConfig);
        });
        return objectMap;
    }

}
