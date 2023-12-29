package cn.javgo.lion.mybatis.generator.util;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * description：入口配置文件
 *
 * @author javgo.cn
 * @date 2023/12/28
 */
@Slf4j
public class ConfigUtil {

    /**
     * 系统配置文件 config.properties
     */
    public static Map<String, String> CONFIG = new HashMap<>();

    static {
        try {
            // 加载 config.properties 配置文件
            ResourceBundle bundle = ResourceBundle.getBundle("config");
            bundle.keySet().forEach(key -> CONFIG.put(key, bundle.getString(key)));
        } catch (Exception e) {
            log.error("加载配置文件失败", e);
        }
    }

    // 数据库配置
    public final static String DB_CLASS = "com.mysql.cj.jdbc.Driver";
    public final static String URL = CONFIG.get("db.url");
    public final static String USERNAME = CONFIG.get("db.username");
    public final static String PASSWORD = CONFIG.get("db.password");

    // 作者
    public final static String AUTHOR = Objects.isNull(CONFIG.get("author")) ? "admin" : CONFIG.get("author");

    // 项目配置
    public final static String PROJECT_PATH = CONFIG.get("projectPath");
    public final static String PROJECT_NAME = CONFIG.get("projectName");
    public final static String PACKAGE_PREFIX = CONFIG.get("packagePrefix");
    public final static String PACKAGE_NAME = Objects.isNull(CONFIG.get("packageName")) ? "" : CONFIG.get("packageName");
    public final static String PACKAGE_PATH = PACKAGE_PREFIX.replace(".", "/");
    public final static String BOSS_PATH = PROJECT_NAME.replace(PACKAGE_NAME, "boss");

    // 表名
    public final static String TABLE_NAMES = CONFIG.get("tableNames");
}
