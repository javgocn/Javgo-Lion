package cn.javgo.lion.mybatis.generator.plugins;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

import java.lang.reflect.Field;
import java.util.List;

/**
 * description：合并插件
 *
 * @author javgo.cn
 * @date 2023/12/29
 */
public class IsMergeablePlugin extends PluginAdapter {

    /**
     * 验证输入的列表。
     *
     * @param list 需要验证的列表。
     * @return true，表示列表是有效的。
     */
    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    /**
     * 当生成 SQL 映射时，会调用此方法。
     * 它将生成的 SQL 映射的 'isMergeable' 字段修改为 false。
     * 这意味着生成的 SQL 映射不会与任何现有的 SQL 映射合并。
     *
     * @param sqlMap            生成的 SQL 映射。
     * @param introspectedTable SQL 映射基于的表。
     * @return true，表示 SQL 映射生成成功。
     */
    @Override
    public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
        try {
            Field field = sqlMap.getClass().getDeclaredField("isMergeable");
            field.setAccessible(true);
            field.setBoolean(sqlMap, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

}
