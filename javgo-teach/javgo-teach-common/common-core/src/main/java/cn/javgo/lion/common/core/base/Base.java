package cn.javgo.lion.common.core.base;

import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基础类
 *
 * @author javgo.cn
 * @date 2023/12/24
 */
public class Base {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    public void log(Object obj) {
        log.info(JSONUtil.toJsonPrettyStr(obj));
    }
}
