package com.cutter.point.blog.picture.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName FileConfig
 * @Description 文件操作配置
 * @Author xiaof
 * @Date 2019/10/10 21:37
 * @Version 1.0
 **/
@Component
public class FileConfig {

    public static String fileBasePath;
    public static long maxFileSize; // 最大文件大小字节
    public final static String STORE_FILE_EXPAND = "blogdata_temp";

    public static String getFileBasePath() {
        return fileBasePath;
    }

    public static long getMaxFileSize() {
        return maxFileSize;
    }

    @Value("${file.size}")
    public void setMaxFileSize(long maxFileSize) {
        //转换为M，传进来的数字
        FileConfig.maxFileSize = maxFileSize * 1024 * 1024;
    }

    @Value("${file.fileBasePath}")
    public void setFileBasePath(String fileBasePath) {
        FileConfig.fileBasePath = fileBasePath;
    }
}
