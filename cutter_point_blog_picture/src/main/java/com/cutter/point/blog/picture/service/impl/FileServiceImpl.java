package com.cutter.point.blog.picture.service.impl;

import com.cutter.point.blog.picture.entity.File;
import org.springframework.stereotype.Service;

import com.cutter.point.blog.picture.mapper.FileMapper;
import com.cutter.point.blog.picture.service.FileService;
import com.moxi.mougblog.base.serviceImpl.SuperServiceImpl;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xuzhixiang
 * @since 2018-09-17
 */
@Service
public class FileServiceImpl extends SuperServiceImpl<FileMapper, File> implements FileService {

}
