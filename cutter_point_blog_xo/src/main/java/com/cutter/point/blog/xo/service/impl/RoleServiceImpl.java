package com.cutter.point.blog.xo.service.impl;

import org.springframework.stereotype.Service;

import com.cutter.point.blog.xo.entity.Role;
import com.cutter.point.blog.xo.mapper.RoleMapper;
import com.cutter.point.blog.xo.service.RoleService;
import com.moxi.mougblog.base.serviceImpl.SuperServiceImpl;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author limbo
 * @since 2018-09-30
 */
@Service
public class RoleServiceImpl extends SuperServiceImpl<RoleMapper, Role> implements RoleService {

}
