package com.cutter.point.blog.xo.service.impl;

import com.cutter.point.blog.xo.entity.AdminRole;
import org.springframework.stereotype.Service;

import com.cutter.point.blog.xo.mapper.AdminRoleMapper;
import com.cutter.point.blog.xo.service.AdminRoleService;
import com.moxi.mougblog.base.serviceImpl.SuperServiceImpl;


/**
 * <p>
 * 管理员角色关系表 服务实现类
 * </p>
 *
 * @author limbo
 * @since 2018-09-30
 */
@Service
public class AdminRoleServiceImpl extends SuperServiceImpl<AdminRoleMapper, AdminRole> implements AdminRoleService {

}
