package cn.javgo.lion.mybatis.generator;

import cn.javgo.lion.mybatis.generator.util.ConfigUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.*;

/**
 * description：生成代码主入口。
 * <p>
 * 主要使用了 MyBatis Plus 的代码生成器 AutoGenerator。以下是其主要步骤：<br/>
 * 1.首先，定义一个静态方法 runCreate()，这是生成代码的主要入口。<br/>
 * 2.在 runCreate() 方法中，首先创建一个 AutoGenerator 对象，这是 MyBatis Plus 的代码生成器。<br/>
 * 3.然后，设置全局配置 GlobalConfig，包括是否覆盖已有文件、是否设置 swagger2 注解、作者和设置输出路径等。<br/>
 * 4.接着，设置数据库配置 DataSourceConfig，包括设置数据库类型、设置数据库驱动、设置数据库连接地址、设置数据库用户名和密码以及设置类型转换器等。<br/>
 * 5.然后，设置策略配置 StrategyConfig，包括设置表名映射到实体类名的命名策略、设置表字段映射到实体类属性名的命名策略、设置 lombok 模型、
 *   设置生成 @RestController 控制器、设置生成的实体类继承的父类全称等。<br/>
 * 6.接着，设置模版配置 TemplateConfig，设置不生成 controller、entity、mapper、xml、service、serviceImpl 文件。<br/>
 * 7.然后，设置自定义模版 InjectionConfig，并设置了自定义输出配置。<br/>
 * 8.最后，执行生成 autoGenerator.execute()。
 * <p>
 * 其中涉及到一些其他的私有方法，如 typeConvert()、cfg()、forList() 等，这些方法主要用于自定义类型转换器、自定义模版和自定义输出配置等。
 * 总的来说，ControllerGenerator 的主要作用是通过配置 MyBatis Plus 的代码生成器 AutoGenerator，根据数据库表结构自动生成对应的代码。
 *
 * @author javgo.cn
 * @date 2023/12/28
 */
@Slf4j
public class ControllerGenerator {

    public static void main(String[] args) {
        ControllerGenerator.runCreate();
    }

    /**
     * 生成代码
     */
    public static void runCreate() {
        log.debug("准备开始执行创建所有文件...");
        log.debug("DbConfig.url = " + ConfigUtil.URL);
        log.debug("DbConfig.driverName = " + ConfigUtil.DB_CLASS);
        log.debug("DbConfig.username = " + ConfigUtil.USERNAME);
        log.debug("DbConfig.password = " + ConfigUtil.PASSWORD);

        // 代码生成器
        AutoGenerator autoGenerator = new AutoGenerator();

        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        // 是否覆盖已有文件
        globalConfig.setFileOverride(true);
        // 是否设置实体类的 swagger2 注解
        globalConfig.setSwagger2(true);
        // 作者
        globalConfig.setAuthor(ConfigUtil.AUTHOR);
        // 设置输出路径
        globalConfig.setOutputDir(ConfigUtil.PROJECT_PATH);
        // 为代码生成器设置全局配置
        autoGenerator.setGlobalConfig(globalConfig);

        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        // 设置数据库类型
        dataSourceConfig.setDbType(DbType.MYSQL);
        // 设置数据库驱动
        dataSourceConfig.setDriverName(ConfigUtil.DB_CLASS);
        // 设置数据库连接地址
        dataSourceConfig.setUrl(ConfigUtil.URL);
        // 设置数据库用户名
        dataSourceConfig.setUsername(ConfigUtil.USERNAME);
        // 设置数据库密码
        dataSourceConfig.setPassword(ConfigUtil.PASSWORD);
        // 设置类型转换器
        dataSourceConfig.setTypeConvert(typeConvert());
        // 为代码生成器设置数据库配置
        autoGenerator.setDataSource(dataSourceConfig);

        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        // 设置表名映射到实体类名的命名策略(下划线转驼峰)
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        // 设置表字段映射到实体类属性名的命名策略(下划线转驼峰)
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        // 设置 lombok 模型
        strategyConfig.setEntityLombokModel(true);
        // 设置生成 @RestController 控制器
        strategyConfig.setRestControllerStyle(true);
        // 设置生成的实体类继承的父类全称
        strategyConfig.setEntityTableFieldAnnotationEnable(true);
        // 如果 config.properties 配置文件中设置了表名，则直接使用该配置
        if (Objects.nonNull(ConfigUtil.TABLE_NAMES) && !"".equals(ConfigUtil.TABLE_NAMES.trim()) && !"%".equals(ConfigUtil.TABLE_NAMES.trim())) {
            strategyConfig.setInclude(ConfigUtil.TABLE_NAMES.split(","));
        }
        // 为代码生成器设置策略配置
        autoGenerator.setStrategy(strategyConfig);

        // 自定义代码模版配置
        TemplateConfig templateConfig = new TemplateConfig();
        // 设置不生成 controller、entity、mapper、xml、service、serviceImpl 文件
        templateConfig.setController(null);
        templateConfig.setEntity(null);
        templateConfig.setMapper(null);
        templateConfig.setXml(null);
        templateConfig.setService(null);
        templateConfig.setServiceImpl(null);
        // 为代码生成器设置模版引擎配置
        autoGenerator.setTemplate(templateConfig);

        // 自定义属性注入
        autoGenerator.setCfg(cfg());

        // MyBatis Plus 默认模版引擎为 Velocity，这里指定为 Freemarker
        autoGenerator.setTemplateEngine(new FreemarkerTemplateEngine());

        // 执行生成
        autoGenerator.execute();
    }

