package cn.javgo.lion.mybatis.generator.plugins;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;
import java.util.Properties;

/**
 * description：注解插件
 *
 * @author javgo.cn
 * @date 2023/12/29
 */
public class AnnotationPlugin extends PluginAdapter {

    /**
     * 注解的类名
     */
    private String annotationClass;

    /**
     * 注解的名称
     */
    private String annotationName;

    /**
     * 校验插件是否合法
     */
    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    /**
     * 设置属性
     */
    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        annotationClass = String.valueOf(properties.getProperty("annotationClass"));
        annotationName = String.valueOf(properties.getProperty("annotationName"));
    }

    /**
     * 客户端生成
     */
    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        // 创建一个指定注解的类名的类型对象
        FullyQualifiedJavaType importedType = new FullyQualifiedJavaType(annotationClass);
        // 将创建的类型对象添加到接口的导入类型列表中
        interfaze.addImportedType(importedType);
        // 将注解的名称添加到接口中
        interfaze.addAnnotation(annotationName);
        return true;
    }

}
