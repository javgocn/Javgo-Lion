package cn.javgo.lion.common.core.base;

import cn.javgo.lion.common.core.enums.ResultEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 异常基类
 *
 * @author javgo.cn
 * @date 2023/12/24
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    protected int code;

    public BaseException() {
        super("系统异常");
        this.code = 500;
    }

    public BaseException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public BaseException(String message) {
        super(message);
        this.code = 99;
    }

    public BaseException(int code, String message) {
        super(message);
        this.code = code;
    }
}
