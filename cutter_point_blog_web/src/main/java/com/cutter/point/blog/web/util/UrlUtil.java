package com.cutter.point.blog.web.util;

import com.cutter.point.blog.web.constrant.BlogWebYmlConstrant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName UrlUtil
 * @Description 组装一系列的url
 * @Author xiaof
 * @Date 2019/10/17 22:43
 * @Version 1.0
 **/
@Component
public class UrlUtil {
//    @Value("BLOG.file.imageUrl")
//    private static String imageUrl;

    public static String getImageUrl(String uid) {
        String picture = BlogWebYmlConstrant.contextPath + BlogWebYmlConstrant.imageUrl + "?uid=" + uid;
        return picture;
    }
}
