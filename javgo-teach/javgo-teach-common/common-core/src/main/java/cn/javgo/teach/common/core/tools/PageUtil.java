package cn.javgo.teach.common.core.tools;

import cn.hutool.core.bean.BeanUtil;
import cn.javgo.teach.common.core.base.Page;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 分页工具类
 *
 * @author javgo.cn
 * @date 2023/12/24
 */
@Slf4j
public class PageUtil<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 默认每页显示记录数
     */
    public static final int DEFAULT_PAGE_SIZE = 20;

    /**
     * 最大每页显示记录数
     */
    public static final int MAX_PAGE_SIZE = 1000;

    private PageUtil() {
    }

    /**
     * 转换分页
     *
     * @param page  分页
     * @param clazz 集合元素类型
     * @param <T>   泛型
     * @return 分页
     */
    public static <T extends Serializable> Page<T> transform(Page<T> page, Class<T> clazz) {
        Page<T> tPage = new Page<>();
        try {
            tPage.setList(copyList(page.getList(), clazz));
        } catch (Exception e) {
            log.error("transform error", e);
        }
        tPage.setPageCurrent(page.getPageCurrent());
        tPage.setPageSize(page.getPageSize());
        tPage.setTotalCount(page.getTotalCount());
        tPage.setTotalPage(page.getTotalPage());
        return tPage;
    }

    /**
     * 复制集合
     *
     * @param source 源集合
     * @param clazz  源集合元素类型
     * @param <T>    泛型
     * @return 目标集合
     */
    public static <T> List<T> copyList(List<?> source, Class<T> clazz) {
        if (CollectionUtils.isEmpty(source)) {
            return Lists.newArrayList();
        }
        List<T> target = new ArrayList<>(source.size());
        for (Object o : source) {
            try {
                T t = clazz.newInstance();
                target.add(t);
                BeanUtil.copyProperties(o, t);
            } catch (Exception e) {
                log.error("copyList error", e);
            }
        }
        return target;
    }

    /**
     * 检查 sql 注入
     *
     * @param sql sql
     * @return 正常返回原sql，否则返回空字符串
     */
    public static String checkSql(String sql) {
        String injectionStr = "'|and|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare|;|or|+|,";
        String[] injectionArr = injectionStr.split("\\|");
        for (String str : injectionArr) {
            if (sql.contains(str)) {
                return "";
            }
        }
        return sql;
    }

  /**
   * 计算总页数
   * @param totalCount 总记录数
   * @param pageSize 每页记录数
   * @return 总页数
   */
    public static int countTotalPage(final int totalCount, final int pageSize) {
        if (totalCount == 0) {
            return 1;
        }
        if (totalCount % pageSize == 0) {
            return totalCount / pageSize;
        }
        return totalCount / pageSize + 1;
    }
}
