package com.cutter.point.blog.picture.entity;

import com.cutter.point.blog.base.entity.ModelDto;

import java.util.Date;

/**
 * @ClassName TFileStore
 * @Author xiaof
 * @Date 2019/10/10 22:48
 * @Version 1.0
 **/
public class TFileStore extends ModelDto {

    private String uid;
//    `file_old_name` varchar(255) DEFAULT NULL COMMENT '旧文件名',
    private String fileOldName;
//    `file_name` varchar(255) DEFAULT NULL COMMENT '文件名',
    private String fileName;
//    `file_url` varchar(255) DEFAULT NULL COMMENT '文件地址',
    private String fileUrl;
    //`file_position` int(20) comment '文件起始位置',
    private long filePosition;
//    `file_expanded_name` varchar(255) DEFAULT NULL COMMENT '文件扩展名',
    private String fileExpandedName;
//    `file_size` int(20) DEFAULT '0' COMMENT '文件大小',
    private long fileSize;
//    `key` varchar(255) comment '文件信息关联key',
    private String key;
//    `key_type` varchar(64) comment '文件类型',
    private String keyType;
//    `status` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '状态',
    private int status;
//    `create_time` timestamp COMMENT '创建时间',
    private Date createTime;
//    `update_time` timestamp  COMMENT '更新时间',
    private Date updateTime;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFileOldName() {
        return fileOldName;
    }

    public void setFileOldName(String fileOldName) {
        this.fileOldName = fileOldName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKeyType() {
        return keyType;
    }

    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    public long getFilePosition() {
        return filePosition;
    }

    public void setFilePosition(long filePosition) {
        this.filePosition = filePosition;
    }
}
