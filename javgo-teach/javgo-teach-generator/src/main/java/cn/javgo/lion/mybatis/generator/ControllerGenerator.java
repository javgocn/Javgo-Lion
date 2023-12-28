package cn.javgo.lion.mybatis.generator;

import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * description：
 *
 * @author javgo.cn
 * @date 2023/12/28
 */
@Slf4j
public class ControllerGenerator {

    public static void main(String[] args) {

    }

    private static List<FileOutConfig> forList() {
        List<FileOutConfig> focList = new ArrayList<>();

        // service.dao
        // focList.addAll(serviceDaoList());

        // service.api
        //focList.addAll(serviceApiList());

        // service.auth
        // focList.addAll(serviceAuthList());

        // service.admin
        // focList.addAll(serviceAdminList());

        // feign
        //focList.addAll(feignList());

        // service.feign
        //focList.addAll(serviceFeignList());

        return focList;
    }

    /**
     * 执行创建
     */
    public static void runCreate() {
        log.debug("准备开始执行创建所有文件...");
//        log.debug("DbConfig.url = " + ConfigUtil.);
    }
}
