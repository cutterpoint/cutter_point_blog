package com.cutter.point.blog.xo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cutter.point.blog.base.entity.SuperEntity;

import java.util.Date;

/**
 * @ClassName TFileStore
 * @Description 文件集合存放实体
 * @Author xiaof
 * @Date 2019/10/14 22:55
 * @Version 1.0
 **/
@TableName("t_file_store")
public class TFileStore extends SuperEntity<TFileStore> {

    private String fileOldName;
    private String fileName;
    private String fileUrl;
    private String fileExpandedName;
    private Long filePosition;
    private Integer fileSize;
    private String keyName;
    private String keyType;

    public String getFileOldName() {
        return fileOldName;
    }

    public void setFileOldName(String fileOldName) {
        this.fileOldName = fileOldName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileExpandedName() {
        return fileExpandedName;
    }

    public void setFileExpandedName(String fileExpandedName) {
        this.fileExpandedName = fileExpandedName;
    }

    public Long getFilePosition() {
        return filePosition;
    }

    public void setFilePosition(Long filePosition) {
        this.filePosition = filePosition;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getKeyType() {
        return keyType;
    }

    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }

}
