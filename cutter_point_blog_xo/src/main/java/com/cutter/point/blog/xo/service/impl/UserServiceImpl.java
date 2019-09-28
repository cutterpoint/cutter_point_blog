package com.cutter.point.blog.xo.service.impl;

import org.springframework.stereotype.Service;

import com.cutter.point.blog.xo.entity.User;
import com.cutter.point.blog.xo.mapper.UserMapper;
import com.cutter.point.blog.xo.service.UserService;
import com.moxi.mougblog.base.serviceImpl.SuperServiceImpl;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author xuzhixiang
 * @since 2018-09-04
 */
@Service
public class UserServiceImpl extends SuperServiceImpl<UserMapper, User> implements UserService {

}
