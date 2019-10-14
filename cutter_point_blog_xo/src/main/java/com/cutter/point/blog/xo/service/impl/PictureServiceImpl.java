package com.cutter.point.blog.xo.service.impl;

import com.cutter.point.blog.xo.entity.Picture;
import org.springframework.stereotype.Service;

import com.cutter.point.blog.xo.mapper.PictureMapper;
import com.cutter.point.blog.xo.service.PictureService;
import com.cutter.point.blog.base.serviceImpl.SuperServiceImpl;

/**
 * <p>
 * 图片表 服务实现类
 * </p>
 *
 * @author xuzhixiang
 * @since 2018-09-04
 */
@Service
public class PictureServiceImpl extends SuperServiceImpl<PictureMapper, Picture> implements PictureService {

}
