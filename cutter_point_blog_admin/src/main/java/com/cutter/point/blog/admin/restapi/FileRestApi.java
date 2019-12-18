package com.cutter.point.blog.admin.restapi;

import com.alibaba.fastjson.JSON;
import com.cutter.point.blog.admin.feign.PictureFeignClient;
import com.cutter.point.blog.xo.entity.TFileStore;
import com.cutter.point.blog.xo.service.TFileStoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName FileRestApi
 * @Description 用来操作文件的服务
 * @Author xiaof
 * @Date 2019/10/17 22:20
 * @Version 1.0
 **/
@RestController
@RequestMapping("/file")
@Api(value="文件图片类RestApi",tags={"FileRestApi"})
public class FileRestApi {

    private static Logger log = LogManager.getLogger(FileRestApi.class);

    @Autowired
    private PictureFeignClient pictureFeignClient;
    @Autowired
    private TFileStoreService tFileStoreService;

    @ApiOperation(value="通过UID获取图片字节信息", notes="通过UID获取图片字节信息")
    @RequestMapping("/getImageByUid")
    public byte[] getImageByUid(@ApiParam(name = "uid", value = "file的uid",required = true) @RequestParam(name = "uid", required = true) String uid) {
        //根据uid获取对应的图片字节
        List list = tFileStoreService.getTFileStoreByUid(uid);
        if (list.size() <= 0) {
            return null;
        }
        TFileStore tFileStore = (TFileStore) list.get(0);
        return pictureFeignClient.read(tFileStore.getFileUrl(), tFileStore.getFilePosition(), tFileStore.getFileSize());
    }

    @ApiOperation(value="上传图片", notes="上传图片字节")
    @RequestMapping("/uploadImage")
    public void uploadImage(MultipartFile file, ServletRequest request) {

        // 判断文件是否为空，空则返回失败页面
        if (file.isEmpty()) {
            return;
        }

        try {
            String res = pictureFeignClient.write(file.getBytes());
            //存放数据
            TFileStore tFileStore = JSON.parseObject(res, TFileStore.class);
            if (tFileStore != null && tFileStore.getFileUrl() != null) {
                tFileStoreService.save(tFileStore);
            }
        } catch (IOException e) {
//            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
    }
}
