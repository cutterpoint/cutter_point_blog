package com.cutter.point.blog.xo.service.impl;

import org.springframework.stereotype.Service;

import com.cutter.point.blog.xo.entity.ExceptionLog;
import com.cutter.point.blog.xo.mapper.ExceptionLogMapper;
import com.cutter.point.blog.xo.service.ExceptionLogService;
import com.cutter.point.blog.base.serviceImpl.SuperServiceImpl;


/**
 * <p>
 * 操作日志 服务实现类
 * </p>
 *
 * @author limbo
 * @since 2018-09-30
 */
@Service
public class ExceptionLogServiceImpl extends SuperServiceImpl<ExceptionLogMapper, ExceptionLog> implements ExceptionLogService {

}
