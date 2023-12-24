package cn.javgo.teach.common.core.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 基础分页类
 *
 * @author javgo.cn
 * @date 2023/12/24
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Page<T extends Serializable> implements Serializable {
    private static final long serialVersionUID = -5764853545343945831L;

    /**
     * 当前分页的数据集
     */
    private List<T> list;

    /**
     * 总记录数
     */
    private int totalCount;

    /**
     * 总页数
     */
    private int totalPage;

    /**
     * 当前页
     */
    private int pageCurrent;

    /**
     * 每页记录数
     */
    private int pageSize;
}
