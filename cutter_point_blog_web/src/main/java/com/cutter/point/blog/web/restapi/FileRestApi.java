package com.cutter.point.blog.web.restapi;

import com.cutter.point.blog.web.feign.PictureFeignClient;
import com.cutter.point.blog.xo.entity.TFileStore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    private PictureFeignClient pictureFeignClient;

    @ApiOperation(value="通过UID获取图片字节信息", notes="通过UID获取图片字节信息")
    @RequestMapping("/getImageByUid")
    public byte[] getImageByUid(@ApiParam(name = "uid", value = "file的uid",required = true) @RequestParam(name = "uid", required = true) String uid) {
        //根据uid获取对应的图片字节
        TFileStore tFileStore = new TFileStore();
        tFileStore.setUid(uid);
        tFileStore = (TFileStore) tFileStore.qry();
        return pictureFeignClient.read(tFileStore.getFileUrl(), tFileStore.getFilePosition(), tFileStore.getFileSize());
    }
}
