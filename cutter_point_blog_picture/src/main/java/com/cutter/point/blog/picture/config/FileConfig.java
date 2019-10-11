package com.cutter.point.blog.picture.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName FileConfig
 * @Description 文件操作配置
 * @Author xiaof
 * @Date 2019/10/10 21:37
 * @Version 1.0
 **/
@ConfigurationProperties(prefix = "file")
@Component
public class FileConfig {

    private static String fileBasePath;
    private static long maxFileSize; // 最大文件大小字节
    public final static String STORE_FILE_EXPAND = "blogdata_temp";

    public static String getFileBasePath() {
        return fileBasePath;
    }

    public static long getMaxFileSize() {
        return maxFileSize;
    }
}
