package cn.javgo.teach.common.core.tools;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 文件工具类
 *
 * @author javgo.cn
 * @date 2023/12/26
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FileUtils {

    private static final String PIC_TYPE_MAP = "jpg|png|gif|bmp|webp|jpeg";

    public static boolean isPicture(MultipartFile file) {
        try {
            String fileType = FileNameUtil.getSuffix(file.getOriginalFilename());
            if (!StringUtils.hasText(fileType)) {
                fileType = FileTypeUtil.getType(file.getInputStream());
            }
            return StrUtil.isNotBlank(fileType) && PIC_TYPE_MAP.contains(fileType);
        } catch (IOException e) {
            log.error("文件类型判断异常", e);
            return false;
        }
    }
}
