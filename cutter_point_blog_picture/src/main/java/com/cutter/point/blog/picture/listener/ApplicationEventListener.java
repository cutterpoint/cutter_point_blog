package com.cutter.point.blog.picture.listener;

import org.apache.log4j.Logger;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStoppedEvent;

/**
 * @ClassName ApplicationEventListener
 * @Description springboot初始化事件监听
 * @Author xiaof
 * @Date 2019/10/20 20:43
 * @Version 1.0
 **/
public class ApplicationEventListener implements ApplicationListener {

    private static Logger logger = Logger.getLogger(ApplicationEventListener.class);
    public static String RUN_TYPE_START="START";
    public static String RUN_TYPE_STOPED="STOPED";
    public static String RUN_TYPE_CLOSED="CLOSED";

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        {
            if (event instanceof ApplicationEnvironmentPreparedEvent) { // 初始化环境变量
                //logger.info("***监听到初始化环境变量事件***，不处理");
            } else if (event instanceof ApplicationPreparedEvent) { // 初始化完成
                //logger.info("***监听到初始化完成事件***，不处理");

            } else if (event instanceof ContextRefreshedEvent) { // 应用刷新
                //logger.info("***监听到应用刷新事件***，不处理");

            } else if (event instanceof ApplicationReadyEvent) {// 应用已启动完成
                logger.info("***监听到启动完成事件****************应用启动成功***************************");
                try {
                    ApplicationContext appContext = ((ApplicationReadyEvent) event).getApplicationContext();

//                    SpringContextUtil.setApplicationContext(appContext);

                }catch (Throwable e) {
                    logger.error("插入启动记录一次，忽略此异常", e);
                }
            }  else if (event instanceof ContextStoppedEvent) { // 应用停止
                logger.info("***监听到应用停止事件***********应用正在停止***************************");
                try {
                    ApplicationContext appContext = ((ContextStoppedEvent) event).getApplicationContext();

//                    SpringContextUtil.setApplicationContext(appContext);
                }catch (Throwable e) {
                    logger.error("插入启动记录一次，忽略此异常", e);
                }
            } else if (event instanceof ContextClosedEvent) { // 应用关闭
                logger.info("***监听到应用关闭事件***********应用正在关闭***************************");
                try {
                    ApplicationContext appContext = ((ContextClosedEvent) event).getApplicationContext();

//                    SpringContextUtil.setApplicationContext(appContext); //设置上下文方便使用

                }catch (Throwable e) {
                    logger.error("插入启动记录一次，忽略此异常", e);
                }
            } else {
                //	logger.info("***监听到应用其他事件，不处理***"+event.getClass().getName());

            }
        }
    }
}
