package cn.javgo.lion.common.jdbc;

import cn.javgo.lion.common.core.base.Page;
import cn.javgo.lion.common.core.tools.PageUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * description：Spring JDBC 基础类，提供常用的数据库操作方法
 *
 * @author javgo.cn
 * @date 2023/12/28
 */
public class AbstractBaseJdbc {

    @Resource
    protected JdbcTemplate jdbcTemplate;

    @Resource
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 获取最后插入记录的ID。
     *
     * @return 最后插入记录的ID
     */
    public Long getLastId() {
        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
    }

    /**
     * 根据 SQL 语句查询单个对象。
     *
     * @param sql SQL 语句
     * @param clazz 返回对象的类
     * @param args SQL 参数
     * @param <T> 返回类型泛型
     * @return 查询到的对象
     */
    public <T> T queryForObject(String sql, Class<T> clazz, Object... args) {
        Assert.hasText(sql, "SQL statement must not be empty");
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(clazz), args);
    }

    /**
     * 根据 SQL 语句查询对象列表。
     *
     * @param sql SQL 语句
     * @param clazz 返回对象列表的元素类型
     * @param args SQL 参数
     * @param <T> 返回类型泛型
     * @return 查询到的对象列表
     */
    public <T> List<T> queryForObjectList(String sql, Class<T> clazz, Object... args) {
        Assert.hasText(sql, "SQL statement must not be empty");
        return jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<>(clazz));
    }

    /**
     * 根据 SQL 语句进行分页查询。
     *
     * @param sql SQL 语句
     * @param pageCurrent 当前页数
     * @param pageSize 每页显示的记录数
     * @param clazz 返回对象列表的元素类型
     * @param <T> 返回类型泛型
     * @return 分页结果
     */
    public <T extends Serializable> Page<T> queryForPage(String sql, int pageCurrent, int pageSize, Class<T> clazz) {
        Assert.hasText(sql, "SQL statement must not be empty");
        Assert.isTrue(pageCurrent >= 1, "Page number must be at least 1");
        Assert.notNull(clazz, "Class must not be null");

        // 查询总记录数
        String sqlCount = PageUtil.countSql(sql);
        int count = jdbcTemplate.queryForObject(sqlCount, Integer.class);
        // 校验当前页数
        pageCurrent = PageUtil.checkPageCurrent(count, pageSize, pageCurrent);
        // 校验每页显示的记录数
        pageSize = PageUtil.checkPageSize(pageSize);
        // 计算总页数
        int totalPage = PageUtil.countTotalPage(count, pageSize);

        // 拼接分页查询的 SQL 语句
        String sqlList = sql + PageUtil.limitSql(count, pageCurrent, pageSize);
        List<T> list = jdbcTemplate.query(sqlList, new BeanPropertyRowMapper<>(clazz));
        return new Page<>(list, count, totalPage, pageCurrent, pageSize);
    }
}
