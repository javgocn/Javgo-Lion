package cn.javgo.lion.mybatis.generator.plugins;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.ShellRunner;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

/**
 * description：分页插件
 *
 * @author javgo.cn
 * @date 2023/12/29
 */
public class PaginationPlugin extends PluginAdapter {

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
     * 当生成模型示例类时，会调用此方法。
     * 它会向生成的类中添加 "limitStart" 和 "pageSize" 两个字段。
     *
     * @param topLevelClass     生成的模型示例类。
     * @param introspectedTable 生成模型示例类所基于的表。
     * @return 调用父类的同名方法，并返回其结果。
     */
    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        // 添加 "limitStart" 字段
        addLimit(topLevelClass, introspectedTable, "limitStart");
        // 添加 "pageSize" 字段
        addLimit(topLevelClass, introspectedTable, "pageSize");
        return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
    }

    /**
     * 当生成不包含 BLOBs 的 SelectByExample SQL 映射时，会调用此方法。
     * 它会向生成的 SQL 映射中添加一个 if 元素，用于实现分页查询。
     *
     * @param element           生成的 SelectByExample SQL 映射。
     * @param introspectedTable SQL 映射所基于的表。
     * @return 调用父类的 sqlMapUpdateByExampleWithoutBLOBsElementGenerated 方法，并返回其结果。
     */
    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        // 创建一个 if 元素
        XmlElement ifElement = new XmlElement("if");
        // 为 if 元素添加一个测试条件
        ifElement.addAttribute(new Attribute("test", "limitStart >= 0"));
        // 为 if 元素添加一个文本元素，用于生成分页查询的 SQL 语句
        ifElement.addElement(new TextElement("limit ${limitStart} , ${pageSize}"));
        // 将 if 元素添加到 SQL 映射中
        element.getElements().add(ifElement);
        return super.sqlMapUpdateByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
    }

    /**
     * 向指定的类中添加一个字段。
     * 该字段的名称由参数 name 指定，类型为 int，初始值为 -1。
     * 同时，还会为该字段生成对应的 getter 和 setter 方法。
     *
     * @param topLevelClass     需要添加字段的类。
     * @param introspectedTable 类所基于的表。
     * @param name              需要添加的字段的名称。
     */
    private void addLimit(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, String name) {
        // 获取注释生成器
        CommentGenerator commentGenerator = context.getCommentGenerator();

        // 创建一个新的字段
        Field field = new Field("foo", FullyQualifiedJavaType.getIntInstance());
        field.setVisibility(JavaVisibility.PROTECTED);
        field.setType(FullyQualifiedJavaType.getIntInstance());
        field.setName(name);
        field.setInitializationString("-1");
        // 为新创建的字段添加注释
        commentGenerator.addFieldComment(field, introspectedTable);
        // 将新创建的字段添加到类中
        topLevelClass.addField(field);

        // 将字段名称的首字母转换为大写，用于生成 getter 和 setter 方法的名称
        char c = name.charAt(0);
        String camel = Character.toUpperCase(c) + name.substring(1);

        // 创建 setter 方法
        Method method = new Method("bar");
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("set" + camel);
        method.addParameter(new Parameter(FullyQualifiedJavaType.getIntInstance(), name));
        method.addBodyLine("this." + name + "=" + name + ";");
        // 为 setter 方法添加注释
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        // 将 setter 方法添加到类中
        topLevelClass.addMethod(method);

        // 创建 getter 方法
        method = new Method("bar");
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.setName("get" + camel);
        method.addBodyLine("return " + name + ";");
        // 为 getter 方法添加注释
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        // 将 getter 方法添加到类中
        topLevelClass.addMethod(method);
    }

    /**
     * 生成 MyBatis 配置文件。
     * 首先，从类路径中获取 "mybatisConfig.xml" 文件的路径。
     * 然后，创建一个包含两个元素的字符串数组，第一个元素是 "-configfile"，第二个元素是配置文件的路径。
     * 最后，调用 ShellRunner 的 main 方法，传入刚刚创建的字符串数组，生成 MyBatis 配置文件。
     */
    public static void generate() {
        // 从类路径中获取 "mybatisConfig.xml" 文件的路径
        String config = PaginationPlugin.class.getClassLoader().getResource("mybatisConfig.xml").getFile();
        // 创建一个包含两个元素的字符串数组
        String[] arg = {"-configfile", config, "-overwrite"};
        // 调用 ShellRunner 的 main 方法，生成 MyBatis 配置文件
        ShellRunner.main(arg);
    }

    /**
     * 程序的入口方法。
     * 调用 generate 方法，生成 MyBatis 配置文件。
     *
     * @param args 命令行参数。在本方法中未使用。
     */
    public static void main(String[] args) {
        // 调用 generate 方法，生成 MyBatis 配置文件
        generate();
    }
}
