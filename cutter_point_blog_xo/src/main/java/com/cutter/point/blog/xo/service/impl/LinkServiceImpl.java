package com.cutter.point.blog.xo.service.impl;

import org.springframework.stereotype.Service;

import com.cutter.point.blog.xo.entity.Link;
import com.cutter.point.blog.xo.mapper.LinkMapper;
import com.cutter.point.blog.xo.service.LinkService;
import com.cutter.point.blog.base.serviceImpl.SuperServiceImpl;

/**
 * <p>
 * 友链表 服务实现类
 * </p>
 *
 * @author xuzhixiang
 * @since 2018-09-08
 */
@Service
public class LinkServiceImpl extends SuperServiceImpl<LinkMapper, Link> implements LinkService {

}
