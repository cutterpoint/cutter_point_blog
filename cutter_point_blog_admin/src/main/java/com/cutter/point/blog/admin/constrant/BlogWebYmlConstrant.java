package com.cutter.point.blog.admin.constrant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @ClassName BlogWebYmlConstrant
 * @Description YML文件的配置属性
 * @Author xiaof
 * @Date 2019/10/17 22:09
 * @Version 1.0
 **/
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class BlogWebYmlConstrant {

    public static String contextPath;
//    @Value(value = "${BLOG.file.readUrl}")
    public static String readUrl;
    public static String writeUrl;
    public static String imageUrl;

    public String getContextPath() {
        return contextPath;
    }

    @Value(value = "${server.servlet.context-path}")
    public void setContextPath(String contextPath) {
        BlogWebYmlConstrant.contextPath = contextPath;
    }

    public String getReadUrl() {
        return readUrl;
    }

    @Value(value = "${BLOG.file.readUrl}")
    public void setReadUrl(String readUrl) {
        BlogWebYmlConstrant.readUrl = readUrl;
    }

    public String getWriteUrl() {
        return writeUrl;
    }

    @Value(value = "${BLOG.file.writeUrl}")
    public void setWriteUrl(String writeUrl) {
        BlogWebYmlConstrant.writeUrl = writeUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Value(value = "${BLOG.file.imageUrl}")
    public void setImageUrl(String imageUrl) {
        BlogWebYmlConstrant.imageUrl = imageUrl;
    }
}
