package com.cutter.point.blog.xo.service.impl;

import com.cutter.point.blog.xo.entity.Visitor;
import org.springframework.stereotype.Service;

import com.cutter.point.blog.xo.mapper.VisitorMapper;
import com.cutter.point.blog.xo.service.VisitorService;
import com.cutter.point.blog.base.serviceImpl.SuperServiceImpl;

/**
 * <p>
 * 博主表 服务实现类
 * </p>
 *
 * @author xuzhixiang
 * @since 2018-09-08
 */
@Service
public class VisitorServiceImpl extends SuperServiceImpl<VisitorMapper, Visitor> implements VisitorService {

}
