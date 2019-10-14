package com.cutter.point.blog.xo.service.impl;

import org.springframework.stereotype.Service;

import com.cutter.point.blog.xo.entity.Admin;
import com.cutter.point.blog.xo.mapper.AdminMapper;
import com.cutter.point.blog.xo.service.AdminService;
import com.cutter.point.blog.base.serviceImpl.SuperServiceImpl;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author xuzhixiang
 * @since 2018-09-04
 */
@Service
public class AdminServiceImpl extends SuperServiceImpl<AdminMapper, Admin> implements AdminService {

}
