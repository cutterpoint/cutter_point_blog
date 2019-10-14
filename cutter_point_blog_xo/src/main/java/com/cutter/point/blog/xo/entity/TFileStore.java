package com.cutter.point.blog.xo.entity;

import com.cutter.point.blog.base.entity.ModelDto;

import java.util.Date;

/**
 * @ClassName TFileStore
 * @Description 文件集合存放实体
 * @Author xiaof
 * @Date 2019/10/14 22:55
 * @Version 1.0
 **/
public class TFileStore extends ModelDto {

    private String uid;
    private String fileOldName;
    private String fileName;
    private String fileUrl;
    private String fileExpandedName;
    private Integer filePosition;
    private Long fileSize;
    private String key;
    private String keyType;
    private int status;
    private Date createTime;
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

    public Integer getFilePosition() {
        return filePosition;
    }

    public void setFilePosition(Integer filePosition) {
        this.filePosition = filePosition;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
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
}
