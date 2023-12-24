package cn.javgo.teach.common.core.base;

import cn.javgo.teach.common.core.enums.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * 通用返回对象
 *
 * @author javgo.cn
 * @date 2023/12/24
 */
@Slf4j
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer code;

    private String msg;

    private T data;

    public static <T> Result<T> error(ResultEnum resultEnum) {
        log.debug("错误码：{}，错误信息：{}", resultEnum.getCode(), resultEnum.getMessage());
        return new Result<>(resultEnum.getCode(), resultEnum.getMessage(), null);
    }

    public static <T> Result<T> error(String msg) {
        log.debug("错误码：{}，错误信息：{}", ResultEnum.ERROR.getCode(), msg);
        return new Result<>(ResultEnum.ERROR.getCode(), msg, null);
    }

    public static <T> Result<T> error(int code, String msg) {
        log.debug("错误码：{}，错误信息：{}", code, msg);
        return new Result<>(code, msg, null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), data);
    }

    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(ResultEnum.SUCCESS.getCode(), msg, data);
    }
}
