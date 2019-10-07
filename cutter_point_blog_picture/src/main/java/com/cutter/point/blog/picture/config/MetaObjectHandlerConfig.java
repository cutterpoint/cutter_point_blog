package com.cutter.point.blog.picture.config;

import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.cutter.point.blog.picture.global.SysConf;

@Component
public class MetaObjectHandlerConfig implements MetaObjectHandler {
	
  Logger log = LogManager.getLogger(MetaObjectHandlerConfig.class);	
  @Override
  public void insertFill(MetaObject metaObject) {
	log.info("插入方法填充");
    setFieldValByName(SysConf.CREATE_TIME, new Date(), metaObject);
    setFieldValByName(SysConf.UPDATE_TIME, new Date(), metaObject);
  }

  @Override
  public void updateFill(MetaObject metaObject) {
	  log.info("更新方法填充");
	  setFieldValByName(SysConf.UPDATE_TIME, new Date(), metaObject);
  }
}
