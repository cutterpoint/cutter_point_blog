package com.cutter.point.blog.web.util;

import com.cutter.point.blog.web.constrant.BlogWebYmlConstrant;

/**
 * @ClassName UrlUtil
 * @Description 组装一系列的url
 * @Author xiaof
 * @Date 2019/10/17 22:43
 * @Version 1.0
 **/
public class UrlUtil {

    public static String getImageUrl(String uid) {
        String picture = BlogWebYmlConstrant.getImageUrl + "?uid=" + uid;
        return picture;
    }
}
