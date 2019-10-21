package com.cutter.point.blog.xo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cutter.point.blog.base.global.BaseSQLConf;
import com.cutter.point.blog.base.serviceImpl.SuperServiceImpl;
import com.cutter.point.blog.xo.entity.TFileStore;
import com.cutter.point.blog.xo.mapper.TFileStoreMapper;
import com.cutter.point.blog.xo.service.TFileStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName TFileStoreServiceImpl
 * @Description 文件实体类操作dao
 * @Author xiaof
 * @Date 2019/10/20 22:54
 * @Version 1.0
 **/
@Service
public class TFileStoreServiceImpl extends SuperServiceImpl<TFileStoreMapper, TFileStore> implements TFileStoreService {

    @Autowired
    TFileStoreMapper tFileStoreMapper;

    @Override
    public List<TFileStore> getTFileStoreByUid(String uid) {

        QueryWrapper<TFileStore> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseSQLConf.UID, uid);

        return tFileStoreMapper.selectList(queryWrapper);
    }
}
