package com.cutter.point.blog.xo.service.impl;

import com.cutter.point.blog.xo.entity.WebConfig;
import org.springframework.stereotype.Service;

import com.cutter.point.blog.xo.mapper.WebConfigMapper;
import com.cutter.point.blog.xo.service.WebConfigService;
import com.moxi.mougblog.base.serviceImpl.SuperServiceImpl;


/**
 * <p>
 * 网站配置关系表 服务实现类
 * </p>
 *
 * @author xuzhixiang@163.com
 * @since 2018年11月11日15:10:41
 */
@Service
public class WebConfigServiceImpl extends SuperServiceImpl<WebConfigMapper, WebConfig> implements WebConfigService {

}