    /**
     * 自定义类型转换器
     */
    private static MySqlTypeConvert typeConvert() {
        return new MySqlTypeConvert() {
            @Override
            public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                // 将数据库中的 tinyint(1) 转换为 Boolean 类型
                if (fieldType.contains("tinyint")) {
                    if ("tinyint(1)".equalsIgnoreCase(fieldType)) {
                        return DbColumnType.BOOLEAN;
                    }
                    // 不满足上述条件时，将字段的类型设置为 Integer 类型
                    return DbColumnType.INTEGER;
                }
                return super.processTypeConvert(globalConfig, fieldType);
            }
        };
    }

    /**
     * 自定义属性注入
     */
    private static InjectionConfig cfg() {
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                // 项目名称
                map.put("projectName", ConfigUtil.PROJECT_NAME);
                // 项目包前缀
                map.put("packagePrefix", ConfigUtil.PACKAGE_PREFIX);
                // 项目包名
                map.put("packageName", ConfigUtil.PACKAGE_NAME);
                this.setMap(map);
            }
        };
        // 自定义输出配置
        injectionConfig.setFileOutConfigList(forList());
        return injectionConfig;
    }

    /**
     * 自定义输出配置
     */
    private static List<FileOutConfig> forList() {
        List<FileOutConfig> focList = new ArrayList<>();

        // service.dao
        focList.addAll(serviceDaoList());

        // service.api
        // focList.addAll(serviceApiList());

        // service.auth
        focList.addAll(serviceAuthList());

        // service.admin
        focList.addAll(serviceAdminList());

        // feign
        // focList.addAll(feignList());

        // service.feign
        // focList.addAll(serviceFeignList());

        return focList;
    }

    /**
     * service - dao 模块<br/>
     * 在 MyBatis Plus 中，FileOutConfig 用于指定模板文件（.ftl文件）和生成文件的路径与命名规则。每个FileOutConfig实例都代表一个特定的文件输出配置。<br/>
     * FreeMarker 模板文件（以 .ftl 为扩展名）是一种用于生成文本输出（如源代码、XML 文件等）的模板。在 MyBatis Plus 或类似的代码生成工具中，
     * .ftl 文件被用来定义生成的源代码文件的格式和内容。这些模板文件利用 FreeMarker 模板语言，允许动态插入变量、执行条件判断和循环等操作，
     * 以生成定制化的代码文件。
     */
    private static List<FileOutConfig> serviceDaoList() {
        // 项目路径：项目所在磁盘路径 + 项目名称
        final String path = ConfigUtil.PROJECT_PATH + File.separator + ConfigUtil.PROJECT_NAME;

        // 自定义输出配置
        List<FileOutConfig> fileOutConfigList = new ArrayList<>();

        // 添加 dao 文件
        fileOutConfigList.add(new FileOutConfig("/template/service/dao/impl/impl.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名，如果 Entity 设置了前后缀，此处的名称会跟着发生变化
                return path + "/src/main/java/" + ConfigUtil.PACKAGE_PATH + File.separator + ConfigUtil.PACKAGE_NAME + "/dao/impl/" +
                        tableInfo.getEntityName() + "DaoImpl.java";
            }
        });

        // 添加 dao 文件
        fileOutConfigList.add(new FileOutConfig("/template/service/dao/dao.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 生成的文件路径：项目路径 + 包路径 + 包名 + dao + 实体类名 + Dao.java
                return path + "/src/main/java/" + ConfigUtil.PACKAGE_PATH + File.separator + ConfigUtil.PACKAGE_NAME + "/dao/" +
                        tableInfo.getEntityName() + "Dao.java";
            }
        });

        return fileOutConfigList;
    }

    /**
     * service - api 模块<br/>
     * TIP：下面的一系列方法都是来生成一系列的 FileOutConfig 对象，这些对象定义了如何生成和输出特定的代码文件。每个 FileOutConfig 对象
     * 都对应一个特定的代码文件，包括其模板路径和输出路径。
     */
    private static List<FileOutConfig> serviceApiList() {
        // 项目路径：项目所在磁盘路径 + 项目名称
        final String path = ConfigUtil.PROJECT_PATH + File.separator + ConfigUtil.PROJECT_NAME;

        // 自定义输出配置
        List<FileOutConfig> fileOutConfigList = new ArrayList<>();

        // 生成 API 控制器文件：使用 FreeMarker 模板文件（路径为 "/template/service/api/api.controller.java.ftl"），并根据这个模板生成代码。
        fileOutConfigList.add(new FileOutConfig("/template/service/api/api.controller.java.ftl") {
            // 设置输出文件的路径：由项目路径、包路径、包名、服务名和实体类名共同决定。
            @Override
            public String outputFile(TableInfo tableInfo) {
                return path + "/src/main/java/" + ConfigUtil.PACKAGE_PATH + File.separator + ConfigUtil.PACKAGE_NAME + "/service/" +
                        "/api/Api" + tableInfo.getEntityName() + "Controller.java";
            }
        });

        fileOutConfigList.add(new FileOutConfig("/template/service/api/biz/api.biz.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return path + "/src/main/java/" + ConfigUtil.PACKAGE_PATH + File.separator + ConfigUtil.PACKAGE_NAME + "/service/" +
                        "/api/service/Api" + tableInfo.getEntityName() + "Biz.java";
            }
        });

        fileOutConfigList.add(new FileOutConfig("/template/service/api/api.req.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return path + "/src/main/java/" + ConfigUtil.PACKAGE_PATH + File.separator + ConfigUtil.PACKAGE_NAME + "/service/" +
                        "/api/req/Api" + tableInfo.getEntityName() + "Req.java";
            }
        });

        fileOutConfigList.add(new FileOutConfig("/template/service/api/api.resp.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return path + "/src/main/java/" + ConfigUtil.PACKAGE_PATH + File.separator + ConfigUtil.PACKAGE_NAME + "/service/" +
                        "/api/resp/Api" + tableInfo.getEntityName() + "Resp.java";
            }
        });

        return fileOutConfigList;
    }

    /**
     * service - auth 模块
     */
    private static List<FileOutConfig> serviceAuthList() {
        final String path = ConfigUtil.PROJECT_PATH + File.separator + ConfigUtil.PROJECT_NAME;

        List<FileOutConfig> fileOutConfigList = new ArrayList<>();

        fileOutConfigList.add(new FileOutConfig("/template/service/auth/auth.controller.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return path + "/src/main/java/" + ConfigUtil.PACKAGE_PATH + File.separator + ConfigUtil.PACKAGE_NAME + "/service/" +
                        "/auth/Auth" + tableInfo.getEntityName() + "Controller.java";
            }
        });

        fileOutConfigList.add(new FileOutConfig("/template/service/auth/biz/auth.biz.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return path + "/src/main/java/" + ConfigUtil.PACKAGE_PATH + File.separator + ConfigUtil.PACKAGE_NAME + "/service/" +
                        "/auth/service/Auth" + tableInfo.getEntityName() + "Biz.java";
            }
        });

        fileOutConfigList.add(new FileOutConfig("/template/service/auth/auth.req.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return path + "/src/main/java/" + ConfigUtil.PACKAGE_PATH + File.separator + ConfigUtil.PACKAGE_NAME + "/service/" +
                        "/auth/req/Auth" + tableInfo.getEntityName() + "Req.java";
            }
        });

        fileOutConfigList.add(new FileOutConfig("/template/service/auth/auth.resp.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return path + "/src/main/java/" + ConfigUtil.PACKAGE_PATH + File.separator + ConfigUtil.PACKAGE_NAME + "/service/" +
                        "/auth/resp/Auth" + tableInfo.getEntityName() + "Resp.java";
            }
        });

        return fileOutConfigList;
    }

    /**
     * service - admin 模块
     */
    private static List<FileOutConfig> serviceAdminList() {
        final String path = ConfigUtil.PROJECT_PATH + File.separator + ConfigUtil.PROJECT_NAME;

        List<FileOutConfig> fileOutConfigList = new ArrayList<>();

        fileOutConfigList.add(new FileOutConfig("/template/service/admin/admin.controller.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return path + "/src/main/java/" + ConfigUtil.PACKAGE_PATH + File.separator + ConfigUtil.PACKAGE_NAME + "/service/" +
                        "/admin/Admin" + tableInfo.getEntityName() + "Controller.java";
            }
        });

        fileOutConfigList.add(new FileOutConfig("/template/service/admin/biz/admin.biz.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return path + "/src/main/java/" + ConfigUtil.PACKAGE_PATH + File.separator + ConfigUtil.PACKAGE_NAME + "/service/" +
                        "/admin/service/Admin" + tableInfo.getEntityName() + "Biz.java";
            }
        });

        fileOutConfigList.add(new FileOutConfig("/template/service/admin/admin.req.save.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return path + "/src/main/java/" + ConfigUtil.PACKAGE_PATH + File.separator + ConfigUtil.PACKAGE_NAME + "/service/admin/req/" +
                        "Admin" + tableInfo.getEntityName() + "SaveReq.java";
            }
        });

        fileOutConfigList.add(new FileOutConfig("/template/service/admin/admin.req.page.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return path + "/src/main/java/" + ConfigUtil.PACKAGE_PATH + File.separator + ConfigUtil.PACKAGE_NAME + "/service/admin/req/" +
                        "Admin" + tableInfo.getEntityName() + "PageReq.java";
            }
        });

        fileOutConfigList.add(new FileOutConfig("/template/service/admin/admin.req.edit.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return path + "/src/main/java/" + ConfigUtil.PACKAGE_PATH + File.separator + ConfigUtil.PACKAGE_NAME + "/service/admin/req/" +
                        "Admin" + tableInfo.getEntityName() + "EditReq.java";
            }
        });

        fileOutConfigList.add(new FileOutConfig("/template/service/admin/admin.resp.view.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return path + "/src/main/java/" + ConfigUtil.PACKAGE_PATH + File.separator + ConfigUtil.PACKAGE_NAME + "/service/admin/resp/" +
                        "Admin" + tableInfo.getEntityName() + "ViewResp.java";
            }
        });

        fileOutConfigList.add(new FileOutConfig("/template/service/admin/admin.resp.page.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return path + "/src/main/java/" + ConfigUtil.PACKAGE_PATH + File.separator + ConfigUtil.PACKAGE_NAME + "/service/admin/resp/" +
                        "Admin" + tableInfo.getEntityName() + "PageResp.java";
            }
        });

        return fileOutConfigList;
    }

    /**
     * feign 模块
     */
    private static List<FileOutConfig> feignList() {
        // 替换 service 为 feign
        String projectPath = ConfigUtil.PROJECT_PATH.replace("service", "feign");
        String projectName = ConfigUtil.PROJECT_NAME.replace("service", "feign");

        String packagePath = ConfigUtil.PACKAGE_PATH.replace("service", "feign");
        String packageName = ConfigUtil.PACKAGE_NAME.replace("service", "feign");

        final String path = projectPath + File.separator + projectName;

        List<FileOutConfig> fileOutConfigList = new ArrayList<>();

        fileOutConfigList.add(new FileOutConfig("/template/feign/interfaces.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return path + "/src/main/java/" + packagePath + File.separator + packageName + "/feign/interfaces/IFeign" +
                        tableInfo.getEntityName() + ".java";
            }
        });

        fileOutConfigList.add(new FileOutConfig("/template/feign/qo.page.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return path + "/src/main/java/" + packagePath + File.separator + packageName + "/feign/interfaces/qo/" +
                        tableInfo.getEntityName() + "PageQO.java";
            }
        });

        fileOutConfigList.add(new FileOutConfig("/template/feign/qo.save.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return path + "/src/main/java/" + packagePath + File.separator + packageName + "/feign/interfaces/qo/" +
                        tableInfo.getEntityName() + "SaveQO.java";
            }
        });

        fileOutConfigList.add(new FileOutConfig("/template/feign/qo.edit.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return path + "/src/main/java/" + packagePath + File.separator + packageName + "/feign/interfaces/qo/" +
                        tableInfo.getEntityName() + "EditQO.java";
            }
        });

        fileOutConfigList.add(new FileOutConfig("/template/feign/vo.page.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return path + "/src/main/java/" + packagePath + File.separator + packageName + "/feign/interfaces/vo/" +
                        tableInfo.getEntityName() + "PageVO.java";
            }
        });

        fileOutConfigList.add(new FileOutConfig("/template/feign/vo.view.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return path + "/src/main/java/" + packagePath + File.separator + packageName + "/feign/interfaces/vo/" +
                        tableInfo.getEntityName() + "ViewVO.java";
            }
        });

        return fileOutConfigList;
    }

    /**
     * service - feign 模块
     */
    private static List<FileOutConfig> serviceFeignList() {
        final String path = ConfigUtil.PROJECT_PATH + File.separator + ConfigUtil.PROJECT_NAME;

        List<FileOutConfig> fileOutConfigList = new ArrayList<>();

        fileOutConfigList.add(new FileOutConfig("/template/service/feign/feign.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return path + "/src/main/java/" + ConfigUtil.PACKAGE_PATH + File.separator + ConfigUtil.PACKAGE_NAME + "/feign/Feign" +
                        tableInfo.getEntityName() + "Controller.java";
            }
        });

        fileOutConfigList.add(new FileOutConfig("/template/service/feign/biz/biz.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return path + "/src/main/java/" + ConfigUtil.PACKAGE_PATH + File.separator + ConfigUtil.PACKAGE_NAME + "/feign/service/Feign" +
                        tableInfo.getEntityName() + "Biz.java";
            }
        });

        return fileOutConfigList;
    }
}
