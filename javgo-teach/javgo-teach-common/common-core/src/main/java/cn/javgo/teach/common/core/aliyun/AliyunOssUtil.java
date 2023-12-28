package cn.javgo.teach.common.core.aliyun;

import cn.hutool.core.util.IdUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.ObjectMetadata;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Objects;

/**
 * description：阿里云 OSS 工具类
 *
 * @author javgo.cn
 * @date 2023/12/28
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AliyunOssUtil {

    private final static String FILE_PREFIX = "javgo-lion";

    /**
     * 生成阿里云 OSS 的签名 URL。
     *
     * @param aliyun  阿里云配置信息
     * @param url     资源URL
     * @param expires 过期时间
     * @return 签名后的URL
     */
    public static String generateSignedUrl(Aliyun aliyun, String url, Date expires) {
        String resourcePath = getResourcePath(url, aliyun.getAliyunOssUrl());
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(aliyun.getAliyunOssBucket(), resourcePath);
        request.setExpiration(expires);
        return aliyun.getAliyunOssUrl() + resourcePath + "?" + getOssClient(aliyun).generatePresignedUrl(request).getQuery();
    }

    /**
     * 上传图片文件到阿里云 OSS。
     *
     * @param file   图片文件
     * @param aliyun 阿里云配置信息
     * @return 上传后的URL
     */
    public static String uploadImage(File file, Aliyun aliyun) {
        return uploadFile(file, aliyun, FILE_PREFIX);
    }

    /**
     * 上传图片文件到阿里云OSS（多部分文件）。
     *
     * @param file   多部分文件
     * @param aliyun 阿里云配置信息
     * @return 上传后的URL
     */
    public static String uploadImage(MultipartFile file, Aliyun aliyun) {
        return uploadMultipartFile(file, aliyun, FILE_PREFIX);
    }

    /**
     * 上传文档到阿里云OSS。
     *
     * @param file   文档文件
     * @param aliyun 阿里云配置信息
     * @return 上传后的URL
     */
    public static String uploadDocument(File file, Aliyun aliyun) {
        return uploadFile(file, aliyun, FILE_PREFIX);
    }

    /**
     * 上传文档到阿里云OSS（多部分文件）。
     *
     * @param file   多部分文件
     * @param aliyun 阿里云配置信息
     * @return 上传后的URL
     */
    public static String uploadDocument(MultipartFile file, Aliyun aliyun) {
        return uploadMultipartFile(file, aliyun, FILE_PREFIX);
    }

    /**
     * 从阿里云OSS删除文件。
     *
     * @param url    文件URL
     * @param aliyun 阿里云配置信息
     */
    public static void deleteFile(String url, Aliyun aliyun) {
        String filePath = getResourcePath(url, aliyun.getAliyunOssUrl());
        deleteObject(aliyun, filePath);
    }

    /**
     * 根据给定的URL和OSS URL获取资源路径
     *
     * @param url    待处理的URL
     * @param ossUrl OOS URL
     * @return 资源路径
     */
    private static String getResourcePath(String url, String ossUrl) {
        return url.replace(ossUrl, "");
    }


    /**
     * 上传文件到阿里云
     *
     * @param file   要上传的文件
     * @param aliyun 阿里云对象
     * @param prefix 文件路径前缀
     * @return 上传成功返回文件路径，失败返回空字符串
     */
    private static String uploadFile(File file, Aliyun aliyun, String prefix) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            return putObject(aliyun, generateFilePath(file, prefix), fileInputStream, file.getName());
        } catch (Exception e) {
            log.error("文件上传失败", e);
            return "";
        }
    }


    /**
     * 上传 MultipartFile 类型的文件到阿里云 OSS
     *
     * @param file   待上传的文件
     * @param aliyun 阿里云配置信息
     * @param prefix 文件路径前缀
     * @return 上传成功返回文件路径，失败返回空字符串
     */
    private static String uploadMultipartFile(MultipartFile file, Aliyun aliyun, String prefix) {
        try (InputStream inputStream = file.getInputStream()) {
            return putObject(aliyun, generateFilePath(file, prefix), inputStream, file.getOriginalFilename());
        } catch (Exception e) {
            log.error("文件上传失败", e);
            return "";
        }
    }


    /**
     * 生成文件路径 (前缀/UUID.后缀)
     *
     * @param file   文件
     * @param prefix 文件路径前缀
     * @return 文件路径
     */
    private static String generateFilePath(File file, String prefix) {
        String extension = getFileExtension(file.getName());
        return prefix + "/" + IdUtil.simpleUUID() + extension;
    }

    /**
     * 生成文件路径 (前缀/UUID.后缀)
     *
     * @param file   上传的文件
     * @param prefix 文件路径前缀
     * @return 生成的文件路径
     */
    private static String generateFilePath(MultipartFile file, String prefix) {
        String extension = getFileExtension(Objects.requireNonNull(file.getOriginalFilename()));
        return prefix + "/" + IdUtil.simpleUUID() + extension;
    }

    /**
     * 获取文件的扩展名。
     *
     * @param fileName 文件名
     * @return 文件的扩展名
     */
    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 将 InputStream 中的数据存储到 OSS 中，并返回 OSS 上的文件路径
     *
     * @param aliyun      阿里云配置信息
     * @param filePath    文件路径
     * @param inputStream 输入流
     * @param fileName    文件名
     * @return 存储成功后的文件路径
     */
    private static String putObject(Aliyun aliyun, String filePath, InputStream inputStream, String fileName) {
        OSS ossClient = getOssClient(aliyun);
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentDisposition("attachment; filename=" + fileName);
        meta.setObjectAcl(CannedAccessControlList.Private);
        ossClient.putObject(aliyun.getAliyunOssBucket(), filePath, inputStream, meta);
        return aliyun.getAliyunOssUrl() + filePath;
    }

    /**
     * 根据给定的阿里云对象获取 OSS 客户端连接
     *
     * @param aliyun 阿里云对象
     * @return OSS 客户端连接
     */
    private static OSS getOssClient(Aliyun aliyun) {
        return new OSSClientBuilder().build(aliyun.getAliyunOssEndpoint(), aliyun.getAliyunAccessKeyId(), aliyun.getAliyunAccessKeySecret());
    }

    /**
     * 从指定的 OSS 存储空间中删除指定的 Object。
     *
     * @param aliyun 阿里云对象
     * @param key    要删除的Object的键
     */
    private static void deleteObject(Aliyun aliyun, String key) {
        getOssClient(aliyun).deleteObject(aliyun.getAliyunOssBucket(), key);
    }
}
