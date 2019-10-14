package com.cutter.point.blog.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @ClassName SpringContextUtil
 * @Description spring辅助类
 * @Author xiaof
 * @Date 2019/10/10 23:00
 * @Version 1.0
 **/
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        applicationContext = arg0;
    }

    public static ApplicationContext getSpringContext() {
        return applicationContext;
    }

    public static Object getBean(String beanName) {
        if (applicationContext == null) {
            throw new NullPointerException("applicationContext is null");
        }
        return applicationContext.getBean(beanName);
    }
}
