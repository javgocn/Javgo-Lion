package cn.javgo.lion.mybatis.generator.plugins;

import org.mybatis.generator.api.PluginAdapter;

import java.util.List;

/**
 * description：合并插件
 *
 * @author javgo.cn
 * @date 2023/12/29
 */
public class IsMergeablePlugin extends PluginAdapter {
    @Override
    public boolean validate(List<String> list) {
        return true;
    }

}
