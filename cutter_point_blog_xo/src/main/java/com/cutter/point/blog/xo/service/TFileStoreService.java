package com.cutter.point.blog.xo.service;

import com.cutter.point.blog.base.service.SuperService;
import com.cutter.point.blog.xo.entity.TFileStore;

import java.util.List;
import java.util.Map;

public interface TFileStoreService extends SuperService<TFileStore> {


    /**
     * 通过标签获取文件list
     * @date 2019年10月20日23:05:25
     */
    public List<TFileStore> getTFileStoreByUid(String uid);

}
