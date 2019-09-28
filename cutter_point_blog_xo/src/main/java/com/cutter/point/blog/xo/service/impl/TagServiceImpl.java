package com.cutter.point.blog.xo.service.impl;

import com.cutter.point.blog.xo.entity.Tag;
import org.springframework.stereotype.Service;

import com.cutter.point.blog.xo.mapper.TagMapper;
import com.cutter.point.blog.xo.service.TagService;
import com.moxi.mougblog.base.serviceImpl.SuperServiceImpl;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author xuzhixiang
 * @since 2018-09-08
 */
@Service
public class TagServiceImpl extends SuperServiceImpl<TagMapper, Tag> implements TagService {

}
