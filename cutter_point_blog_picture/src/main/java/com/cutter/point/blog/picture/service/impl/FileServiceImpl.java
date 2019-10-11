package com.cutter.point.blog.picture.service.impl;

import com.cutter.point.blog.picture.entity.File;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.cutter.point.blog.picture.mapper.FileMapper;
import com.cutter.point.blog.picture.service.FileService;
import com.cutter.point.blog.base.serviceImpl.SuperServiceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    private final static Logger logger = Logger.getLogger(FileServiceImpl.class);

    private java.io.File makeFile(String dirPath) {
        String newFileName = currentDateToString("yyyyMMddHHmmssSSS") + ".dat_temp";
        java.io.File file = new java.io.File(dirPath + "/" + newFileName);

        try {
            file.createNewFile();
            return file;
        } catch (Exception e) {
            logger.error("创建文件失败!!", e);
            throw new RuntimeException("创建文件失败!!", e);
        }
    }

    public static String currentDateToString(String timeRegex) {
        SimpleDateFormat formatDate = new SimpleDateFormat(timeRegex);
        String str = formatDate.format(new Date());
        return str;
    }
}
