package cn.javgo.teach.common.core.tools;

import cn.hutool.core.bean.BeanUtil;
import cn.javgo.teach.common.core.base.Page;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     *
     * @param totalCount 总记录数
     * @param pageSize   每页记录数
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

    /**
     * 检查当前页码<br>
     * 校验逻辑：<br>
     * 1、根据总记录数（totalCount）和每页记录数（pageSize）计算总页数（totalPage）<br>
     * 2、判断页面提交的当前页数（pageCurrent）是否大于总页数（totalPage），大于则返回最后一页（totalPage）<br>
     * 3、判断页面提交的当前页数（pageCurrent）是否小于 1，小于则返回第一页（1）<br>
     * 4、如果都符合，则返回页面提交的当前页数（pageCurrent）
     *
     * @param totalCount  总记录数
     * @param pageSize    每页记录数
     * @param pageCurrent 当前页码
     * @return 当前页码
     */
    public static int checkPageCurrent(int totalCount, int pageSize, int pageCurrent) {
        int totalPage = countTotalPage(totalCount, pageSize);
        if (pageCurrent > totalPage) {
            if (totalCount < 1) {
                return 1;
            }
            return totalPage;
        } else if (pageCurrent < 1) {
            return 1;
        }
        return pageCurrent;
    }

    /**
     * 检查每页显示记录数<br>
     * 校验逻辑：<br>
     * 1、如果页面输入的每页记录数（pageSize）大于最大每页显示记录数（MAX_PAGE_SIZE），则返回最大每页显示记录数（MAX_PAGE_SIZE）<br>
     * 2、如果页面输入的每页记录数（pageSize）小于等于 0，则返回默认每页显示记录数（DEFAULT_PAGE_SIZE）
     *
     * @param pageSize 每页显示记录数
     * @return 每页显示记录数
     */
    public static int checkPageSize(int pageSize) {
        if (pageSize > MAX_PAGE_SIZE) {
            return MAX_PAGE_SIZE;
        } else if (pageSize < 1) {
            return DEFAULT_PAGE_SIZE;
        }
        return pageSize;
    }

    /**
     * 计算当前分页开始记录的偏移量
     *
     * @param pageCurrent 当前页
     * @param pageSize    每页记录数
     * @return 偏移量
     */
    public static int countOffset(final int pageCurrent, final int pageSize) {
        return (pageCurrent - 1) * pageSize;
    }

    /**
     * 根据总记录书校验页面传入的分页参数，返回分页的sql
     *
     * @param totalCount  总记录数
     * @param pageSize    每页记录数
     * @param pageCurrent 当前页
     * @return 分页sql
     */
    public static String limitSql(int totalCount, int pageCurrent, int pageSize) {
        pageCurrent = checkPageCurrent(totalCount, pageSize, pageCurrent);
        pageSize = checkPageSize(pageSize);
        return " LIMIT " + countOffset(pageCurrent, pageSize) + "," + pageSize;
    }

    /**
     * 根据分页查询 sql 语句，返回 count sql 语句
     *
     * @param sql 分页查询 sql 语句
     * @return count sql 语句
     */
    public static String countSql(String sql) {
        String countSql = sql.substring(sql.indexOf("FROM"));
        return "SELECT COUNT(*) " + removeOrderBy(countSql);
    }

    /**
     * 移除 sql 语句中的 order by 子句（用于分页前获取总记录数，此时不需要排序）
     *
     * @param sql sql 语句
     * @return 移除 order by 子句后的 sql 语句
     */
    private static String removeOrderBy(String sql) {
        Pattern pattern = Pattern.compile("ORDER\\s*BY[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(sql);
        StringBuffer stringBuilder = new StringBuffer();
        // 匹配到 order by 子句，则从字符串中删除
        while (matcher.find()) {
            matcher.appendReplacement(stringBuilder, "");
        }
        // 追加剩余的字符
        matcher.appendTail(stringBuilder);
        return stringBuilder.toString();
    }

    /**
     * 构建全部模糊查询
     * @param combine 组合字符串
     * @return 模糊查询字符串
     */
    public static String like(String combine) {
        return "%" + combine + "%";
    }

    /**
     * 构建右模糊查询
     * @param combine 组合字符串
     * @return 模糊查询字符串
     */
    public static String rightLike(String combine) {
        return combine + "%";
    }

}
