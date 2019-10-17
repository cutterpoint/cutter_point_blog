package com.cutter.point.blog.web.constrant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @ClassName BlogWebYmlConstrant
 * @Description YML文件的配置属性
 * @Author xiaof
 * @Date 2019/10/17 22:09
 * @Version 1.0
 **/
@Component
public class BlogWebYmlConstrant {

    @Value(value = "${server.servlet.context-path}")
    public static String contextPath;
    @Value(value = "${BLOG.file.readUrl}")
    public static String readUrl;
    @Value(value = "${BLOG.file.writeUrl}")
    public static String writeUrl;
    @Value(value = "${BLOG.file.getImageUrl}")
    public static String getImageUrl;

}
